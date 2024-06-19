package org.example.metabox.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserService {


    public User loginKakao(String code) {
        // 1.1 RestTemplate 설정
        RestTemplate rt = new RestTemplate();

        // 1.2 http header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 1.3 http body 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "7b3698608adc154feafed29a780bcd5b");
        body.add("redirect_uri", "http://localhost:8080/oauth/callback/kakao");
        body.add("code", code);

        // 1.4 body+header 객체 만들기
        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        // 1.5 api 요청하기 (토큰 받기)
        ResponseEntity<UserResponse.TokenDTO> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                UserResponse.TokenDTO.class);

        // 1.6 값 확인
        System.out.println(response.getBody().toString());

        return null;

    }
}
