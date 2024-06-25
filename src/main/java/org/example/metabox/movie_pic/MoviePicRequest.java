package org.example.metabox.movie_pic;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class MoviePicRequest {

    // 스틸컷 추가(스틸컷만 추가할 때)
    @Data
    public static class StillsAddDTO {
        private int movieId;
        private MultipartFile[] stills;
    }
}
