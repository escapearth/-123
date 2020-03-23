package org.open.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class OpenMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenMarketApplication.class, args);
    }

}
