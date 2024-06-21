package org.example.metabox.theater;

import lombok.Data;
import org.example.metabox.theater_scrap.TheaterScrap;

import java.util.List;
import java.util.stream.Collectors;

public class TheaterResponse {

    @Data
    public static class TheaterDTO {
        private List<ScrapDTO> scrapDTOList;
        private List<TheaterAreaDTO> theaterAreaDTOList;

        public TheaterDTO(List<TheaterScrap> theaterScrapList, List<Theater> theaterList) {
            this.scrapDTOList = theaterScrapList.stream().map(ScrapDTO::new).collect(Collectors.toList());
            this.theaterAreaDTOList = theaterList.stream()
                    .collect(Collectors.groupingBy(Theater::getAreaCode))
                    .entrySet().stream()
                    .map(entry -> new TheaterAreaDTO(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
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
    }

//    @Data
//    public static class TheaterDTO {
//        private List<ScrapDTO> scrapDTOList;
//        private List<TheaterAreaDTO> theaterAreaDTOList;
//        private List<TheaterListDTO> theaterListDTO;
//
//        public TheaterDTO(List<TheaterScrap> theaterScrapList, List<String[]> areaList, List<Theater> theaterNameList, List<Theater> theaterList) {
//            this.scrapDTOList = theaterScrapList.stream().map(ScrapDTO::new).toList();
//            this.theaterAreaDTOList = areaList.stream().map((String[] area) -> new TheaterAreaDTO(area, theaterNameList)).toList();
//            this.theaterListDTO = theaterList.stream().map(TheaterListDTO::new).toList();
//        }
//
//        @Data
//        public class TheaterAreaDTO {
//            private String areaCode;
//            private String areaName;
//            private List<TheaterNameDTO> theaterNameDTOList;
//
//            @Data
//            private class TheaterNameDTO {
//                private int theaterId;
//                private String theaterName;
//
//                public TheaterNameDTO(Theater theater) {
//                    this.theaterId = theater.getId();
//                    this.theaterName = theater.getName();
//                }
//            }
//
//            private TheaterAreaDTO(String[] area, List<Theater> TheaterNameList) {
//                this.areaCode = area[1];
//                this.areaName = area[0];
//                this.theaterNameDTOList = TheaterNameList.stream().map(TheaterNameDTO::new).toList();
//            }
//        }
//
//        @Data
//        private class TheaterListDTO {
//            private int theaterId;
//            private String theaterName;
//            private String theaterImgFilename;
//
//            public TheaterListDTO(Theater theater) {
//                this.theaterId = theater.getId();
//                this.theaterName = theater.getName();
//                this.theaterImgFilename = theater.getImgFilename();
//            }
//        }
//
//        @Data
//        private class ScrapDTO {
//            private int id;
//            private int theaterId;
//            private String theaterName;
//
//            public ScrapDTO(TheaterScrap theaterScrap) {
//                this.id = theaterScrap.getId();
//                this.theaterId = theaterScrap.getTheater().getId();
//                this.theaterName = theaterScrap.getTheater().getName();
//            }
//        }
//    }
}
