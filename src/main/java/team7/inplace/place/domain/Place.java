package team7.inplace.place.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "places")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId;

    @Column(nullable = false, length = 50)
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

    @Column(nullable = false, length = 50)
    private String address1;

    @Column(nullable = false, length = 50)
    private String address2;

    @Column(nullable = false, length = 50)
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

    @ElementCollection
    @CollectionTable(name = "place_times", joinColumns = @JoinColumn(name = "place_id"))
    private List<PlaceTime> timeList;

    @ElementCollection
    @CollectionTable(name = "menus", joinColumns = @JoinColumn(name = "place_id"))
    private List<Menu> menuList;

}
