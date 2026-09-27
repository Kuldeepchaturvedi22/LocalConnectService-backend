package com.localconnectservice.portal.user;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "portal_users", indexes = {@Index(name = "idx_user_mobile", columnList = "mobile", unique = true), @Index(name = "idx_user_public_id", columnList = "publicId", unique = true)})
public class PortalUser {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(nullable = false, unique = true, updatable = false) private String publicId;
  @Column(nullable = false) private String fullName;
  @Column(nullable = false, unique = true) private String mobile;
  @Column(unique = true) private String email;
  @Column(nullable = false) private String passwordHash;
  @Enumerated(EnumType.STRING) @Column(nullable = false) private Role role;
  @Enumerated(EnumType.STRING) @Column(nullable = false) private AccountStatus status = AccountStatus.PENDING;
  private String parentPublicId;
  @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();

  protected PortalUser() {}
  public PortalUser(String fullName, String mobile, String email, String passwordHash, Role role, AccountStatus status) {
    this.publicId = "USR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    this.fullName = fullName; this.mobile = mobile; this.email = email; this.passwordHash = passwordHash; this.role = role; this.status = status;
  }
  public Long getId() { return id; }
  public String getPublicId() { return publicId; }
  public String getFullName() { return fullName; }
  public String getMobile() { return mobile; }
  public String getEmail() { return email; }
  public String getPasswordHash() { return passwordHash; }
  public Role getRole() { return role; }
  public AccountStatus getStatus() { return status; }
  public String getParentPublicId() { return parentPublicId; }
  public Instant getCreatedAt() { return createdAt; }
}
