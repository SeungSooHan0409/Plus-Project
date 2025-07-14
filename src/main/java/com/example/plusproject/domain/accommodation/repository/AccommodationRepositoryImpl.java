package com.example.plusproject.domain.accommodation.repository;

import com.example.plusproject.domain.accommodation.dto.AccommodationSearchResponseDto;
import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.accommodation.entity.QAccommodation;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccommodationRepositoryImpl implements AccommodationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Accommodation> searchAccommodationsByNameOrAddress(String keyword, Pageable pageable) {

        QAccommodation accommodation = QAccommodation.accommodation;

        BooleanExpression condition = null;

        if (keyword != null && !keyword.isBlank()) {
            // '%keyword%'
            condition = accommodation.address.containsIgnoreCase(keyword)
                    .or(accommodation.accommodationName.containsIgnoreCase(keyword));
        }

        List<Accommodation> content = queryFactory
                .selectFrom(accommodation)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(accommodation.createdAt.desc())
                .fetch();

        long total = queryFactory
                .select(accommodation.count())
                .from(accommodation)
                .where(condition)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public long countAccommodationsByNameOrAddress(String keyword) {

        QAccommodation accommodation = QAccommodation.accommodation;

        BooleanExpression condition = null;
        if (keyword != null && !keyword.isBlank()) {
            condition = accommodation.accommodationName.containsIgnoreCase(keyword)
                    .or(accommodation.address.containsIgnoreCase(keyword));
        }
        return queryFactory
                .select(accommodation.count())
                .from(accommodation)
                .where(condition)
                .fetchOne();
    }

    @Override
    public Page<AccommodationSearchResponseDto> searchByCity(String city, Pageable pageable) {

        QAccommodation accommodation = QAccommodation.accommodation;

        List<AccommodationSearchResponseDto> content = queryFactory
                .select(Projections.constructor(AccommodationSearchResponseDto.class,
                        accommodation.id,
                        accommodation.accommodationName,
                        accommodation.address,
                        accommodation.city,
                        accommodation.description,
                        accommodation.roomType,
                        accommodation.image,
                        accommodation.services,
                        accommodation.price,
                        accommodation.user.id
                ))
                .from(accommodation)
                .where(accommodation.city.containsIgnoreCase(city))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(accommodation.count())
                .from(accommodation)
                .where(accommodation.city.containsIgnoreCase(city))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public long countAccommodationsByCity(String city) {

        QAccommodation accommodation = QAccommodation.accommodation;

        return queryFactory
                .select(accommodation.count())
                .from(accommodation)
                .where(accommodation.city.containsIgnoreCase(city))
                .fetchOne();
    }
}