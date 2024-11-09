package team7.inplace.oauthToken.application;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.UserErrorCode;
import team7.inplace.oauthToken.application.command.OauthTokenCommand;
import team7.inplace.oauthToken.domain.OauthToken;
import team7.inplace.oauthToken.persistence.OauthTokenRepository;
import team7.inplace.security.util.TokenEncryptionUtil;
import team7.inplace.user.domain.User;

@Service
@RequiredArgsConstructor
public class OauthTokenService {

    private final OauthTokenRepository oauthTokenRepository;
    private final EntityManager entityManager;
    private final TokenEncryptionUtil tokenEncryptionUtil;

    @Transactional(readOnly = true)
    public String findOAuthTokenByUserId(Long userId) throws InplaceException {
        return tokenEncryptionUtil.decrypt(oauthTokenRepository.findByUserId(userId)
            .orElseThrow(() -> InplaceException.of(UserErrorCode.OAUTH_TOKEN_NOT_FOUND))
            .getOauthToken());
    }

    @Transactional
    public void insertOauthToken(OauthTokenCommand oauthTokenCommand) throws InplaceException {
        User userProxy = entityManager.getReference(User.class, oauthTokenCommand.userId());
        OauthToken oauthToken = OauthToken.of(
            tokenEncryptionUtil.encrypt(oauthTokenCommand.oauthToken()),
            oauthTokenCommand.expiresAt(),
            userProxy
        );

        oauthTokenRepository.save(oauthToken);
    }

    @Transactional
    public void updateOauthToken(OauthTokenCommand oauthTokenCommand) throws InplaceException {
        OauthToken oauthToken = oauthTokenRepository.findByUserId(oauthTokenCommand.userId())
            .orElseThrow(() -> InplaceException.of(UserErrorCode.OAUTH_TOKEN_NOT_FOUND));

        oauthToken.updateInfo(
            tokenEncryptionUtil.encrypt(oauthTokenCommand.oauthToken()),
            oauthTokenCommand.expiresAt()
        );
    }

}
