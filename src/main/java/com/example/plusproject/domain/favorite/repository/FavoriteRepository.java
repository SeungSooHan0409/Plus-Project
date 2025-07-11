package com.example.plusproject.domain.favorite.repository;

import com.example.plusproject.domain.favorite.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Page<Favorite> findAllByOrderByModifiedAt(Pageable pageable);

}
