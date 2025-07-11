//package com.example.plusproject.domain.favorite.testdata;
//
//import com.example.plusproject.domain.accommodation.entity.Accommodation;
//import com.example.plusproject.domain.accommodation.service.AccommodationService;
//import com.example.plusproject.domain.favorite.entity.Favorite;
//import com.example.plusproject.domain.favorite.repository.FavoriteRepository;
//import com.example.plusproject.domain.user.entity.User;
//import com.example.plusproject.domain.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class TestDataInitializer implements CommandLineRunner {
//
//    private final FavoriteRepository favoriteRepository;
//    private final UserService userService;
//    private final AccommodationService accommodationService;
//
//    // 데이터 1만건 이하면 5만건의 데이터 생성
//    @Override
//    public void run(String... args) throws Exception {
//        if(favoriteRepository.count() <= 10000) {
//
//            // 더미 User, Accomodation
//            User user = userService.findUserById(1L);
//            Accommodation accommodation = accommodationService.findAccommodationById(1L);
//
//            // batch 생성
//            List<Favorite> batch = new ArrayList<>();
//            int batchSize = 1000;
//
//            // 데이터 생성
//            for(int i = 0; i < 50000; i++) {
//
//                batch.add(new Favorite(user, accommodation));
//
//                // 1000 개마다 데이터 저장
//                if(batch.size() == batchSize) {
//                    favoriteRepository.saveAll(batch);
//                    batch.clear();
//                }
//
//                // 남은 데이터 있으면 저장
//                if (!batch.isEmpty()) {
//                    favoriteRepository.saveAll(batch);
//                }
//
//            }
//        }
//    }
//}
