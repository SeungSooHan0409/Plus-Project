package com.example.plusproject.domain.favorite.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteData {

    private String accommodationName;
    private String nickname;


    public FavoriteData(String accommodationName, String nickname) {
        this.accommodationName = accommodationName;
        this.nickname = nickname;
    }
}
