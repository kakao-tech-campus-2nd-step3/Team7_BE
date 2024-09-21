package team7.inplace.influencer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Influencer {
    /*
     * 더미 데이터 입니다 !!!
     */
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String job;
    @Column
    private String imgUrl;
}
