package team7.inplace.influencer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor // 테스팅을 위한 부분 추가, 협의 하에 다른 방식 채택 가능
public class Influencer {
    /*
     * 더미 데이터 입니다 !!!
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NonNull
    private String name;
    @Column
    @NonNull
    private String job;
    @Column
    @NonNull
    private String imgUrl;
}
