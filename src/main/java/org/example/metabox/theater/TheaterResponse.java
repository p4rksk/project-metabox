package org.example.metabox.theater;

import lombok.Data;
import org.example.metabox.movie.Movie;
import org.example.metabox.screening_info.ScreeningInfo;
import org.example.metabox.theater_scrap.TheaterScrap;

import java.util.List;
import java.util.stream.Collectors;

public class TheaterResponse {


    @Data
    public static class TheaterDTO {
        private List<ScrapDTO> scrapDTOList;
        private List<TheaterAreaDTO> theaterAreaDTOList;
        private List<ScreeningInfoDTO> screeningInfoDTOList;

        public TheaterDTO(List<TheaterScrap> theaterScrapList, List<Theater> theaterList, List<Movie> movieList) {
            this.scrapDTOList = theaterScrapList.stream().map(ScrapDTO::new).collect(Collectors.toList());
            this.theaterAreaDTOList = theaterList.stream()
                    .collect(Collectors.groupingBy(Theater::getAreaCode))
                    .entrySet().stream()
                    .map(entry -> new TheaterAreaDTO(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
            this.screeningInfoDTOList = movieList.stream().map(ScreeningInfoDTO::new).collect(Collectors.toList());
        }

        @Data
        private class ScrapDTO {
            private String id;
            private String theaterId;
            private String theaterName;

            public ScrapDTO(TheaterScrap theaterScrap) {
                this.id = String.valueOf(theaterScrap.getId());
                this.theaterId = String.valueOf(theaterScrap.getTheater().getId());
                this.theaterName = theaterScrap.getTheater().getName();
            }
        }

        @Data
        private class TheaterAreaDTO {
            private String areaCode;
            private String areaName;
            private List<TheaterNameDTO> theaterNameDTOList;

            public TheaterAreaDTO(String areaCode, List<Theater> theaters) {
                this.areaCode = areaCode;
                this.areaName = theaters.isEmpty() ? "" : theaters.get(0).getAreaName();
                this.theaterNameDTOList = theaters.stream().map(TheaterNameDTO::new).collect(Collectors.toList());
            }

            @Data
            private class TheaterNameDTO {
                private int theaterId;
                private String theaterName;

                public TheaterNameDTO(Theater theater) {
                    this.theaterId = theater.getId();
                    this.theaterName = theater.getName();
                }
            }
        }

        @Data
        private class ScreeningInfoDTO {
            private String movieTitle;
            private String movieInfo;
            private List<ScreeningDTO> screeningList;

            @Data
            private class ScreeningDTO {
                private String screeningName;
                private Integer screeningSeatCount;
                private List<ScreeningTimeDTO> screeningTimeList;

                @Data
                private class ScreeningTimeDTO {
                    private String startTime;
                    private Integer currentSeatCount;

                    public ScreeningTimeDTO(ScreeningInfo screeningInfo) {
                        this.startTime = screeningInfo.getStartTime();
                        this.currentSeatCount = screeningInfo.getScreening().getSeatCount() - screeningInfo.getSeatBookList().size();
                    }
                }

                public ScreeningDTO(ScreeningInfo screeningInfo) {
                    this.screeningName = screeningInfo.getScreening().getName();
                    this.screeningSeatCount = screeningInfo.getScreening().getSeatCount();
                    this.screeningTimeList = screeningInfo.getScreening().getScreeningInfoList().stream().map(ScreeningTimeDTO::new).collect(Collectors.toList());
                }
            }

            public ScreeningInfoDTO(Movie movie) {
                this.movieTitle = movie.getTitle();
                this.movieInfo = movie.getInfo();
                this.screeningList = movie.getScreeningInfoList().stream().map(ScreeningDTO::new).collect(Collectors.toList());
            }
        }
    }
}
