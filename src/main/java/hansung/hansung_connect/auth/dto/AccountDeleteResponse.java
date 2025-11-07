package hansung.hansung_connect.auth.dto;

import java.time.Instant;

public record AccountDeleteResponse(
        Long userId,
        Instant deletedAt
) {
}
