package team7.inplace.video.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.place.domain.Place;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@RequiredArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "video_url", nullable = false, columnDefinition = "TEXT")
    @NonNull
    private String videoUrl;
    // 즉시 로딩 적용
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "influencer_id", nullable = false)
    @NonNull
    private Influencer influencer;
    // 즉시 로딩 적용
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "place_id", nullable = false)
    @NonNull
    private Place place;
}
