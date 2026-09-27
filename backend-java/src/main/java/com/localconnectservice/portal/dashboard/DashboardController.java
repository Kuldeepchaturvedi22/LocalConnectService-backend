package com.localconnectservice.portal.dashboard;

import com.localconnectservice.portal.user.PortalUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController @RequestMapping("/api/v1/dashboard")
public class DashboardController {
  @GetMapping("/summary") DashboardSummary summary(Authentication authentication) {
    PortalUser user = (PortalUser) authentication.getPrincipal();
    return new DashboardSummary(user.getRole().getLabel(), List.of(
      new Card("New Enquiries", 2, "आज की नई requests"), new Card("Active Operations", 1, "Assigned work"),
      new Card("Pending Approvals", 3, "Action required"), new Card("Unread Notifications", 2, "Latest updates")));
  }
  public record DashboardSummary(String scope, List<Card> cards) {}
  public record Card(String label, int value, String note) {}
}
