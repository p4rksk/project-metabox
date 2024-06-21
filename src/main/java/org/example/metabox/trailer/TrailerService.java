package org.example.metabox.trailer;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FFmpegLogCallback;
import org.bytedeco.javacv.Frame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class TrailerService {

    private final Path videoLocation = Paths.get(System.getProperty("user.dir"), "video");

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
