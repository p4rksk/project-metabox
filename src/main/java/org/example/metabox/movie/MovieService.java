package org.example.metabox.movie;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

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

}
