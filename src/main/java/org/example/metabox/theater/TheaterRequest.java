package org.example.metabox.theater;

import lombok.AllArgsConstructor;
import lombok.Data;

public class TheaterRequest {

    @AllArgsConstructor
    @Data
    public class ScheduleDTO {
        private String areaCode;
    }
}
