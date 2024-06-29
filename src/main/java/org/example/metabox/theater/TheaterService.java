package org.example.metabox.theater;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.metabox._core.errors.exception.Exception401;
import org.example.metabox._core.errors.exception.Exception404;
import org.example.metabox._core.util.FormatUtil;
import org.example.metabox.movie.MovieRepository;
import org.example.metabox.screening_info.ScreeningInfo;
import org.example.metabox.screening_info.ScreeningInfoRepository;
import org.example.metabox.theater_scrap.TheaterScrap;
import org.example.metabox.theater_scrap.TheaterScrapRepository;
import org.example.metabox.user.SessionUser;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TheaterService {
    private final TheaterRepository theaterRepository;
    private final TheaterScrapRepository theaterScrapRepository;
    private final MovieRepository movieRepository;
    private final ScreeningInfoRepository screeningInfoRepository;

    @Transactional
    public TheaterResponse.TheaterDTO movieSchedule(SessionUser sessionUser, Integer theaterId, LocalDate date) {
        // 1. 내가 Scrap한 목록 불러오기
        List<TheaterScrap> theaterScrapList = new ArrayList<>();
        if (sessionUser == null) {
//            while (theaterScrapList.size() < 5) {
//                theaterScrapList.add(TheaterScrap.builder().id(0).theater(Theater.builder().name("").build()).build());
//            }
        } else {
            theaterScrapList = theaterScrapRepository.findByUserId(sessionUser.getId());
            // 무조건 theaterScrapList의 사이즈가 5가 되도록 설정
//            while (theaterScrapList.size() < 5) {
//                theaterScrapList.add(TheaterScrap.builder().id(0).theater(Theater.builder().name("").build()).build());
//            }
        }
        // 2. 지역 목록에 따른 극장 목록 가져오기
        List<Theater> theaterList = theaterRepository.findAll();

        // 3. ScreeningInfo 가져오기

        List<ScreeningInfo> screeningInfoList = screeningInfoRepository.findByTheaterId(theaterId, date);

        // 4. theater 가져오기
        Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new Exception404("극장을 찾을 수 없습니다."));

        // 리턴
        TheaterResponse.TheaterDTO respDTO = new TheaterResponse.TheaterDTO(theaterScrapList, theaterList, screeningInfoList, theater);
        return respDTO;
    }

    @Transactional
    public TheaterResponse.TheaterInfoDTO theaterInfo(SessionUser sessionUser, Integer theaterId) {
        // 1. 내가 Scrap한 목록 불러오기
        List<TheaterScrap> theaterScrapList = new ArrayList<>();
        if (sessionUser == null) {
//            while (theaterScrapList.size() < 5) {
//                theaterScrapList.add(TheaterScrap.builder().id(0).theater(Theater.builder().name("").build()).build());
//            }
        } else {
            theaterScrapList = theaterScrapRepository.findByUserId(sessionUser.getId());
            // 무조건 theaterScrapList의 사이즈가 5가 되도록 설정
//            while (theaterScrapList.size() < 5) {
//                theaterScrapList.add(TheaterScrap.builder().id(0).theater(Theater.builder().name("").build()).build());
//            }
        }
        // 2. 지역 목록에 따른 극장 목록 가져오기
        List<Theater> theaterList = theaterRepository.findAll();

        //3. theater 가져오기
        Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new Exception404("극장을 찾을 수 없습니다."));

        TheaterResponse.TheaterInfoDTO respDTO = new TheaterResponse.TheaterInfoDTO(theaterScrapList, theaterList, theater);
        return respDTO;
    }

    @Transactional
    public TheaterResponse.TheaterAjaxDTO movieScheduleDate(Integer theaterId, LocalDate date) {
        List<ScreeningInfo> screeningInfoList = screeningInfoRepository.findByTheaterId(theaterId, date);
        TheaterResponse.TheaterAjaxDTO respDTO = new TheaterResponse.TheaterAjaxDTO(screeningInfoList);
        return respDTO;
    }

    public Theater login(TheaterRequest.LoginDTO reqDTO) {
        System.out.println("아이디 : " + reqDTO.getLoginId());
        System.out.println("비밀번호 : " + reqDTO.getPassword());
        Theater theater = theaterRepository.findByLoginIdAndPassword(reqDTO.getLoginId(), reqDTO.getPassword()).orElseThrow(() -> new Exception401("아이디 또는 비밀번호가 틀렸습니다."));
        return theater;
    }

    // 극장 관리자
    public TheaterResponse.TheaterSalesDTO getThearerSalesInfo(int theaterId) {
        // 극장 매출 조회
        List<Object[]> theaterInfo = theaterRepository.getTheaterSales(theaterId); // 무조건 1건이라 Object[]로 받고 싶은데 받으면 터짐..
        Object[] result = theaterInfo.get(0);

        // 영화별 매출 조회
        List<Object[]> movieSales = theaterRepository.findTheaterSalesByMovie(theaterId);
        List<TheaterResponse.TheaterSalesDTO.MovieTotalSalesDTO> movieSalesList = movieSales.stream().map(row ->
                TheaterResponse.TheaterSalesDTO.MovieTotalSalesDTO.builder()
                        .id((int) row[0])
                        .movieName((String) row[1])
                        .startDate((Date) row[2])
                        .endDate((Date) row[3])
                        .totalMovieSales(FormatUtil.moneyFormat((Long) row[4]))
                        .viewerCount(FormatUtil.viewerFormat((long) row[5]))
                        .build()
        ).collect(Collectors.toList());

        return TheaterResponse.TheaterSalesDTO.builder()
                .areaName((String) result[0])
                .theaterName((String) result[1])
                .address((String) result[2])
                .theaterTel((String) result[3])
                .totalTheaterSales(FormatUtil.moneyFormat((Long) result[4]))
                .movieSalesList(movieSalesList)
                .build();
    }
}
