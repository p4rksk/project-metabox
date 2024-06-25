package org.example.metabox.movie;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.example.metabox._core.util.FileUtil;
import org.example.metabox.movie_pic.MoviePic;
import org.example.metabox.movie_pic.MoviePicRepository;
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

    private final Path videoLocation = Paths.get(System.getProperty("user.dir"), "upload");

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
        String status = checkMovieReleaseStatus(movie.getStartDate());

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

    // 영화 등록 메서드
    public Movie addMovie(MovieRequest.movieSavaFormDTO reqDTO) {
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
                .title(reqDTO.getTitle())
                .engTitle(reqDTO.getEngTitle())
                .director(reqDTO.getDirector())
                .actor(reqDTO.getActor())
                .genre(reqDTO.getGenre())
                .info(reqDTO.getInfo())
                .startDate(reqDTO.getStartDate())
                .endDate(reqDTO.getEndDate())
                .imgFilename(posterFileName)
                .description(reqDTO.getDescription())
                .build();

        // Movie 객체를 데이터베이스에 저장하여 PK 값 생성
        movie = movieRepository.save(movie);

        // 스틸컷 이미지 파일 처리
        List<MoviePic> moviePicList = new ArrayList<>();
        MultipartFile[] stills = reqDTO.getStills();
        if (stills != null && stills.length > 0) {
            for (MultipartFile still : stills) {
                try {
                    String stillFileName = fileUtil.saveMovieStill(still);
                    MoviePic moviePic = new MoviePic();
                    moviePic.setImgFilename(stillFileName);
                    moviePic.setMovie(movie); // 외래 키 설정
                    moviePicList.add(moviePic);
                } catch (IOException e) {
                    throw new RuntimeException("스틸컷 이미지 오류", e);
                }
            }
            moviePicRepository.saveAll(moviePicList);
        }
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
}
