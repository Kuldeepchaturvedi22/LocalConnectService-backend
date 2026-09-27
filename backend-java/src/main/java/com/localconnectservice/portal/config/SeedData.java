package com.localconnectservice.portal.config;

import com.localconnectservice.portal.user.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SeedData {
  @Bean CommandLineRunner seedAdmin(UserRepository users, PasswordEncoder passwords) {
    return args -> {
      if (users.findFirstByMobileOrEmailOrPublicId("9999999999", "admin@localconnectservice.com", "SA-DEMO-001").isEmpty()) {
        users.save(new PortalUser("Super Admin Demo", "9999999999", "admin@localconnectservice.com", passwords.encode("admin123"), Role.SUPER_ADMIN, AccountStatus.ACTIVE));
      }
    };
  }
}
