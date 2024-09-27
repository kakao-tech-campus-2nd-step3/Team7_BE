package team7.inplace.video.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import team7.inplace.influencer.entity.Influencer;
import team7.inplace.place.domain.Place;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@RequiredArgsConstructor // 테스팅을 위한 부분 추가, 협의 하에 다른 방식 채택 가능
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "video_url", nullable = false, columnDefinition = "TEXT")
    @NonNull
    private String videoUrl;
    @ManyToOne
    @JoinColumn(name = "influencer_id", nullable = false)
    @NonNull
    private Influencer influencer;
    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    @NonNull
    private Place place;
}
