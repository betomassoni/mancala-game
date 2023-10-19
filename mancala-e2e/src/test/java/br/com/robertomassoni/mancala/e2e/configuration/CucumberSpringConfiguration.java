package br.com.robertomassoni.mancala.e2e.configuration;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = CucumberSpringConfiguration.SpringConfiguration.class,
        initializers = ConfigDataApplicationContextInitializer.class)
public class CucumberSpringConfiguration {

    @Configuration
    @EntityScan(basePackages = "br.com.robertomassoni.mancala.repository.redis.entity")
    @ComponentScan(basePackages = "br.com.robertomassoni.mancala.e2e")
    @EnableAutoConfiguration
    static class SpringConfiguration {

    }
}
