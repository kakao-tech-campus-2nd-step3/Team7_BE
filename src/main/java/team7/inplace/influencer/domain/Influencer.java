package team7.inplace.influencer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Influencer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long influencerId;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 20)
    private String job;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String imgUrl;

    @Builder
    public Influencer(String name, String job, String imgUrl) {
        this.name = name;
        this.job = job;
        this.imgUrl = imgUrl;
    }
}
