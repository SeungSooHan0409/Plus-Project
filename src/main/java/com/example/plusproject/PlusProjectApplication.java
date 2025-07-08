package com.example.plusproject;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PlusProjectApplication {

    public static void main(String[] args) {

        // .env 로드
        Dotenv dotenv = Dotenv.load();

        // 환경변수 설정
        System.setProperty("PORT", dotenv.get("PORT"));
        System.setProperty("DATABASE", dotenv.get("DATABASE"));
        System.setProperty("USERNAME", dotenv.get("USERNAME"));
        System.setProperty("PASSWORD", dotenv.get("PASSWORD"));

        SpringApplication.run(PlusProjectApplication.class, args);

    }


}
