package org.example.metabox.book;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class BookRequest {

    @NoArgsConstructor
    @Data
    public static class PaymentRequestDTO {
        private String impUid;
        private String merchantUid;
        private int totalPrice;
        private int bookPrice;
        private int usedPoint;
        private int screeningInfoId;
        private List<Integer> selectedSeatIds;
    }
}
