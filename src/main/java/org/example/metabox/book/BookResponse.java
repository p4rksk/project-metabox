package org.example.metabox.book;

import lombok.Data;
import org.example.metabox.theater.Theater;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BookResponse {

    @Data
    public static class BookDTO {

        private List<TheaterAreaDTO> theaterAreaDTOList;

        public BookDTO(List<Theater> theaterList) {

            this.theaterAreaDTOList = theaterList.stream()
                    .collect(Collectors.groupingBy(Theater::getAreaCode))
                    .entrySet().stream()
                    .map(entry -> new TheaterAreaDTO(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
        }

        @Data
        private class TheaterAreaDTO {
            private String areaCode;
            private String areaName;
            private List<TheaterAreaDTO.TheaterNameDTO> theaterNameDTOList;

            public TheaterAreaDTO(String areaCode, List<Theater> theaters) {
                this.areaCode = areaCode;
                this.areaName = theaters.isEmpty() ? "" : theaters.get(0).getAreaName();

                int size = theaters.size();
                this.theaterNameDTOList = IntStream.range(0, size)
                        .mapToObj(i -> new TheaterNameDTO(theaters.get(i), i == size - 1))
                        .collect(Collectors.toList());
            }

            @Data
            private class TheaterNameDTO {
                private int theaterId;
                private String theaterName;
                private boolean isLast;

                public TheaterNameDTO(Theater theater, boolean isLast) {
                    this.theaterId = theater.getId();
                    this.theaterName = theater.getName();
                    this.isLast = isLast;
                }
            }
        }
    }
}
