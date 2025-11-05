package hansung.hansung_connect.auth.service;

import hansung.hansung_connect.auth.dto.LoginResponse;
import hansung.hansung_connect.auth.dto.OnboardingRequest;
import hansung.hansung_connect.auth.kakao.KakaoClient;
import hansung.hansung_connect.auth.kakao.KakaoUserDto;
import hansung.hansung_connect.auth.repository.RefreshTokenRepository;
import hansung.hansung_connect.auth.token.RefreshToken;
import hansung.hansung_connect.auth.token.TokenProvider;
import hansung.hansung_connect.domain.user.entity.User;
import hansung.hansung_connect.domain.user.repository.UserRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoClient kakaoClient;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public LoginResponse loginWithKakao(String kakaoAccessToken) {
        KakaoUserDto ku = kakaoClient.getUser(kakaoAccessToken);

        User user = userRepository.findByKakaoUserId(ku.getId())
                .orElseGet(() -> userRepository.save(
                        User.fromKakao(
                                ku.getId()
                        )
                ));

        // 카카오 프로필 최신화(선택)
        user.updateKakaoProfile(ku.getId());

        boolean isNewUser = !user.isOnboarded()
                && user.getName() == null
                && user.getMajor() == null;

        String access = tokenProvider.createAccessToken(user.getId());
        String refresh = tokenProvider.createRefreshToken(user.getId());

        // 기존 refresh 전부 정리(선택) 후 새 토큰 저장
        refreshTokenRepository.deleteAllByUser_Id(user.getId());
        refreshTokenRepository.save(RefreshToken.builder()
                .token(refresh)
                .user(user)
                .expiresAt(Instant.now().plus(14, ChronoUnit.DAYS))
                .build());

        return new LoginResponse(access, refresh, isNewUser, !user.isOnboarded());
    }

    @Transactional
    public void onboarding(Long userId, OnboardingRequest req) {
        User user = userRepository.findById(userId).orElseThrow();
        user.completeOnboarding(
                req.getName(), req.getMajor(), req.getStudentNumber(),
                req.getAcademicStatus(), req.isJobSeeking(), req.isMentor()
        );
    }

    @Transactional
    public void logout(Long userId, String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}
