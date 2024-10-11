package team7.inplace.place.persistence;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import team7.inplace.influencer.domain.QInfluencer;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.Place;
import team7.inplace.place.domain.QPlace;
import team7.inplace.video.domain.QVideo;

@Repository
@AllArgsConstructor
public class PlaceCustomRepositoryImpl implements PlaceCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Place> findPlacesByDistance(String longitude, String latitude, Pageable pageable) {
        QPlace place = QPlace.place;

        NumberTemplate<Double> distanceExpression = Expressions.numberTemplate(Double.class,
            "6371 * acos(cos(radians({0})) * cos(radians({1})) * cos(radians({2}) - radians({3})) + sin(radians({0})) * sin(radians({1})))",
            Double.parseDouble(latitude), place.coordinate.latitude, place.coordinate.longitude,
            Double.parseDouble(longitude));

        List<Place> places = jpaQueryFactory.selectFrom(place)
            .orderBy(distanceExpression.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(places, pageable, places.size());
    }

    @Override
    public Page<Place> findPlacesByDistanceAndFilters(String topLeftLongitude,
        String topLeftLatitude, String bottomRightLongitude, String bottomRightLatitude,
        String longitude, String latitude, List<String> categories, List<String> influencers,
        Pageable pageable) {

        QPlace place = QPlace.place;
        QVideo video = QVideo.video;
        QInfluencer influencer = QInfluencer.influencer;

        NumberTemplate<Double> distanceExpression = Expressions.numberTemplate(Double.class,
            "6371 * acos(cos(radians({0})) * cos(radians({1})) * cos(radians({2}) - radians({3})) + sin(radians({0})) * sin(radians({1})))",
            Double.parseDouble(latitude), place.coordinate.latitude, place.coordinate.longitude,
            Double.parseDouble(longitude));

        List<Place> places = jpaQueryFactory.selectFrom(place)
            .leftJoin(video).on(video.place.eq(place))
            .leftJoin(place).on(influencer.id.eq(video.influencer.id))
            .where(
                withinBoundary(
                    place,
                    Double.parseDouble(topLeftLongitude),
                    Double.parseDouble(topLeftLatitude),
                    Double.parseDouble(bottomRightLongitude),
                    Double.parseDouble(bottomRightLatitude)
                ),
                placeCategoryIn(categories),
                placeInfluencerIn(influencers)
            ).orderBy(distanceExpression.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(places, pageable, places.size());
    }

    private BooleanExpression withinBoundary(QPlace place, double topLeftLongitude,
        double topLeftLatitude,
        double bottomRightLongitude, double bottomRightLatitude) {
        NumberTemplate<Double> longitude = Expressions.numberTemplate(Double.class,
            "CAST({0} AS DOUBLE)", place.coordinate.longitude);
        NumberTemplate<Double> latitude = Expressions.numberTemplate(Double.class,
            "CAST({0} AS DOUBLE)", place.coordinate.latitude);

        return longitude.between(topLeftLongitude, bottomRightLongitude)
            .and(latitude.between(bottomRightLatitude, topLeftLatitude));
    }

    private BooleanExpression placeCategoryIn(List<String> categories) {
        if (categories == null) {
            return null;
        }

        List<Category> enumCategories = categories.stream()
            .map(Category::valueOf)
            .collect(Collectors.toList());

        return QPlace.place.category.in(enumCategories);
    }

    private BooleanExpression placeInfluencerIn(List<String> influencers) {
        if (influencers == null) {
            return null;
        }

        return QInfluencer.influencer.name.in(influencers);
    }
}
