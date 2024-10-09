package team7.inplace.userFavoriteInfluencer.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.user.domain.User;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
public class UserFavoriteInfluencer {
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
    private boolean like = false;

    public void check(boolean check) {
        if (check) {
            like();
            return;
        }
        dislike();
    }

    private void like() {
        this.like = true;
    }

    private void dislike() {
        this.like = false;
    }
}
