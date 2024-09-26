package team7.inplace.place.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team7.inplace.place.domain.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    //거리 계산
    @Query(value = "SELECT *, " +
        "(6371 * acos(cos(radians(CAST(:latitude AS DECIMAL(10, 6)))) " +
        "* cos(radians(CAST(latitude AS DECIMAL(10, 6)))) " +
        "* cos(radians(CAST(longitude AS DECIMAL(10, 6))) - radians(CAST(:longitude AS DECIMAL(10, 6)))) "
        +
        "+ sin(radians(CAST(:latitude AS DECIMAL(10, 6)))) " +
        "* sin(radians(CAST(latitude AS DECIMAL(10, 6)))))) AS distance " +
        "FROM places " +
        "ORDER BY distance",
        countQuery = "SELECT count(*) FROM place",  // 총 개수 쿼리
        nativeQuery = true)
    Page<Place> getPlacesByDistance(
        @Param("latitude") String latitude,
        @Param("longitude") String longitude,
        Pageable pageable);
}
