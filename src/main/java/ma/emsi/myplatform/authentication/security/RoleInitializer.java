package ma.emsi.myplatform.authentication.security;

import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.authentication.role.Role;
import ma.emsi.myplatform.authentication.role.Rolerepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleInitializer {
    private static final Logger logger = LoggerFactory.getLogger(RoleInitializer.class);
    private final Rolerepository roleRepository;

    @PostConstruct
    public void init() {
        List<String> roles = Arrays.asList("ADMIN", "FOURNISSEUR", "ACHETEUR");
        for (String roleName : roles) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
                logger.info("Role {} created", roleName);
            } else {
                logger.info("Role {} already exists", roleName);
            }
        }
    }
}

