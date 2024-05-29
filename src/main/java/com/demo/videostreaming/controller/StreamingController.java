package com.demo.videostreaming.controller;

import com.demo.videostreaming.service.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StreamingController {

    private static final String UPLOAD_DIR="D:/Explore/UDEMY/Spring Boot WebFlux - Video Streaming/videostreaming/src/main/resources/videos/";


    private final StreamingService service;

    @GetMapping(value = "video/{title}", produces = "video/mp4")
    public Mono<Resource> getVideos(@PathVariable String title){
        return service.getVideo(title);
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
        if(file.isEmpty()){
            return null;
        }

        try {
            final Path directory = Paths.get(UPLOAD_DIR);
            final Path filepath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());

            if(!Files.exists(directory)){
                Files.createDirectories(directory);
            }
            Files.write(filepath, file.getBytes());
        }catch (IOException e){
            return null;
        }
        return "ok";
    }

    @GetMapping("/helloCi")
    public String helloCi(){
        return "Hello CI";
    }
}
