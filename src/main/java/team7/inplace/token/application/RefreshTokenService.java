package team7.inplace.token.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.AuthorizationErrorCode;
import team7.inplace.security.util.JwtUtil;
import team7.inplace.token.domain.RefreshToken;
import team7.inplace.token.persistence.RefreshTokenRepository;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public boolean isInvalidRefreshToken(String refreshToken) throws InplaceException {
        String username = jwtUtil.getUsername(refreshToken);
        return !refreshTokenRepository.findById(username)
            .orElseThrow(() -> InplaceException.of(AuthorizationErrorCode.INVALID_TOKEN))
            .getRefreshToken().equals(refreshToken);
    }

    public void saveRefreshToken(String username, String token) {
        RefreshToken refreshToken = new RefreshToken(username, token);
        refreshTokenRepository.save(refreshToken);
    }

}
