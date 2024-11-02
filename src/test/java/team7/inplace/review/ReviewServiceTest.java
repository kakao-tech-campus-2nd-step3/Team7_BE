package team7.inplace.review;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.ReviewErrorCode;
import team7.inplace.place.domain.Place;
import team7.inplace.place.persistence.PlaceRepository;
import team7.inplace.review.application.ReviewService;
import team7.inplace.review.application.dto.ReviewCommand;
import team7.inplace.review.domain.Review;
import team7.inplace.review.persistence.ReviewRepository;
import team7.inplace.security.util.AuthorizationUtil;
import team7.inplace.user.domain.Role;
import team7.inplace.user.domain.User;
import team7.inplace.user.domain.UserType;
import team7.inplace.user.persistence.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Long userId;
    private Long placeId;
    private String comment;
    private boolean isLiked;
    private User user;
    private Place place;
    private ReviewCommand command;

    @BeforeEach
    public void setUp() {
        userId = 1L;
        placeId = 1L;
        comment = "comment";
        isLiked = true;

        user = new User("name", "password", "nickname", UserType.KAKAO, Role.USER);
        place = new Place("name", "facility", "menuImgUrl", "category",
            "Address 1|Address 2|Address 3", "x", "y",
            Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
            Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
            Arrays.asList("삼겹살|5000|false|menu.url|description",
                "돼지찌개|7000|true|menu.url|description"),
            LocalDateTime.of(2024, 3, 28, 5, 30),
            Arrays.asList("menuBoard1.url", "menuBoard2.url")
        );
        command = new ReviewCommand(isLiked, comment);

    }

    @Test
    public void createReviewTest() {
        MockedStatic<AuthorizationUtil> authorizationUtil = mockStatic(AuthorizationUtil.class);

        given(AuthorizationUtil.getUserId()).willReturn(userId);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(placeRepository.findById(placeId)).willReturn(Optional.of(place));
        given(reviewRepository.existsByUserIdAndPlaceId(userId, placeId)).willReturn(false);

        assertDoesNotThrow(() -> reviewService.createReview(placeId, command));

        verify(reviewRepository).save(any(Review.class));

        authorizationUtil.close();
    }

    @Test
    @DisplayName("장소에 대해 사용자의 리뷰가 이미 존재하면 예외 발생")
    public void createReviewTest_ReviewAlreadyExists() {
        MockedStatic<AuthorizationUtil> authorizationUtil = mockStatic(AuthorizationUtil.class);

        given(AuthorizationUtil.getUserId()).willReturn(userId);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(placeRepository.findById(placeId)).willReturn(Optional.of(place));
        given(reviewRepository.existsByUserIdAndPlaceId(userId, placeId)).willReturn(true);

        assertThatThrownBy(() -> reviewService.createReview(placeId, command))
            .isInstanceOf(InplaceException.class)
            .hasMessage(ReviewErrorCode.REVIEW_ALREADY_EXISTS.getMessage());

        authorizationUtil.close();
    }
}
