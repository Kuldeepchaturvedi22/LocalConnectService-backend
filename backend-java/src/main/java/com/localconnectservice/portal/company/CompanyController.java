package com.localconnectservice.portal.company;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {
  private final CompanyRepository repo;
  public CompanyController(CompanyRepository repo) { this.repo = repo; }

  @GetMapping
  Page<CompanyView> list(@RequestParam(defaultValue = "0") int page) {
    return repo.findAllByOrderByCreatedAtDesc(PageRequest.of(page, 20)).map(CompanyView::from);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  CompanyView create(@Valid @RequestBody CompanyRequest req) {
    return CompanyView.from(repo.save(new Company(req.name(), req.contactName(), req.mobile(), req.serviceCategory())));
  }

  @PatchMapping("/{id}")
  CompanyView update(@PathVariable String id, @RequestBody UpdateRequest req) {
    Company c = repo.findByPublicId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));
    if (req.name() != null) c.setName(req.name());
    if (req.contactName() != null) c.setContactName(req.contactName());
    if (req.mobile() != null) c.setMobile(req.mobile());
    if (req.serviceCategory() != null) c.setServiceCategory(req.serviceCategory());
    if (req.status() != null) c.setStatus(req.status());
    return CompanyView.from(repo.save(c));
  }

  public record CompanyRequest(
    @NotBlank String name,
    @NotBlank String contactName,
    @NotBlank @Pattern(regexp = "\\d{10}") String mobile,
    @NotBlank String serviceCategory) {}

  public record UpdateRequest(String name, String contactName, String mobile, String serviceCategory, CompanyStatus status) {}

  public record CompanyView(String id, String name, String contactName, String mobile,
                             String serviceCategory, String status, String createdAt) {
    static CompanyView from(Company c) {
      return new CompanyView(c.getPublicId(), c.getName(), c.getContactName(), c.getMobile(),
        c.getServiceCategory(), c.getStatus().name(), c.getCreatedAt().toString());
    }
  }
}
