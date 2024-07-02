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

    // 극장 사진을 저장하는 메서드
    public String saveTheaterImage(MultipartFile file) throws IOException {
        return saveFile("theater", file);
    }

    //m3u8파일을 상대경로로 변환하는 메서드
    public String getRelativePathToM3u8File(String fileName) {
        try {
            // upload 폴더 경로 설정
            Path videoFolder = Paths.get(System.getProperty("user.dir"), "upload");

            // upload 폴더 내에서 fileName으로 끝나는 첫 번째 .m3u8 파일 경로 찾기
            Path m3u8FilePath = Files.walk(videoFolder)
                    .filter(path -> path.getFileName().toString().endsWith(fileName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("해당 파일이 video 폴더 내에 존재하지 않습니다: " + fileName));

            // upload 폴더로부터 상대 경로 생성
            Path relativePath = videoFolder.relativize(m3u8FilePath);

            // 상대 경로를 문자열로 반환
            return relativePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("video 폴더를 읽는 중 오류가 발생했습니다.", e);
        }
    }


    //mp4 파일을 상대경로로 변환하는 메서드
    public String getRelativePathToMp4File(String fileName) {
        try {
            // upload 폴더 경로 설정
            Path videoFolder = Paths.get(System.getProperty("user.dir"), "upload");

            // upload 폴더 내에서 fileName으로 끝나는 첫 번째 .mp4 파일 경로 찾기
            Path mp4FilePath = Files.walk(videoFolder)
                    .filter(path -> path.getFileName().toString().endsWith(fileName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("해당 파일이 video 폴더 내에 존재하지 않습니다: " + fileName));

            // upload 폴더로부터 상대 경로 생성
            Path relativePath = videoFolder.relativize(mp4FilePath);

            // 상대 경로를 문자열로 반환
            return relativePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("video 폴더를 읽는 중 오류가 발생했습니다.", e);
        }
    }
}
