package hansung.hansung_connect.auth.converter;

import hansung.hansung_connect.auth.dto.AccountDeleteResponse;
import java.time.Instant;

public class AuthConverter {

    private AuthConverter() {
    }

    public static AccountDeleteResponse toDeleteResponse(Long userId) {
        return new AccountDeleteResponse(userId, Instant.now());
    }
}
