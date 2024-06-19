package org.example.metabox.movie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;


    // 모든 영화를 조회하는 메서드
    public List<MovieResponse.MovieChartDTO> getAllMovies() {
        // movieRepository를 사용하여 모든 Movie 객체를 데이터베이스로부터 가져옵니다.
        List<Movie> movies = movieRepository.findAll();

        // 가져온 Movie 객체들을 MovieResponse.MovieDTO 객체로 변환하여 리스트로 만듭니다.
        return movies.stream()
                     .map(MovieResponse.MovieChartDTO::new)
                     .collect(Collectors.toList());
    }

    // movieId에 해당하는 상세 정보를 조회하는 메서드
    public MovieResponse.MovieDetailDTO findById(Integer movieId) {
        // movieId에 해당하는 영화 정보를 데이터베이스에서 조회합니다.
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("해당 영화가 존재하지 않습니다. " + movieId));

        // 영화 개봉 상태를 계산합니다.
        String status = checkMovieReleaseStatus(movie.getDate());

        // 조회한 영화 정보를 MovieDetailDTO로 변환하여 반환합니다.
        return MovieResponse.MovieDetailDTO.formEntity(movie, status);
    }

    // 상영 상태를 확인하는 메서드
    public String checkMovieReleaseStatus(Date releaseDate) {
        // 현재 날짜를 LocalDate 객체로 가져옵니다.
        LocalDate today = LocalDate.now();
        // 영화 개봉일을 LocalDate 객체로 변환합니다.
        LocalDate movieReleaseDate = releaseDate.toLocalDate();

        // 개봉일이 오늘 이전이거나 오늘과 같으면 "현재상영중"을 반환합니다.
        if (movieReleaseDate.isBefore(today) || movieReleaseDate.isEqual(today)) {
            return "현재상영중";
        } else {
            // 개봉일이 오늘 이후인 경우, 오늘부터 개봉일까지의 일수를 계산합니다.
            long dDay = ChronoUnit.DAYS.between(today, movieReleaseDate);
            // "상영예정 D-일수" 형식으로 반환합니다.
            return "상영예정 D-" + dDay;
        }
    }

    public Movie addMovie(MovieRequest.movieSavaFormDTO reqDTO) {

        MultipartFile file = reqDTO.getImgFilename();
        String fileName = file.getOriginalFilename();
        String uploadDir = "image/movie"; // 파일을 저장할 경로

        try {
            Path copyLocation = Paths.get(uploadDir + "/" + fileName);
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            // 예외 처리
        }

        Movie movie = Movie.builder()
                .title(reqDTO.getTitle())
                .engTitle(reqDTO.getEngTitle())
                .director(reqDTO.getDirector())
                .actor(reqDTO.getActor())
                .genre(reqDTO.getGenre())
                .info(reqDTO.getInfo())
                .date(reqDTO.getDate())
                .imgFilename(fileName)
                .description(reqDTO.getDescription())
                .build();
        return movieRepository.save(movie);
    }

}
