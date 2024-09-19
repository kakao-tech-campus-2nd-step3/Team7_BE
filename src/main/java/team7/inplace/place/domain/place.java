package team7.inplace.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
public class place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "BIGINT(20)")
    private Long placeId;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String name;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean pet;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean wifi;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean parking;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean fordisabled;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean nursery;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean smokingroom;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String address1;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String address2;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String address3;

    @Column
    private String menuImgUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String longitude;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String latitude;
}
