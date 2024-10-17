package team7.inplace.favoriteInfluencer.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.user.domain.User;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
public class FavoriteInfluencer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    @NonNull
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @NonNull
    @JoinColumn(name = "influencer_id")
    private Influencer influencer;
    @Column
    private boolean isLiked = false;

    public void updateLike(boolean like) {
        this.isLiked = like;
    }
}
