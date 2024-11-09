package team7.inplace.oauthToken.application.command;

import java.time.Instant;

public record OauthTokenCommand(
    String oauthToken,
    Instant expiresAt,
    Long userId
) {

    public static OauthTokenCommand of(String oauthToken, Instant expiresAt, Long userId) {
        return new OauthTokenCommand(oauthToken, expiresAt, userId);
    }
}
