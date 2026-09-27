package com.localconnectservice.portal.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserRepository repo;
  private final PasswordEncoder passwords;
  public UserController(UserRepository repo, PasswordEncoder passwords) { this.repo = repo; this.passwords = passwords; }

  @GetMapping
  Page<UserView> list(@RequestParam(defaultValue = "0") int page) {
    return repo.findAllByOrderByCreatedAtDesc(PageRequest.of(page, 20)).map(UserView::from);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  UserView create(@Valid @RequestBody UserRequest req) {
    PortalUser user = new PortalUser(req.fullName(), req.mobile(), req.email(), passwords.encode(req.password()), req.role(), AccountStatus.ACTIVE);
    return UserView.from(repo.save(user));
  }

  public record UserRequest(
    @NotBlank String fullName,
    @NotBlank @Pattern(regexp = "\\d{10}") String mobile,
    String email,
    @NotBlank String password,
    @NotNull Role role) {}

  public record UserView(String id, String fullName, String mobile, String email, String role, String roleLabel, String status, String createdAt) {
    static UserView from(PortalUser u) {
      return new UserView(u.getPublicId(), u.getFullName(), u.getMobile(), u.getEmail(),
        u.getRole().name(), u.getRole().getLabel(), u.getStatus().name(), u.getCreatedAt().toString());
    }
  }
}
