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
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class TrailerController {

    private final TrailerService trailerService;

    @GetMapping("/{hlsName}.m3u8")
    public ResponseEntity<Resource> getHls(@PathVariable String hlsName) throws IOException {
        Resource resource = trailerService.getVideoRes(hlsName + ".m3u8");

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=good.m3u8");
        headers.setContentType(MediaType.parseMediaType("application/vnd.apple.mpegurl"));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/{tsName}.ts")
    public ResponseEntity<Resource> getHlsTs(@PathVariable String tsName) throws IOException {

        tsName = tsName + ".ts";
        Resource resource = trailerService.getVideoRes(tsName);

        HttpHeaders headers = new HttpHeaders();
        // CONTENT_DISPOSITION : 다운로드 동작을 명시적으로 제어하고, 파일 이름을 지정할 수 있습니다.
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + tsName);
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/upload")
    public ResponseEntity<?> convert() throws IOException {
        trailerService.convertHls();
        return ResponseEntity.ok().build();
    }

}
