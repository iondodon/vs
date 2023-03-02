package org.example.vs.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

@EntityScan(basePackages = "org.example.vs")
@EnableJpaRepositories(basePackages = "org.example.vs")
@SpringBootApplication(scanBasePackages = "org.example.vs")
public class VsApplication {
    public static void main(String[] args) {
        SpringApplication.run(VsApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void serviceSetup() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}