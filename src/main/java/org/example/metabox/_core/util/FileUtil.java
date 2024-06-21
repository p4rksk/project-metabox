package org.example.metabox._core.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUtil {

    // 기본 업로드 폴더 설정
    private static final String BASE_UPLOAD_FOLDER = "image"; // 프로젝트 루트 디렉토리에 있는 이미지 폴더

    // 파일을 저장하는 메서드
    public String saveFile(String subFolder, MultipartFile file) throws IOException {
        // 업로드된 파일의 원래 이름을 가져옴
        String originalFileName = file.getOriginalFilename();
        
        // 기본 업로드 폴더와 서브 폴더를 결합하여 전체 업로드 경로를 생성
        Path uploadPath = Paths.get(BASE_UPLOAD_FOLDER, subFolder);

        // 업로드 경로가 존재하지 않으면 디렉토리를 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 업로드 경로에 파일 이름을 결합하여 복사할 위치를 설정
        Path copyLocation = uploadPath.resolve(originalFileName);

        // 파일을 지정된 위치로 복사 (덮어쓰기)
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

        // 저장된 파일 이름을 반환
        return originalFileName;
    }

    // 영화 포스터 파일을 저장하는 메서드
    public String saveMoviePoster(MultipartFile file) throws IOException {
        return saveFile("movie", file);
    }

    // 스틸컷 파일을 저장하는 메서드
    public String saveMovieStill(MultipartFile file) throws IOException {
        return saveFile("movie_pic", file);
    }

    // 영화 홍보 영상 파일을 저장하는 메서드
    public String saveMovieTrailer(MultipartFile file) throws IOException {
        return saveFile("trailer", file);
    }

}
