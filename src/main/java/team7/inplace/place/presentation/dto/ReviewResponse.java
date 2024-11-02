package team7.inplace.place.presentation.dto;

import java.util.Date;
import team7.inplace.review.application.dto.ReviewInfo;

public record ReviewResponse(
    Long reviewId,
    boolean likes,
    String comment,
    String userNickname,
    Date createdDate
) {

    public static ReviewResponse from(ReviewInfo reviewInfo) {
        return new ReviewResponse(
            reviewInfo.reviewId(),
            reviewInfo.likes(),
            reviewInfo.comment(),
            reviewInfo.userNickname(),
            reviewInfo.createdDate()
        );
    }
}
