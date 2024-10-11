package team7.inplace.token.application.dto;

public class TokenCommand {

    public record ReIssued(
        String accessToken,
        String refreshToken
    ) {

        public static ReIssued of(String reIssuedRefreshToken, String reIssuedAccessToken) {
            return new ReIssued(reIssuedRefreshToken, reIssuedAccessToken);
        }
    }
}
