package org.example.metabox.trailer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class TrailerController {

    private final TrailerRepository trailerRepository;
    private final TrailerService trailerService;

    @GetMapping("/{trailerId}/master.m3u8")
    public ResponseEntity<Resource> getMasterM3U8(@PathVariable int trailerId) throws IOException {
        // 트레일러 정보를 데이터베이스에서 조회
        Trailer trailer = trailerRepository.findById(trailerId)
                .orElseThrow(() -> new RuntimeException("트레일러를 찾을 수 없습니다."));

        // 마스터 M3U8 파일 이름을 가져옴
        String masterM3u8Filename = trailer.getMasterM3U8Filename();

        // 파일 이름을 사용하여 리소스를 가져옴
        Resource resource = trailerService.getVideoRes(masterM3u8Filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/vnd.apple.mpegurl")
                .body(resource);
    }


}
