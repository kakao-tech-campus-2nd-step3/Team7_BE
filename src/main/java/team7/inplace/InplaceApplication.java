package team7.inplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import team7.inplace.token.persistence.RefreshTokenRepository;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaRepositories(
    basePackages = "team7.inplace",
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {RefreshTokenRepository.class}
    )
)
public class InplaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InplaceApplication.class, args);
    }

}
