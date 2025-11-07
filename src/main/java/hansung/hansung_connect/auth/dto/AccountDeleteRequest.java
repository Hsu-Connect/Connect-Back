package hansung.hansung_connect.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AccountDeleteRequest(
        @NotBlank String refreshToken  // 본인 확인 및 탈취 방지 목적
) {
}
