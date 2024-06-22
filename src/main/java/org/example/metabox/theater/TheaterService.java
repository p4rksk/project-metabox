package org.example.metabox.theater;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.metabox.movie.Movie;
import org.example.metabox.movie.MovieRepository;
import org.example.metabox.theater_scrap.TheaterScrap;
import org.example.metabox.theater_scrap.TheaterScrapRepository;
import org.example.metabox.user.SessionUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TheaterService {
    private final TheaterRepository theaterRepository;
    private final TheaterScrapRepository theaterScrapRepository;
    private final MovieRepository movieRepository;

    @Transactional
    public TheaterResponse.TheaterDTO movieSchedule(SessionUser sessionUser, Integer theaterId) {
        // 1. 내가 Scrap한 목록 불러오기
        List<TheaterScrap> theaterScrapList = new ArrayList<>();
        if (sessionUser == null) {
            while (theaterScrapList.size() < 5) {
                theaterScrapList.add(TheaterScrap.builder().id(0).theater(Theater.builder().name("").build()).build());
            }
        } else {
            theaterScrapList = theaterScrapRepository.findByUserId(sessionUser.getId());
            // 무조건 theaterScrapList의 사이즈가 5가 되도록 설정
            while (theaterScrapList.size() < 5) {
                theaterScrapList.add(TheaterScrap.builder().id(0).theater(Theater.builder().name("").build()).build());
            }
        }
        // 2. 지역 목록에 따른 극장 목록 가져오기
        List<Theater> theaterList = theaterRepository.findAll();

        // 3. ScreeningInfo 가져오기
        List<Movie> movieList = movieRepository.findByTheaterId(theaterId);
        // 리턴
        TheaterResponse.TheaterDTO respDTO = new TheaterResponse.TheaterDTO(theaterScrapList, theaterList, movieList);
        System.out.println("안녕" + respDTO);
        return respDTO;
    }
}
