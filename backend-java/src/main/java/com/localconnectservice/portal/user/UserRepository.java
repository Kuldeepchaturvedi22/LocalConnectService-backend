package com.localconnectservice.portal.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<PortalUser, Long> {
  Optional<PortalUser> findFirstByMobileOrEmailOrPublicId(String mobile, String email, String publicId);
  Optional<PortalUser> findByPublicId(String publicId);
  Page<PortalUser> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
