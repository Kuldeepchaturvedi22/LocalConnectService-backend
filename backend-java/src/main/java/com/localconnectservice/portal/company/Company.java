package com.localconnectservice.portal.company;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "companies")
public class Company {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(nullable = false, unique = true, updatable = false) private String publicId;
  @Column(nullable = false) private String name;
  @Column(nullable = false) private String contactName;
  @Column(nullable = false) private String mobile;
  @Column(nullable = false) private String serviceCategory;
  @Enumerated(EnumType.STRING) @Column(nullable = false) private CompanyStatus status = CompanyStatus.ACTIVE;
  @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();

  protected Company() {}
  public Company(String name, String contactName, String mobile, String serviceCategory) {
    this.publicId = "CMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    this.name = name; this.contactName = contactName; this.mobile = mobile; this.serviceCategory = serviceCategory;
  }
  public String getPublicId() { return publicId; }
  public String getName() { return name; }
  public String getContactName() { return contactName; }
  public String getMobile() { return mobile; }
  public String getServiceCategory() { return serviceCategory; }
  public CompanyStatus getStatus() { return status; }
  public Instant getCreatedAt() { return createdAt; }
  public void setStatus(CompanyStatus status) { this.status = status; }
  public void setName(String name) { this.name = name; }
  public void setContactName(String contactName) { this.contactName = contactName; }
  public void setMobile(String mobile) { this.mobile = mobile; }
  public void setServiceCategory(String serviceCategory) { this.serviceCategory = serviceCategory; }
}
