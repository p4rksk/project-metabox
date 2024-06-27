package org.example.metabox.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.metabox._core.errors.exception.Exception401;
import org.example.metabox._core.util.FormatUtil;
import org.example.metabox.book.BookRepository;
import org.example.metabox.theater.Theater;
import org.example.metabox.theater.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final BookRepository bookRepository; ;
    private final TheaterRepository theaterRepository; ;

    //로그인
    public Admin login(AdminRequest.LoginDTO reqDTO){
        Admin admin = adminRepository.findByLoginIdAndPassword(reqDTO.getLoginId(),reqDTO.getPassword())
                .orElseThrow(() -> new Exception401("존재 하지 않는 계정입니다."));

        return admin;
    }

    // 최고 관리자 매출 페이지 DTO
    public AdminResponse.RootAdminResponseDTO getRootAdmin() {
        // 전체 매출 조회
        Long totalSales = bookRepository.getTotalSales();

        // 지점별 매출 조회
        List<Object[]> salesByTheater = bookRepository.findSalesByTheater();

        // DTO로 변환
        List<AdminResponse.TheaterSalesDTO> theaterSalesDTOList = salesByTheater.stream()
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

        // RootAdminResponseDTO를 생성하여 반환
        return AdminResponse.RootAdminResponseDTO.builder()
                // TODO: 더미데이터가 이상한 거 같음
                .totalSales(FormatUtil.moneyFormat(totalSales))
                .theaterSales(theaterSalesDTOList)
                .build();
    }
}
