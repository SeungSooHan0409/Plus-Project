package com.example.plusproject;

import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.accommodation.repository.AccommodationRepository;
import com.example.plusproject.domain.user.entity.User;
import com.example.plusproject.domain.user.enums.UserRole;
import com.example.plusproject.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AccommodationBulkInsertTest {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Commit // 데이터 롤백 방지
    @Transactional
    public void generateMillionAccommodations() {

        User user = userRepository.save(User.builder()
                .name("테스트이름3")
                .nickname("testNickname4")
                .email("test3@example.com")
                .password("Test12312312!")
                .phoneNumber("+8201011112222")
                .residence("서울특별시")
                .role(UserRole.HOST)
                .build());

        List<Accommodation> accommodations = new ArrayList<>();

        for (int i = 2000001; i <= 2050001; i++) {
            Accommodation accommodation = new Accommodation(
                    "숙소이름_" + i,
                    "수원시 팔달구 ... " + (i % 500),
                    "수원",
                    2,
                    "image_" + i,
                    "설명_" + i,
                    "원룸",
                    "와이파이,TV",
                    100000 + (i % 10000),
                    user
            );

            accommodations.add(accommodation);

            //10,000개마다 한 번씩 실행
            if (i % 10000 == 0) {
                accommodationRepository.saveAll(accommodations);
                accommodations.clear();
                // 저장되고있는지 확인차원에서 작성
                System.out.println("Saved: " + i);
            }
        }

        if (!accommodations.isEmpty()) {
            accommodationRepository.saveAll(accommodations);
        }
    }
}
