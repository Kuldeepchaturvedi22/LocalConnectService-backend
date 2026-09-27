package com.localconnectservice.portal.company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
  Page<Company> findAllByOrderByCreatedAtDesc(Pageable pageable);
  Optional<Company> findByPublicId(String publicId);
}
