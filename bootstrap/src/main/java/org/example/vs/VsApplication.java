package org.example.vs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.TimeZone;

@SpringBootApplication
public class VsApplication {

    public static void main(String[] args) {
        SpringApplication.run(VsApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void serviceSetup() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}

