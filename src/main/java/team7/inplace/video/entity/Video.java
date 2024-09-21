package team7.inplace.video.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.inplace.influencer.entity.Influencer;
import team7.inplace.place.domain.Place;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="video_url", nullable = false, columnDefinition = "TEXT")
    private String videoUrl;
    @ManyToOne
    @JoinColumn(name = "influencer_id", nullable = false)
    private Influencer influencer;
    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;
}
