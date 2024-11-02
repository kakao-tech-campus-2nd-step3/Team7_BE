package team7.inplace.review.application.dto;

import team7.inplace.place.domain.Place;
import team7.inplace.review.domain.Review;
import team7.inplace.user.domain.User;

public record ReviewCommand(
    boolean likes,
    String comments
) {

    public static Review toEntity(User user, Place place, ReviewCommand command) {
        return new Review(
            user,
            place,
            command.likes,
            command.comments
        );
    }
}
