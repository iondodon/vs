package org.example.vs;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TestingService {
    @Value("${a}")
    private Integer a;
    @Value("${b}")
    private Integer b;
    @Value("${d}")
    private Integer d;

    @PostConstruct
    void postConstruct() {
        System.out.println("a=" + a);
        System.out.println("b=" + b);
        System.out.println("d=" + d);
    }
}
