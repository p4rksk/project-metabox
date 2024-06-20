package org.example.metabox.theater;

import lombok.RequiredArgsConstructor;
import org.example.metabox._core.errors.exception.Exception400;
import org.example.metabox._core.errors.exception.Exception404;
import org.example.metabox.screening_info.ScreeningInfoRepository;
import org.example.metabox.theater_scrap.TheaterScrap;
import org.example.metabox.theater_scrap.TheaterScrapRepository;
import org.example.metabox.theater_scrap.TheaterScrapResponse;
import org.example.metabox.user.SessionUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TheaterService {
    private final TheaterRepository theaterRepository;
    private final TheaterScrapRepository theaterScrapRepository;
    private final ScreeningInfoRepository screeningInfoRepository;

    public List<TheaterScrapResponse.TheaterScrapDTO> movieSchedule(SessionUser sessionUser) {
        if (sessionUser != null) {
            List<TheaterScrapResponse.TheaterScrapDTO> respDTO = theaterScrapRepository.findByUserId(sessionUser.getId());
            System.out.println(respDTO.size());
            while (respDTO.size() < 5) {
                respDTO.add(new TheaterScrapResponse.TheaterScrapDTO(0, "")); // Add empty items
            }
            respDTO.stream().forEach(System.out::println);
            return respDTO;
        }
        List<TheaterScrapResponse.TheaterScrapDTO> respDTO = new ArrayList<>();
        while(respDTO.size() < 5) {
            respDTO.add(new TheaterScrapResponse.TheaterScrapDTO(0, ""));
        }
        return respDTO;
    }
}
