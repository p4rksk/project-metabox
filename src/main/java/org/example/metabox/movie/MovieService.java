package org.example.metabox.movie;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.example.metabox._core.util.FileUtil;
import org.example.metabox.movie_pic.MoviePic;
import org.example.metabox.movie_pic.MoviePicRepository;
import org.example.metabox.review.Review;
import org.example.metabox.review.ReviewRepository;
import org.example.metabox.seat.SeatBookRepository;
import org.example.metabox.trailer.Trailer;
import org.example.metabox.trailer.TrailerRepository;
import org.example.metabox.trailer.TrailerService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final FileUtil fileUtil;
    private final MoviePicRepository moviePicRepository;
    private final TrailerRepository trailerRepository;
    private final TrailerService trailerService;
    private final Path videoLocation = Paths.get(System.getProperty("user.dir"), "video");
    private final MovieQueryRepository movieQueryRepository;
    private final ReviewRepository reviewRepository;
    private final SeatBookRepository seatBookRepository;

    // 관리자 무비차트
    public List<MovieResponse.AdminMovieChartDTO> getAdminMovieChart(){
        // 상영 중 또는 개봉 예정인 영화를 예매율 순으로 조회
        List<Object[]> results = movieQueryRepository.getAdminMovieChart();
        List<MovieResponse.AdminMovieChartDTO> adminMovieChartDTOList = new ArrayList<>();

        for (int rank = 0; rank < results.size(); rank++) {
            Object[] result = results.get(rank);
            int movieId = (Integer) result[0];
            String title = (String) result[1];
            String imgFilename = (String) result[2];
            String info = (String) result[3];
            Date startDate = (Date) result[4];
            BigDecimal bookingRateBigDecimal = (BigDecimal) result[5];
            Double bookingRate = bookingRateBigDecimal.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP).doubleValue();

            // 상영 상태 계산 (예: 상영 중, D-날짜)
            String releaseStatus = checkMovieReleaseStatus(startDate);

            MovieResponse.AdminMovieChartDTO dto = new MovieResponse.AdminMovieChartDTO(
                    movieId,
                    title,
                    imgFilename,
                    info.split(",")[0], // 연령 정보
                    startDate,
                    releaseStatus,
                    bookingRate,
                    rank + 1 // rank는 0부터 시작하므로 1을 더해줍니다.
            );
            adminMovieChartDTOList.add(dto);
        }
        return adminMovieChartDTOList;
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
            return "개봉 D-" + dDay;
        }
    }

    // 영화 등록 메서드
    public Movie addMovie(MovieRequest.MovieSavaFormDTO reqDTO) {
        // MultipartFile 객체로부터 포스터 파일 가져오기
        MultipartFile poster = reqDTO.getImgFilename();
        String posterFileName = null;

        try {
            // 포스터 파일 저장 및 파일 이름 설정
            posterFileName = fileUtil.saveMoviePoster(poster);
        } catch (IOException e) {
            // 파일 저장 중 예외 발생 시 런타임 예외로 전환
            throw new RuntimeException("이미지 오류", e);
        }

        // Movie 객체 빌더 패턴으로 생성
        Movie movie = Movie.builder()
                .title(reqDTO.getTitle())               // 영화 제목 설정
                .engTitle(reqDTO.getEngTitle())         // 영어 제목 설정
                .director(reqDTO.getDirector())         // 감독 설정
                .actor(reqDTO.getActor())               // 배우 설정
                .genre(reqDTO.getGenre())               // 장르 설정
                .info(reqDTO.getInfo())                 // 기본 정보 설정
                .startDate(reqDTO.getStartDate())       // 개봉일 설정
                .endDate(reqDTO.getEndDate())           // 상영 종료일 설정
                .imgFilename(posterFileName)            // 포스터 파일 이름 설정
                .description(reqDTO.getDescription())   // 영화 설명 설정
                .build();

        // Movie 객체를 데이터베이스에 저장하여 PK 값 생성
        movie = movieRepository.save(movie);

        // 스틸컷 이미지 파일 처리
        List<MoviePic> moviePicList = new ArrayList<>();
        MultipartFile[] stills = reqDTO.getStills();
        if (stills != null && stills.length > 0) {
            for (MultipartFile still : stills) {
                try {
                    // 스틸컷 이미지 파일을 저장하고 파일명을 반환
                    String stillFileName = fileUtil.saveMovieStill(still);
                    // MoviePic 객체 생성 및 파일명 설정
                    MoviePic moviePic = new MoviePic();
                    moviePic.setImgFilename(stillFileName);
                    moviePic.setMovie(movie); // 외래 키 설정
                    // MoviePic 리스트에 추가
                    moviePicList.add(moviePic);
                } catch (IOException e) {
                    throw new RuntimeException("스틸컷 이미지 오류", e);
                }
            }
            // MoviePic 리스트를 저장
            moviePicRepository.saveAll(moviePicList);
        }
        // Movie 엔티티에 MoviePic 리스트 설정
        movie.setMoviePicList(moviePicList);

        // 트레일러 파일 처리
        List<Trailer> movieTrailerList = new ArrayList<>();
        MultipartFile[] trailers = reqDTO.getTrailers();
        if (trailers != null && trailers.length > 0) {
            for (MultipartFile trailer : trailers) {
                try {
                    String trailerM3u8FileName = uploadAndEncodeVideo(trailer);
                    String mp4FileName = trailer.getOriginalFilename();

                    Trailer movieTrailer = new Trailer();
                    movieTrailer.setStreamingFilename(mp4FileName);
                    movieTrailer.setM3u8Filename(trailerM3u8FileName);
                    movieTrailer.setMovie(movie); // 외래 키 설정
                    movieTrailerList.add(movieTrailer);
                } catch (IOException e) {
                    throw new RuntimeException("트레일러 파일 오류", e);
                }
            }
            trailerRepository.saveAll(movieTrailerList);
        }
        movie.setTrailerList(movieTrailerList);

        return movie;
    }

    public String uploadAndEncodeVideo(MultipartFile file) throws IOException {
        try {
            Files.createDirectories(videoLocation);
        } catch (IOException e) {
            log.warn("Failed to create video directory: " + e.getMessage());
            throw new RuntimeException("Could not create video directory", e);
        }


        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new IllegalArgumentException("파일 이름이 비어 있습니다.");
        }

        String baseName = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
        String inputFilePath = videoLocation.resolve(originalFilename).toString();
        String outputFilePath = videoLocation.resolve(baseName + ".m3u8").toString();

        File inputFile = new File(inputFilePath);
        file.transferTo(inputFile);

        log.info("Starting FFmpegFrameGrabber for input file: " + inputFilePath);
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFilePath)) {
            grabber.start();

            log.info("Number of Streams: " + grabber.getLengthInFrames());
            int totalFrames = grabber.getLengthInFrames();
            double frameRate = grabber.getFrameRate();
            double durationInSeconds = totalFrames / frameRate;
            log.info("Total Frames: " + totalFrames);
            log.info("Frame Rate: " + frameRate);
            log.info("Duration (seconds): " + durationInSeconds);

            try (FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFilePath, grabber.getImageWidth(), grabber.getImageHeight(), grabber.getAudioChannels())) {
                recorder.setFormat("hls");
                recorder.setOption("hls_time", "10");
                recorder.setOption("hls_list_size", "0");
                recorder.setOption("hls_flags", "split_by_time");
                recorder.setOption("hls_wrap", "0");
                recorder.setOption("loglevel", "debug");

                recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
                recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
                recorder.setFrameRate(grabber.getFrameRate());
                recorder.setSampleRate(grabber.getSampleRate());
                recorder.setAudioChannels(grabber.getAudioChannels());

                recorder.start();

                Frame frame;
                int frameNumber = 0;
                while ((frame = grabber.grabFrame()) != null) {
                    frameNumber++;
                    if (frame.image != null) {
                        recorder.record(frame);
                    } else if (frame.samples != null && frame.samples.length > 0) {
                        recorder.recordSamples(frame.sampleRate, frame.audioChannels, frame.samples);
                    }
                }

                recorder.stop();
                grabber.stop();
            } catch (Exception e) {
                log.warn("비디오 인코딩 중 오류가 발생했습니다: " + e.getMessage());
                throw new IOException("비디오 인코딩 중 오류가 발생했습니다: " + e.getMessage(), e);
            }
        }

        return baseName + ".m3u8";
    }

    public List<MovieResponse.UserMovieChartDTO> getMovieChart(){
        // 상영 중 또는 개봉 예정인 영화를 예매율 순으로 조회
        List<Object[]> results = movieQueryRepository.getUserMovieChart();
        List<MovieResponse.UserMovieChartDTO> userMovieChartDTOList = new ArrayList<>();

        for (int rank = 0; rank < results.size(); rank++) {
            Object[] result = results.get(rank);
            int movieId = (Integer) result[0];
            String title = (String) result[1];
            String imgFilename = (String) result[2];
            String info = (String) result[3];
            Date startDate = (Date) result[4];
            BigDecimal bookingRateBigDecimal = (BigDecimal) result[5];
            Double bookingRate = bookingRateBigDecimal.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP).doubleValue();

            // 상영 상태 계산 (예: 상영 중, D-날짜)
            String releaseStatus = checkMovieReleaseStatus(startDate);

            MovieResponse.UserMovieChartDTO dto = new MovieResponse.UserMovieChartDTO(
                    movieId,
                    title,
                    imgFilename,
                    info.split(",")[0], // 연령 정보
                    startDate,
                    releaseStatus,
                    bookingRate,
                    rank + 1 // rank는 0부터 시작하므로 1을 더해줍니다.
            );
            userMovieChartDTOList.add(dto);
        }
        return userMovieChartDTOList;
    }

    @Transactional
    public MovieResponse.MovieDetailDTO getMovieDetail(int movieId) {
        // movieId로 영화 정보 조회
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        Movie movie = optionalMovie.get();

        // movieId로 트레일러 조회
        List<Trailer> trailers = trailerRepository.findTrailersByMovieId(movieId);

        // movieId로 스틸컷 조회
        List<MoviePic> stills = moviePicRepository.findStillsByMovieId(movieId);
        int posterCount = movie.getImgFilename() != null ? 1 : 0;
        Integer stillsCount = stills.size() + posterCount;

        // movieId로 리뷰 조회
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        Integer reviewCount = reviews.size();

        // 영화의 개봉 상태 설정 (임의의 값 사용)
        String releaseStatus = checkMovieReleaseStatus(movie.getStartDate());

        // 예매율
        Double bookingRate = movieQueryRepository.getBookingRate(movie.getId());

        // DTO에 정보 담기
        return MovieResponse.MovieDetailDTO.builder()
                .id(movie.getId())
                .imgFilename(movie.getImgFilename())
                .title(movie.getTitle())
                .engTitle(movie.getEngTitle())
                .releaseStatus(releaseStatus)
                .bookingRate(bookingRate)
                .director(movie.getDirector())
                .actor(movie.getActor())
                .genre(movie.getGenre())
                .info(movie.getInfo())
                .startDate(movie.getStartDate())
                .endDate(movie.getEndDate())
                .description(movie.getDescription())
                .stills(stills.stream().map(MovieResponse.MovieDetailDTO.MoviePicDTO::fromEntity).collect(Collectors.toList()))
                .trailers(trailers.stream().map(MovieResponse.MovieDetailDTO.TrailerDTO::fromEntity).collect(Collectors.toList()))
                .reviews(reviews.stream().map(MovieResponse.MovieDetailDTO.ReviewDTO::fromEntity).collect(Collectors.toList()))
                .reviewCount(reviewCount)
                .stillsCount(stillsCount)
                .build();
    }

    // 영화 수정(기본 정보만)
    @Transactional
    public int editMovieInfo(MovieRequest.MovieInfoEditDTO reqDTO) {
        int result;
        try {
            // 포스터와 같이 업데이트할 때
            MultipartFile poster = reqDTO.getImgFilename();
            if (poster != null && !poster.isEmpty()) {
                String posterFileName = fileUtil.saveMoviePoster(poster);
                reqDTO.setPosterName(posterFileName);
                result = movieQueryRepository.updateMovieById(reqDTO);
            } else {
                // 포스터 없이 업데이트 할 때
                result = movieQueryRepository.updateMovieByIdWithoutPoster(reqDTO);
            }
        } catch (IOException e) {
            throw new RuntimeException("이미지 오류", e);
        }
        if (result != 1) throw new RuntimeException("업데이트 실패");
        return reqDTO.getId();
    }

    // 영화 삭제
    @Transactional
    public void deleteMovie(Integer movieId) {
        movieRepository.deleteById(movieId);
    }

}
