package org.example.metabox.trailer;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/videos")
public class TrailerController {

    private final TrailerService trailerService;

    public TrailerController(TrailerService trailerService) { this.trailerService = trailerService; }

    private final Path videoLocation = Paths.get(System.getProperty("user.dir"), "video");


    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) {
        System.out.println(1);
        try {
            System.out.println(2);
            String m3u8Filename = trailerService.uploadAndEncodeVideo(file);
            System.out.println(3);
            return ResponseEntity.ok(m3u8Filename);
        } catch (Exception e) {
            System.out.println(4);
            return ResponseEntity.status(500).body("Failed to upload and encode video: " + e.getMessage());
        }
    }
}
