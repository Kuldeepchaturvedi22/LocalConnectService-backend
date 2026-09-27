package com.localconnectservice.portal.enquiry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EnquiryRepository extends JpaRepository<Enquiry, Long> {
  Page<Enquiry> findAllByOrderByCreatedAtDesc(Pageable pageable);
  Optional<Enquiry> findByPublicId(String publicId);
}
