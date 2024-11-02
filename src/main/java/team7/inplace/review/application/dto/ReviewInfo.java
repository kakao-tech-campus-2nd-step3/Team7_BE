package team7.inplace.review.application.dto;

import java.util.Date;
import team7.inplace.review.domain.Review;

public record ReviewInfo(
    Long reviewId,
    boolean likes,
    String comment,
    String userNickname,
    Date createdDate
) {

    public static ReviewInfo from(Review review) {
        return new ReviewInfo(
            review.getId(),
            review.isLiked(),
            review.getComment(),
            review.getUser().getNickname(),
            review.getCreatedDate()
        );
    }
}
