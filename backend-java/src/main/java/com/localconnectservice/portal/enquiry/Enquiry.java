package com.localconnectservice.portal.enquiry;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "enquiries")
public class Enquiry {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(nullable = false, unique = true, updatable = false) private String publicId;
  @Column(nullable = false) private String customerName;
  @Column(nullable = false) private String mobile;
  @Column(nullable = false) private String serviceType;
  private String note;
  @Enumerated(EnumType.STRING) @Column(nullable = false) private EnquiryStatus status = EnquiryStatus.NEW;
  private String assignedToPublicId;
  @Column(nullable = false, updatable = false) private String createdByPublicId;
  @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();

  protected Enquiry() {}
  public Enquiry(String customerName, String mobile, String serviceType, String note, String createdByPublicId) {
    this.publicId = "ENQ-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    this.customerName = customerName; this.mobile = mobile; this.serviceType = serviceType;
    this.note = note; this.createdByPublicId = createdByPublicId;
  }
  public String getPublicId() { return publicId; }
  public String getCustomerName() { return customerName; }
  public String getMobile() { return mobile; }
  public String getServiceType() { return serviceType; }
  public String getNote() { return note; }
  public EnquiryStatus getStatus() { return status; }
  public String getAssignedToPublicId() { return assignedToPublicId; }
  public String getCreatedByPublicId() { return createdByPublicId; }
  public Instant getCreatedAt() { return createdAt; }
  public void setStatus(EnquiryStatus status) { this.status = status; }
  public void setAssignedToPublicId(String id) { this.assignedToPublicId = id; }
  public void setNote(String note) { this.note = note; }
}
