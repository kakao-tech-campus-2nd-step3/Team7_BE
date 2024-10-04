package team7.inplace.place.persistence;

import java.util.List;
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
        countQuery = "SELECT count(*) FROM places",  // 총 개수 쿼리
        nativeQuery = true)
    Page<Place> getPlacesByDistance(
        @Param("longitude") String longitude,
        @Param("latitude") String latitude,
        Pageable pageable);

    @Query(value = "SELECT p.*, " +
        "(6371 * acos(cos(radians(CAST(:latitude AS DECIMAL(10, 6)))) " +
        "* cos(radians(CAST(p.latitude AS DECIMAL(10, 6)))) " +
        "* cos(radians(CAST(p.longitude AS DECIMAL(10, 6))) - radians(CAST(:longitude AS DECIMAL(10, 6)))) "
        +
        "+ sin(radians(CAST(:latitude AS DECIMAL(10, 6)))) " +
        "* sin(radians(CAST(p.latitude AS DECIMAL(10, 6)))))) AS distance " +
        "FROM places p " +
        "LEFT JOIN video v ON p.id = v.place_id " +
        "LEFT JOIN influencer i ON v.influencer_id = i.id " +
        "WHERE (:categories IS NULL OR p.category IN (:categories)) " +
        "AND (:influencers IS NULL OR i.name IN (:influencers)) " +  // 인플루언서 이름 필터
        "ORDER BY distance",
        countQuery = "SELECT count(*) FROM places p " +
            "JOIN video v ON p.id = v.place_id " +
            "JOIN influencer i ON v.influencer_id = i.id " +
            "WHERE (:categories IS NULL OR p.category IN (:categories)) " +
            "AND (:influencers IS NULL OR i.name IN (:influencers))",  // 총 개수 쿼리
        nativeQuery = true)
    Page<Place> getPlacesByDistanceAndFilters(
        @Param("longitude") String longitude,
        @Param("latitude") String latitude,
        @Param("categories") List<String> categories,
        @Param("influencers") List<String> influencers,
        Pageable pageable);
}
