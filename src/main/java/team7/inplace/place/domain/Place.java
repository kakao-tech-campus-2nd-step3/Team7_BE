package team7.inplace.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity(name = "places")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(columnDefinition = "TEXT")
    private String menuImgUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    private Address address;

    @Embedded
    private Coordinate coordinate;

    @ElementCollection
    private List<OffDay> offDays;

    @ElementCollection
    private List<OpenTime> openPeriods;

    @ElementCollection
    private List<Menu> menus;

}
