package ma.emsi.myplatform;

import ma.emsi.myplatform.authentication.role.Role;
import ma.emsi.myplatform.authentication.role.Rolerepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class MyPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyPlatformApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(Rolerepository rolerepository){
        return args -> {

        };
    }
}
