package team7.inplace.video.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.inplace.place.domain.Place;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "video_url")
    private String videoUrl;
    @ManyToOne
    @JoinColumn(name = "influencer_id")
    private Influencer influencer;
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
}
