package team7.inplace.likedPlace.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import team7.inplace.place.domain.Place;
import team7.inplace.user.domain.User;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "liked_places")
public class LikedPlace {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "place_id")
    private Place place;

    @Column
    private boolean isLiked = false;

    public void updateLike(boolean like) {
        this.isLiked = like;
    }

}
