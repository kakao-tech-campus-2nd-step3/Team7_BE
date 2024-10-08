package team7.inplace.favorite.domain;

import jakarta.persistence.*;
import lombok.*;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.user.domain.User;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Favorite {
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

    public void like(boolean tf){
        this.like = tf;
    }
}
