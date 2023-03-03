package org.example.vs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:sales.properties")
@PropertySource("classpath:booking.properties")
@PropertySource("classpath:bootstrap.properties")
public class PropertiesSourcesConfig {

}
