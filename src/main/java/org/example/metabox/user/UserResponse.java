package org.example.metabox.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox._core.util.ScopeDeserializer;
import org.example.metabox.movie.MovieResponse;
import org.example.metabox.movie_scrap.MovieScrapResponse;
import org.example.metabox.review.ReviewResponse;
import org.example.metabox.theater.Theater;
import org.example.metabox.theater_scrap.TheaterScrap;
import org.example.metabox.trailer.Trailer;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class UserResponse {

    //    비회원 예매 조회
    @Data
    public static class GuestCheckDTO {
        private UserDTO userDTO;
        private List<TicketingDTO> ticketingDTO = new ArrayList<>();

        public GuestCheckDTO(UserDTO userDTO, List<TicketingDTO> ticketingDTO) {
            this.userDTO = userDTO;
            this.ticketingDTO = ticketingDTO;
        }

        @Data
        public static class TicketingDTO {
            private Integer id;     // book pk
            private String title;   //영화 제목
            private String imgFilename;
            private String engTitle;   //영화 제목
            private Date date;     // 관람일시 타입 확인 필요
            private String startTime;   // 시작 시간
            private String endTime;     // 종료 시간
            private String name;        // 몇 관인지
            private String theaterName; // METABOX 어느 지점인지
            private Integer userId;
            private String ageInfo;     // 전체, 12세, 15세, 19세
            private String ageColor;
            // 애 한 번 돌때 여러번 돌고, 일치하는 것만 나오게
            private Integer totalPrice;
            private List<String> seatCodes = new ArrayList<>();

            @Builder
            public TicketingDTO(Integer id, String title, String imgFilename, String engTitle, Date date, String startTime, String endTime, String name, String theaterName, Integer userId, String ageInfo, List<TotalPriceDTO> totalPriceDTOS, List<SeatDTO> seatDTOS) {
                this.id = id;
                this.title = title;
                this.imgFilename = imgFilename;
                this.engTitle = engTitle;
                this.date = date;
                this.startTime = startTime;
                this.endTime = endTime;
                this.name = name;
                this.theaterName = theaterName;
                this.userId = userId;
                this.ageInfo = ageInfo;
                this.ageColor = classColor();

                this.totalPrice = totalPriceDTOS.stream().filter(totalPrice -> totalPrice.getBookId().equals(this.id))
                        .map(totalPriceDTO -> totalPriceDTO.getTotalPrice())
                        .findFirst().orElse(0);
                this.seatCodes = seatDTOS.stream().filter(seat -> seat.getBookId().equals(this.id))
                        .map(seatDTO -> seatDTO.getSeatCode())
                        .toList();
            }

            public String classColor() {
                if ("12".equals(ageInfo)) {
                    return "age-info-yellow";
                } else if ("15".equals(ageInfo)) {
                    return "age-info-blue";
                } else if ("전".equals(ageInfo)) {
                    return "age-info-green";
                } else if ("19".equals(ageInfo)) {
                    return "age-info-red";
                } else {
                    return "";
                }

            }
        }

        @Data
        public static class UserDTO {
            private Integer guestId;
            private String name;
            private String phone;
            private String password;
            private String bookNum;

            public UserDTO(Integer guestId, String name, String phone, String password, String bookNum) {
                this.guestId = guestId;
                this.name = name;
                this.phone = phone;
                this.password = password;
                this.bookNum = bookNum;
            }
        }

        @Data
        public static class TotalPriceDTO {
            private Integer bookId;
            private Integer totalPrice;

            @Builder
            public TotalPriceDTO(Integer bookId, Integer totalPrice) {
                this.bookId = bookId;
                this.totalPrice = totalPrice;
            }
        }

        @Data
        public static class SeatDTO {
            private Integer bookId;
            private String seatCode;   //좌석

            @Builder
            public SeatDTO(Integer bookId, String seatCode) {
                this.bookId = bookId;
                this.seatCode = seatCode;
            }
        }

    }


    // ajax 용
    @Data
    public static class TheaterNameDTO {
        private Integer theaterId;
        private String theaterName;     // metaBox 강남

        public TheaterNameDTO(Integer theaterId, String theaterName) {
            this.theaterId = theaterId;
            this.theaterName = theaterName;
        }
    }

    @Data
    public static class MyPageHomeDTO {
        private UserDTO userDTO;
        private List<TicketingDTO> ticketingDTO = new ArrayList<>();
        private List<TheaterDTO> theaterDTOS = new ArrayList<>();
        private List<TheaterScrapDTO> theaterScrapDTOS = new ArrayList<>();
        private int ticketCount;

        public MyPageHomeDTO(UserDTO userDTO, List<TicketingDTO> ticketingDTO, List<TheaterDTO> theaterDTOS, List<TheaterScrapDTO> theaterScrapDTOS, int ticketCount) {
            this.userDTO = userDTO;
            this.ticketingDTO = ticketingDTO;
            this.theaterDTOS = theaterDTOS;
            this.theaterScrapDTOS = theaterScrapDTOS;
            this.ticketCount = ticketCount;
        }

        @Data
        public static class TheaterScrapDTO {
            private Integer theaterId;     // TheaterScrapId (theaterId)
            private String theaterName;    //theaterName

            public TheaterScrapDTO(Integer theaterId, String name) {
                this.theaterId = theaterId;
                this.theaterName = name;
            }
        }

        @Data
        public static class TheaterDTO {
            private Integer id;       // 필요해여
            private String areaName;
            private List<TheaterNameDTO> theaterNameDTOS = new ArrayList<>();

            public TheaterDTO(Integer id, String areaName, List<TheaterNameDTO> theaterNameDTOS) {
                this.id = id;
                this.areaName = areaName;
                this.theaterNameDTOS = theaterNameDTOS;
//                this.theaterNameDTOS = theaterNameDTOS.stream()
//                        .filter(theaterNameDTO -> theaterNameDTO.getTheaterName().equals(this.areaName))
//                        .collect(Collectors.toList());
            }

            @Data
            public static class TheaterNameDTO {
                private Integer theaterId;
                private String theaterName;

                public TheaterNameDTO(Theater theater) {
                    this.theaterId = theater.getId();
                    this.theaterName = theater.getName();
                }
            }
        }

        // 마이페이지 내 예매내역
        @Data
        public static class TicketingDTO {
            private Integer id;     // book pk
            private String title;   //영화 제목
            private String imgFilename;
            private Date date;     // 관람일시 타입 확인 필요
            private String startTime;   // 시작 시간
            private String endTime;     // 종료 시간
            private String name;        // 몇 관인지
            private String theaterName; // METABOX 어느 지점인지
            private Integer userId;
            private String bookNum;
            private String engTitle;
            private String ageInfo;     // 전체, 12세, 15세, 19세
            private String ageColor;

            @Builder
            public TicketingDTO(Integer id, String title, String imgFilename, Date date, String startTime, String endTime, String name, String theaterName, Integer userId, String bookNum, String engTitle, String ageInfo, String ageColor) {
                this.id = id;
                this.title = title;
                this.imgFilename = imgFilename;
                this.date = date;
                this.startTime = startTime;
                this.endTime = endTime;
                this.name = name;
                this.theaterName = theaterName;
                this.userId = userId;
                this.bookNum = bookNum;
                this.engTitle = engTitle;
                this.ageInfo = ageInfo;
                this.ageColor = classColor();
            }


            public String classColor() {
                if ("12".equals(ageInfo)) {
                    return "age-info-yellow";
                } else if ("15".equals(ageInfo)) {
                    return "age-info-blue";
                } else if ("전".equals(ageInfo)) {
                    return "age-info-green";
                } else if ("19".equals(ageInfo)) {
                    return "age-info-red";
                } else {
                    return "";
                }

            }

        }

        @Data
        static class UserDTO {
            private Integer id;
            private String name;
            private String imgFilename;
            private String nickname;
            private Integer point;

            public UserDTO(User sessionUser) {
                this.id = sessionUser.getId();
                this.name = sessionUser.getName();
                this.imgFilename = sessionUser.getImgFilename();
                this.nickname = sessionUser.getNickname();
                this.point = sessionUser.getPoint();
            }
        }

    }


    // 메인 페이지 무비차트, 상영예정작
    @Data
    public static class MainChartDTO {
        // 예매율 1위 트레일러 ID
        private Integer trailerId;

        // 무비차트
        private List<MainMovieChartDTO> movieCharts = new ArrayList<>();
        // 상영예정작
        private List<ToBeChartDTO> toBeCharts = new ArrayList<>();

        @Builder
        public MainChartDTO(List<MainMovieChartDTO> movieCharts, List<ToBeChartDTO> toBeCharts, Integer trailerId) {
            this.movieCharts = movieCharts;
            this.toBeCharts = toBeCharts;
            this.trailerId = trailerId;
        }

        // 메인 트레일러 파트
        @Data
        @NoArgsConstructor
        public static class TrailerDTO {
            private int id;
            private String fileName;
            private String masterM3u8name;

            @Builder
            public TrailerDTO(int id, String fileName, String masterM3u8name) {
                this.id = id;
                this.fileName = fileName;
                this.masterM3u8name = masterM3u8name;
            }
        }

        // 메인의 무비차트
        @Data
        public static class MainMovieChartDTO {
            private Integer movieId;
            private String imgFilename;
            private String title;
            private Double ticketSales;     // 예매율 - 쿼리에서 계산해서 가져오기
            private Integer rank;       //순위
            private String ageInfo;     // 전체, 12세, 15세, 19세
            private String ageColor;

            @Builder
            public MainMovieChartDTO(Integer movieId, String imgFilename, String title, Double ticketSales, Integer rank, String ageInfo) {
                this.movieId = movieId;
                this.imgFilename = imgFilename;
                this.title = title;
                this.ticketSales = ticketSales;
                this.rank = rank;
                this.ageInfo = ageInfo;
                this.ageColor = classColor();
            }

            public String classColor() {
                if ("12".equals(ageInfo)) {
                    return "age-info-yellow";
                } else if ("15".equals(ageInfo)) {
                    return "age-info-blue";
                } else if ("전".equals(ageInfo)) {
                    return "age-info-green";
                } else if ("19".equals(ageInfo)) {
                    return "age-info-red";
                } else {
                    return "";
                }

            }

        }

        // 메인의 상영 예정작
        @Data
        public static class ToBeChartDTO {
            private Integer movieId;
            private String imgFilename;
            private String title;
            private Integer dDay;   //개봉일까지
            private String startDate; //개봉일자
            private String ageInfo;     // 전체, 12세, 15세, 19세
            private String ageColor;


            @Builder
            public ToBeChartDTO(Integer movieId, String imgFilename, String title, Integer dDay, Date startDate, String ageInfo) {
                this.movieId = movieId;
                this.imgFilename = imgFilename;
                this.title = title;
                this.dDay = dDay;
                this.startDate = getStartDateFormat(startDate);
                this.ageInfo = ageInfo;
                this.ageColor = classColor();
            }

            public String classColor() {
                if ("12".equals(ageInfo)) {
                    return "age-info-yellow";
                } else if ("15".equals(ageInfo)) {
                    return "age-info-blue";
                } else if ("전".equals(ageInfo)) {
                    return "age-info-green";
                } else if ("19".equals(ageInfo)) {
                    return "age-info-red";
                } else {
                    return "";
                }

            }


            // 날짜 가공
            public String getStartDateFormat(Date startDate) {
                SimpleDateFormat formatter = new SimpleDateFormat("M월 d일");
                return formatter.format(startDate);
            }

        }
    }


    //마이페이지 DetailBook
    @Data
    public static class DetailBookDTO {
        private UserDTO userDTO;
        private List<MovieChartDTO> movieCharts = new ArrayList<>();
        private List<TheaterDTO> theaterDTOS = new ArrayList<>();
        private List<TheaterScrapDTO> theaterScrapDTOS = new ArrayList<>();
        private List<TicketingDTO> ticketingDTO = new ArrayList<>();
//        private List<SeatDTO> seatDTOS = new ArrayList<>();
//        private List<TotalPriceDTO> totalPriceDTO;

        @Builder
        public DetailBookDTO(UserDTO userDTO, List<MovieChartDTO> movieCharts, List<TheaterDTO> theaterDTOS, List<TheaterScrapDTO> theaterScrapDTOS, List<TicketingDTO> ticketingDTO) {
            this.userDTO = userDTO;
            this.movieCharts = movieCharts;
            this.theaterDTOS = theaterDTOS;
            this.theaterScrapDTOS = theaterScrapDTOS;
            this.ticketingDTO = ticketingDTO;
        }

        // 마이페이지 아직 관람 안한 예매내역
        @Data
        public static class TicketingDTO {
            private Integer id;     // book pk
            private String title;   //영화 제목
            private String imgFilename;
            private String engTitle;   //영화 제목
            private Date date;     // 관람일시 타입 확인 필요
            private String startTime;   // 시작 시간
            private String endTime;     // 종료 시간
            private String name;        // 몇 관인지
            private String theaterName; // METABOX 어느 지점인지
            private Integer userId;
            private String ageInfo;     // 전체, 12세, 15세, 19세
            private String ageColor;
            // 애 한 번 돌때 여러번 돌고, 일치하는 것만 나오게
            private String bookNum;
            private Integer totalPrice;
            private List<String> seatCodes = new ArrayList<>();

            @Builder
            public TicketingDTO(Integer id, String title, String imgFilename, String engTitle, Date date, String startTime, String endTime, String name, String theaterName, Integer userId, String ageInfo, List<TotalPriceDTO> totalPriceDTOS, List<SeatDTO> seatDTOS, String bookNum) {
                this.id = id;
                this.title = title;
                this.imgFilename = imgFilename;
                this.engTitle = engTitle;
                this.date = date;
                this.startTime = startTime;
                this.endTime = endTime;
                this.name = name;
                this.theaterName = theaterName;
                this.userId = userId;
                this.ageInfo = ageInfo;
                this.ageColor = classColor();
                this.bookNum = bookNum;

                this.totalPrice = totalPriceDTOS.stream().filter(totalPrice -> totalPrice.getBookId().equals(this.id))
                        .map(totalPriceDTO -> totalPriceDTO.getTotalPrice())
                        .findFirst().orElse(0);
                this.seatCodes = seatDTOS.stream().filter(seat -> seat.getBookId().equals(this.id))
                        .map(seatDTO -> seatDTO.getSeatCode())
                        .toList();
            }

            public String classColor() {
                if ("12".equals(ageInfo)) {
                    return "age-info-yellow";
                } else if ("15".equals(ageInfo)) {
                    return "age-info-blue";
                } else if ("전".equals(ageInfo)) {
                    return "age-info-green";
                } else if ("19".equals(ageInfo)) {
                    return "age-info-red";
                } else {
                    return "";
                }

            }
        }

        @Data
        public static class TotalPriceDTO {
            private Integer bookId;
            private Integer totalPrice;

            @Builder
            public TotalPriceDTO(Integer bookId, Integer totalPrice) {
                this.bookId = bookId;
                this.totalPrice = totalPrice;
            }
        }

        @Data
        public static class SeatDTO {
            private Integer bookId;
            private String seatCode;   //좌석

            @Builder
            public SeatDTO(Integer bookId, String seatCode) {
                this.bookId = bookId;
                this.seatCode = seatCode;
            }
        }

        @Data
        public static class TheaterScrapDTO {
            private Integer theaterId;     // TheaterScrapId (theaterId)
            private String name;    //theaterName

            public TheaterScrapDTO(Integer theaterId, String name) {
                this.theaterId = theaterId;
                this.name = name;
            }
        }

        @Data
        public static class TheaterDTO {
            private Integer id;       // 필요해여
            private String areaName;
            private List<TheaterNameDTO> theaterNameDTOS = new ArrayList<>();

            public TheaterDTO(Integer id, String areaName, List<TheaterNameDTO> theaterNameDTOS) {
                this.id = id;
                this.areaName = areaName;
                this.theaterNameDTOS = theaterNameDTOS;
//                this.theaterNameDTOS = theaterNameDTOS.stream()
//                        .filter(theaterNameDTO -> theaterNameDTO.getTheaterName().equals(this.areaName))
//                        .collect(Collectors.toList());
            }

            @Data
            public static class TheaterNameDTO {
                private Integer theaterId;
                private String theaterName;

                public TheaterNameDTO(Theater theater) {
                    this.theaterId = theater.getId();
                    this.theaterName = theater.getName();
                }
            }
        }

        // today best 무비차트 뿌리는 DTO
        @Data
        public static class MovieChartDTO {
            private Integer movieId;     //movie Id
            private String imgFilename;
            private String title;
            private Date startDate;

            private Double ticketSales;     // 예매율 - 계산해서 가져오기

            @Builder
            public MovieChartDTO(Integer movieId, String imgFilename, String title, Date startDate, Double ticketSales) {
                this.movieId = movieId;
                this.imgFilename = imgFilename;
                this.title = title;
                this.startDate = startDate;
                this.ticketSales = ticketSales;
            }

        }

        @Data
        static class UserDTO {
            private Integer id;
            private String name;
            private String imgFilename;
            private String nickname;
            private Integer point;

            public UserDTO(User sessionUser) {
                this.id = sessionUser.getId();
                this.name = sessionUser.getName();
                this.imgFilename = sessionUser.getImgFilename();
                this.nickname = sessionUser.getNickname();
                this.point = sessionUser.getPoint();
            }
        }

    }


    @Data
    public static class TokenDTO {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("refresh_token")
        private String refreshToken;
        @JsonProperty("expires_in")
        private Integer expiresIn;
        @JsonDeserialize(using = ScopeDeserializer.class)
        private List<String> scope;
        @JsonProperty("refresh_token_expires_in")
        private Integer refreshTokenExpiresIn;
    }

    @Data
    public static class KakaoUserDTO {
        private Long id;
        @JsonProperty("connected_at")
        private Timestamp connectedAt;
        private Properties properties;

        @Data
        class Properties {
            private String nickname;
            @JsonProperty("profile_image")
            private String profileImage;

        }
    }

    @Data
    public static class NaverUserDTO {
        private Response response;

        @Data
        class Response {
            private String id;
            private String nickname;
            @JsonProperty("profile_image")
            private String profileImage;
            private String age;     // 연령대라서 String으로 받아봄
            private String gender;
            private String email;
            private String mobile;
            @JsonProperty("mobile_e164")
            private String mobileE164;  // 국제 전화번호 형식. 필요없어보이지만 폰번호 받으면 같이 날아오는 듯함
            private String name;    //사람 이름
            private String birthday;    // 일단 문자열로 들어와서..
            private String birthyear;
        }
    }


    // 내가 본 영화내역
    @Data
    public static class MyPageDetailDTO {
        private List<TicketedDTO> tickets;
        private Integer tickedCount;

        @Builder
        public MyPageDetailDTO(List<TicketedDTO> tickets, Integer tickedCount) {
            this.tickets = tickets;
            this.tickedCount = tickedCount;
        }

        // 마이페이지 아직 관람 안한 예매내역
        @Data
        public static class TicketedDTO {
            private Integer id;     // book pk
            private String title;   //영화 제목
            private String imgFilename;
            private Date date;     // 관람일시 타입 확인 필요
            private String startTime;   // 시작 시간
            private String endTime;     // 종료 시간
            private String name;        // 몇 관인지
            private String theaterName; // METABOX 어느 지점인지
            private Integer userId;
            private String bookNum;

            @Builder
            public TicketedDTO(Integer id, String title, String imgFilename, Date date, String startTime, String endTime, String name, String theaterName, Integer userId, String bookNum) {
                this.id = id;
                this.title = title;
                this.imgFilename = imgFilename;
                this.date = date;
                this.startTime = startTime;
                this.endTime = endTime;
                this.name = name;
                this.theaterName = theaterName;
                this.userId = userId;
                this.bookNum = bookNum;

            }
        }
    }
}
