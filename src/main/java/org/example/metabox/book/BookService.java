package org.example.metabox.book;

import lombok.RequiredArgsConstructor;
import org.example.metabox.theater.Theater;
import org.example.metabox.theater.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final TheaterRepository theaterRepository;

    public BookResponse.BookDTO theaterAreaList() {
        // 극장 지역 조회
        List<Theater> theaterList = theaterRepository.findAll();
        return new BookResponse.BookDTO(theaterList);
    }
}
