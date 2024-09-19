package team7.inplace.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
public class Place {

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

    @Column(columnDefinition = "TEXT")
    private String menuImgUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String longitude;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String latitude;

    //Constructor
    protected Place() {
    }

    @Builder
    public Place(String name, boolean pet, boolean wifi, boolean parking, boolean fordisabled,
        boolean nursery, boolean smokingroom, String address1, String address2,
        String address3, String menuImgUrl, Category category, String longitude, String latitude) {
        this.name = name;
        this.pet = pet;
        this.wifi = wifi;
        this.parking = parking;
        this.fordisabled = fordisabled;
        this.nursery = nursery;
        this.smokingroom = smokingroom;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.menuImgUrl = menuImgUrl;
        this.category = category;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
