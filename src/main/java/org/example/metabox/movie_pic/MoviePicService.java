package org.example.metabox.movie_pic;

import lombok.RequiredArgsConstructor;
import org.example.metabox._core.util.FileUtil;
import org.example.metabox.movie.Movie;
import org.example.metabox.movie.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoviePicService {

    private final MoviePicRepository moviePicRepository;
    private final FileUtil fileUtil;
    private final MovieRepository movieRepository;

    // 수정 페이지에서 스틸컷 추가
    public void addStills(int movieId, MoviePicRequest.StillsAddDTO reqDTO) {
        // 스틸컷 이미지 파일 처리
        List<MoviePic> moviePicList = new ArrayList<>();
        MultipartFile[] stills = reqDTO.getStills();

        // Movie 객체를 가져옴
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("영화를 찾을 수 없습니다."));

        for (MultipartFile still : stills) {
            try {
                // 스틸컷 이미지 파일을 저장하고 파일명을 반환
                String stillFileName = fileUtil.saveMovieStill(still);
                // MoviePic 객체 생성 및 파일명 설정
                MoviePic moviePic = new MoviePic();
                moviePic.setImgFilename(stillFileName);
                moviePic.setMovie(movie); // 외래 키 설정
                moviePicList.add(moviePic);
            } catch (IOException e) {
                throw new RuntimeException("스틸컷 이미지 오류", e);
            }
        }
        // MoviePic 리스트를 저장
        moviePicRepository.saveAll(moviePicList);
    }

    // 스틸컷 삭제
    public void deleteStills(int id) {
        moviePicRepository.deleteById(id);
    }
}
