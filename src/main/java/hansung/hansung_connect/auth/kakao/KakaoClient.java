package hansung.hansung_connect.auth.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class KakaoClient {
    private final WebClient kakaoWebClient;

    public KakaoUserDto getUser(String kakaoAccessToken) {
        return kakaoWebClient.get()
                .uri("/v2/user/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + kakaoAccessToken)
                .retrieve()
                .bodyToMono(KakaoUserDto.class)
                .block();
    }
}
