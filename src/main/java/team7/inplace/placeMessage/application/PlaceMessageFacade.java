package team7.inplace.placeMessage.application;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import team7.inplace.global.annotation.Facade;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.UserErrorCode;
import team7.inplace.oauthToken.application.OauthTokenService;
import team7.inplace.place.application.PlaceService;
import team7.inplace.placeMessage.application.command.PlaceMessageCommand;
import team7.inplace.security.util.AuthorizationUtil;

@Facade
@RequiredArgsConstructor
public class PlaceMessageFacade {

    private final PlaceService placeService;
    private final OauthTokenService oauthTokenService;
    private final KakaoMessageService kakaoMessageService;
    private final ScheduledExecutorService scheduledExecutorService;

    public void sendPlaceMessage(Long placeId) throws InplaceException {
        if (AuthorizationUtil.isNotLoginUser()) {
            throw InplaceException.of(UserErrorCode.NOT_FOUND);
        }

        String oauthToken = oauthTokenService.findOAuthTokenByUserId(AuthorizationUtil.getUserId());
        PlaceMessageCommand placeMessageCommand = placeService.getPlaceMessageCommand(placeId);
        kakaoMessageService.sendLocationMessageToMe(oauthToken, placeMessageCommand);
        scheduledExecutorService.schedule(
            () -> kakaoMessageService.sendFeedMessageToMe(oauthToken, placeMessageCommand), 3,
            TimeUnit.DAYS);
    }
}
