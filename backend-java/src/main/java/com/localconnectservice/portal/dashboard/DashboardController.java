package com.localconnectservice.portal.dashboard;

import com.localconnectservice.portal.company.CompanyRepository;
import com.localconnectservice.portal.enquiry.EnquiryRepository;
import com.localconnectservice.portal.enquiry.EnquiryStatus;
import com.localconnectservice.portal.user.PortalUser;
import com.localconnectservice.portal.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController @RequestMapping("/api/v1/dashboard")
public class DashboardController {
  private final EnquiryRepository enquiries;
  private final UserRepository users;
  private final CompanyRepository companies;

  public DashboardController(EnquiryRepository enquiries, UserRepository users, CompanyRepository companies) {
    this.enquiries = enquiries; this.users = users; this.companies = companies;
  }

  @GetMapping("/summary")
  DashboardSummary summary(Authentication authentication) {
    PortalUser user = (PortalUser) authentication.getPrincipal();
    long newEnquiries = enquiries.countByStatus(EnquiryStatus.NEW);
    long inProgress = enquiries.countByStatus(EnquiryStatus.IN_PROGRESS);
    long totalUsers = users.count();
    long totalCompanies = companies.count();
    return new DashboardSummary(user.getRole().getLabel(), List.of(
      new Card("New Enquiries", newEnquiries, "Action needed"),
      new Card("In Progress", inProgress, "Being handled"),
      new Card("Total Users", totalUsers, "Registered users"),
      new Card("Companies", totalCompanies, "Active partners")));
  }

  public record DashboardSummary(String scope, List<Card> cards) {}
  public record Card(String label, long value, String note) {}
}
