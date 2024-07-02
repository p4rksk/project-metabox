package org.example.metabox.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.metabox._core.errors.exception.Exception401;
import org.example.metabox._core.util.FileUtil;
import org.example.metabox._core.util.FormatUtil;
import org.example.metabox.book.BookRepository;
import org.example.metabox.theater.Theater;
import org.example.metabox.theater.TheaterRepository;
import org.example.metabox.theater.TheaterRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final BookRepository bookRepository; ;
    private final TheaterRepository theaterRepository;
    private final FileUtil fileUtil;
    ;

    //로그인
    public Admin login(AdminRequest.LoginDTO reqDTO){
        Admin admin = adminRepository.findByLoginIdAndPassword(reqDTO.getLoginId(),reqDTO.getPassword())
                .orElseThrow(() -> new Exception401("존재 하지 않는 계정입니다."));

        return admin;
    }

    // 최고 관리자 매출 페이지 DTO
    public AdminResponse.RootAdminResponseDTO getRootAdmin() {
        // 전체 매출 조회
        Long totalSales = theaterRepository.findTotalSales();

        // 지점별 매출 조회
        List<Object[]> salesByTheater = theaterRepository.findSalesByTheater();
        List<AdminResponse.TheaterSalesDTO> theaterSalesDTO = salesByTheater.stream()
                .map(result -> {
                    int theaterId = (int) result[0];
                    String theaterName = (String) result[1];
                    Long theaterSales = (Long) result[2];

                    // 지점 정보 조회
                    Theater theater = theaterRepository.findById(theaterId).orElse(null);

                    return AdminResponse.TheaterSalesDTO.builder()
                            .theaterId(theaterId)
                            .theaterName(theaterName)
                            .theaterSales(FormatUtil.moneyFormat(theaterSales))
                            .theaterAddress(theater.getAddress())
                            .theaterTel(theater.getNumber())
                            .build();
                })
                .collect(Collectors.toList());

        // 영화별 매출 조회
        List<Object[]> salesByMovie = theaterRepository.findAllTheaterSalesByMovie();
        List<AdminResponse.MovieSalesDTO> SalesByMovieDTO = salesByMovie.stream()
                .map(result -> {
                    int movieId = (int) result[0];
                    String movieTitle = (String) result[1];
                    Date startDate = (Date) result[2];
                    Date endDate = (Date) result[3];
                    Long movieSales = (Long) result[4];
                    Long viewerCount = (Long) result[5];

                    return AdminResponse.MovieSalesDTO.builder()
                            .movieId(movieId)
                            .movieTitle(movieTitle)
                            .startDate(startDate)
                            .endDate(endDate)
                            .movieSales(FormatUtil.moneyFormat(movieSales))
                            .viewerCount(FormatUtil.viewerFormat(viewerCount))
                            .build();
                })
                .collect(Collectors.toList());

        // RootAdminResponseDTO를 생성하여 반환
        return AdminResponse.RootAdminResponseDTO.builder()
                .totalSales(FormatUtil.moneyFormat(totalSales))
                .theaterSales(theaterSalesDTO)
                .movieSales(SalesByMovieDTO)
                .build();
    }

    public void saveTheater(TheaterRequest.theaterSaveDTO reqDTO) {
        Theater theater = toEntity(reqDTO);
        theaterRepository.save(theater);
    }

    private Theater toEntity(TheaterRequest.theaterSaveDTO reqDTO) {
        String imgFileName = null;

        try {
            imgFileName = fileUtil.saveTheaterImage(reqDTO.getImgFilename());
        } catch (IOException e) {
            throw new RuntimeException("이미지 오류", e);
        }

        Theater theater = Theater.builder()
                .address(reqDTO.getTheaterAddress())
                .areaCode(String.valueOf(reqDTO.getAreaCode()))
                .areaName(reqDTO.getAreaName())
                .imgFilename(imgFileName)
                .loginId(reqDTO.getTheaterId())
                .name(reqDTO.getTheaterName())
                .number(reqDTO.getTheaterTel())
                .password(reqDTO.getPassword())
                .url(reqDTO.getUrl())
                .build();
        return theater;
    }
}
