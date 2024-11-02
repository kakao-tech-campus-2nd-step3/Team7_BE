package team7.inplace.review.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.inplace.place.domain.Place;
import team7.inplace.user.domain.User;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "review", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "place_id"})
})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false)
    private boolean isLiked;

    @Column(length = 100)
    private String comment;

    private Date createdDate;

    public Review(User user, Place place, boolean isLiked, String comment) {
        this.user = user;
        this.place = place;
        this.isLiked = isLiked;
        this.comment = comment;
        this.createdDate = new Date();
    }
}
