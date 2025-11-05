package hansung.hansung_connect.auth.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        boolean isNewUser,
        boolean needsOnboarding
) {
}
