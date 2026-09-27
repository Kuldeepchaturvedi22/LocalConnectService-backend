package com.localconnectservice.portal.user;

public enum Role {
  CLIENT("Client"), ASSOCIATE("AP"), SUPER_ASSOCIATE("SAP"), BRANCH("Branch"), HOD("HOD"),
  OPERATIONS("Operation Team"), COMPANY_PARTNER("Company"), ADMIN("Admin"), SUPER_ADMIN("Super Admin");
  private final String label;
  Role(String label) { this.label = label; }
  public String getLabel() { return label; }
}
