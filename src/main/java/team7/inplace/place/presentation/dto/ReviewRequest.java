package team7.inplace.place.presentation.dto;

import team7.inplace.review.application.dto.ReviewCommand;

public record ReviewRequest(
    boolean likes,
    String comments
) {

    public ReviewCommand toCommand() {
        return new ReviewCommand(likes, comments);
    }
}
