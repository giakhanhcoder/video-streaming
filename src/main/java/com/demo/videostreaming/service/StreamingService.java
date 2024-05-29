package com.demo.videostreaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StreamingService {

    private static final String FORMAT="classpath:videos/%s.mp4";
//    private static final String UPLOAD_DIR="D:/Explore/UDEMY/Spring Boot WebFlux - Video Streaming/videostreaming/src/main/resources/videos/";

    @Autowired
    private ResourceLoader resourceLoader;
    public Mono<Resource> getVideo(String title){
        return Mono.fromSupplier(()->resourceLoader.
                getResource(String.format(FORMAT,title)))   ;
    }

   public Mono<String> saveFile(String uploadDir, FilePart filePart){
       Path path = Paths.get(uploadDir).resolve(filePart.filename()).normalize();
       // Đảm bảo thư mục tồn tại, tạo nếu không có
       try {
           Files.createDirectories(path.getParent());
       }catch (IOException e){
           return Mono.error(e);
       }
       return filePart.transferTo(path).thenReturn(path.toString());
   }
}
























