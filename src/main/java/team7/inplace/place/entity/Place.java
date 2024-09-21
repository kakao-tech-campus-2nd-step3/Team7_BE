package team7.inplace.place.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Place {
    /*
     * 더미 데이터 입니다 !!!
     */
    @Id
    private Long id;
    @Column
    private String name;

    protected Place(){

    }
}
