package team7.inplace.video.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.inplace.influencer.entity.Influencer;
import team7.inplace.place.entity.Place;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="video_url")
    private String videoUrl;
    @ManyToOne
    @JoinColumn(name = "influencer_id")
    private Influencer influencer;
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
}
