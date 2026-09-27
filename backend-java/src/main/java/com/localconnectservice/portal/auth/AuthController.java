package com.localconnectservice.portal.auth;

import com.localconnectservice.portal.security.JwtService;
import com.localconnectservice.portal.user.AccountStatus;
import com.localconnectservice.portal.user.PortalUser;
import com.localconnectservice.portal.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController @RequestMapping("/api/v1/auth")
public class AuthController {
  private final UserRepository users; private final PasswordEncoder passwords; private final JwtService jwt;
  public AuthController(UserRepository users, PasswordEncoder passwords, JwtService jwt) { this.users = users; this.passwords = passwords; this.jwt = jwt; }
  @PostMapping("/login") LoginResponse login(@Valid @RequestBody LoginRequest request) {
    PortalUser user = users.findFirstByMobileOrEmailOrPublicId(request.identifier(), request.identifier(), request.identifier())
      .filter(found -> found.getStatus() == AccountStatus.ACTIVE && passwords.matches(request.password(), found.getPasswordHash()))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ID या password सही नहीं है।"));
    return new LoginResponse(jwt.create(user), UserView.from(user));
  }
  @GetMapping("/me") UserView me(Authentication authentication) { return UserView.from((PortalUser) authentication.getPrincipal()); }
  public record LoginRequest(@NotBlank String identifier, @NotBlank String password) {}
  public record LoginResponse(String accessToken, UserView user) {}
  public record UserView(String id, String name, String mobile, String email, String role, String roleLabel, String initials) {
    static UserView from(PortalUser user) {
      String initials = ArraysSupport.initials(user.getFullName());
      return new UserView(user.getPublicId(), user.getFullName(), user.getMobile(), user.getEmail(), user.getRole().name(), user.getRole().getLabel(), initials);
    }
  }
  private static class ArraysSupport {
    static String initials(String name) { String[] parts = name.trim().split("\\s+"); return (parts[0].substring(0, 1) + (parts.length > 1 ? parts[parts.length - 1].substring(0, 1) : "")).toUpperCase(); }
  }
}
