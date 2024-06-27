package org.example.metabox.book;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.metabox._core.errors.exception.Exception404;
import org.example.metabox.screening.ScreeningRepository;
import org.example.metabox.screening_info.ScreeningInfo;
import org.example.metabox.screening_info.ScreeningInfoRepository;
import org.example.metabox.seat.Seat;
import org.example.metabox.seat.SeatRepository;
import org.example.metabox.theater.Theater;
import org.example.metabox.theater.TheaterRepository;
import org.example.metabox.user.User;
import org.example.metabox.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final TheaterRepository theaterRepository;
    private final ScreeningInfoRepository screeningInfoRepository;
    private final SeatRepository seatRepository;
    private final ScreeningRepository screeningRepository;
    private final UserRepository userRepository;

    public BookResponse.BookDTO theaterAreaList() {
        // 극장 지역 조회
        List<Theater> theaterList = theaterRepository.findAll();
        return new BookResponse.BookDTO(theaterList);
    }

    public List<String> bookFormSubList(String areaCode) {
        List<String> respDTO = screeningInfoRepository.findSubListByAreaCode(areaCode);
        System.out.println("안녕");
        System.out.println(respDTO);
        return respDTO;
    }

    @Transactional
    public BookResponse.BookSeatDTO seatDetail(int screeningInfoId) {
        int screeningId = screeningInfoRepository.findByScreeningInfoId(screeningInfoId);
        List<Seat> seatList = seatRepository.findAllByScreeningId(screeningId);
        ScreeningInfo screeningInfo = screeningInfoRepository.findById(screeningInfoId).orElseThrow(() -> new Exception404("잘못된 접근 입니다."));
        return new BookResponse.BookSeatDTO(screeningInfo, seatList);
    }

    // 포인트 및 결제 페이지
    @Transactional
    public BookResponse.PaymentDTO payment(List<String> idList, int screeningInfoId, int userId) {
        List<Seat> seatList = new ArrayList<>();
        for (int i = 0; i < idList.size(); i++) {
            Seat seat = seatRepository.findById(Integer.valueOf(idList.get(i))).orElseThrow(() -> new Exception404("좌석을 찾을 수 없습니다."));
            seatList.add(seat);
        }
        ScreeningInfo screeningInfo = screeningInfoRepository.findById(screeningInfoId).orElseThrow(() -> new Exception404("상영 정보를 찾을 수 없습니다."));

        User user = userRepository.findById(userId).orElseThrow(() -> new Exception404("유저 정보를 찾을 수 없습니다."));
        int point = user.getPoint();
        return new BookResponse.PaymentDTO(screeningInfo, seatList, point);
    }
}
