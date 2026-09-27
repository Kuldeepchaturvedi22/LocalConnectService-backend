package com.localconnectservice.portal.enquiry;

import com.localconnectservice.portal.user.PortalUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/enquiries")
public class EnquiryController {
  private final EnquiryRepository repo;
  public EnquiryController(EnquiryRepository repo) { this.repo = repo; }

  @GetMapping
  Page<EnquiryView> list(@RequestParam(defaultValue = "0") int page) {
    return repo.findAllByOrderByCreatedAtDesc(PageRequest.of(page, 20)).map(EnquiryView::from);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  EnquiryView create(@Valid @RequestBody EnquiryRequest req, Authentication auth) {
    PortalUser actor = (PortalUser) auth.getPrincipal();
    return EnquiryView.from(repo.save(new Enquiry(req.customerName(), req.mobile(), req.serviceType(), req.note(), actor.getPublicId())));
  }

  @PatchMapping("/{id}")
  EnquiryView update(@PathVariable String id, @RequestBody UpdateRequest req) {
    Enquiry e = repo.findByPublicId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enquiry not found"));
    if (req.status() != null) e.setStatus(req.status());
    if (req.assignedToPublicId() != null) e.setAssignedToPublicId(req.assignedToPublicId());
    if (req.note() != null) e.setNote(req.note());
    return EnquiryView.from(repo.save(e));
  }

  public record EnquiryRequest(
    @NotBlank String customerName,
    @NotBlank @Pattern(regexp = "\\d{10}") String mobile,
    @NotBlank String serviceType,
    String note) {}

  public record UpdateRequest(EnquiryStatus status, String assignedToPublicId, String note) {}

  public record EnquiryView(String id, String customerName, String mobile, String serviceType,
                             String note, String status, String assignedToPublicId,
                             String createdByPublicId, String createdAt) {
    static EnquiryView from(Enquiry e) {
      return new EnquiryView(e.getPublicId(), e.getCustomerName(), e.getMobile(), e.getServiceType(),
        e.getNote(), e.getStatus().name(), e.getAssignedToPublicId(), e.getCreatedByPublicId(), e.getCreatedAt().toString());
    }
  }
}
