package com.example.plusproject.infra.redis;


// spin lock 형태

import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.accommodation.service.AccommodationService;
import com.example.plusproject.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class LettuceLockReservation {

    private final RedisLockRepository redisLockRepository;
    private final ReservationService reservationService;
    private final AccommodationService accommodationService;


    public void makingReservation(Long guestCount, LocalDate checkInDate, LocalDate checkoutDate, String accommodationAddress, Long userId) {

        // 숙소 조회
        Accommodation accommodation = accommodationService.findAccommodationByAddress(accommodationAddress);

        // lock 을 위한 '숙소이름 + 숙박 날짜'가 합해진 key 생성
        // ex. key 값 ) 롯데호텔1101호:20250711
        // 큐에 키 값 담아 놓고 하나씩 빼서 모두 lock 걸어줌
        Queue<String> keys = new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        for(LocalDate date = checkInDate; date.isBefore(checkoutDate); date = date.plusDays(1)){
            String tmpKey = accommodation.getAccommodationName()+":"+date.format(formatter);
            keys.offer(tmpKey);
        }

        while(!getLocks(keys)){                                    // lock 못잡으면 무한 대기
            try{
                Thread.sleep(100);                          // lock 획득 못했으면 100ms 후 다시 시도
            } catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }

        // lock 을 획득 한 뒤에는
        try {                                                      // lock 잡고 while 문 벗어났다면 예약 확정
            reservationService.reserveAccommodation(guestCount, checkInDate, checkoutDate, accommodationAddress, userId);
        } finally{

            releaseLocks(keys);
        }
    }


    // 여러개 lock 메서드
    private Boolean getLocks(Queue<String> keys){
        Queue<String> copyKeys = new LinkedList<>(keys);
        while(!copyKeys.isEmpty()){
            String element = copyKeys.poll();
            Boolean locked = redisLockRepository.lock(element);
            // lock 하나라도 실패하면 이미 획득한 locks 해제 후 false 반환
            if(locked ==null || !locked){
                releaseLocks(keys);
                return false;
            }
        }
        return true; // 전부 성공
    }

    // 여러개 unlock 메서드
    private Boolean releaseLocks(Queue<String> keys){
        Queue<String> copyKeys = new LinkedList<>(keys);
        while(!copyKeys.isEmpty()){
            String element = copyKeys.poll();
            redisLockRepository.unlock(element);
        }
        return true;
    }

}
