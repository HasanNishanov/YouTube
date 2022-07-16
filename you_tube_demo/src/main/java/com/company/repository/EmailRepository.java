package com.company.repository;

import com.company.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<EmailHistoryEntity, Integer> {

    Optional<EmailHistoryEntity> findTopByEmailOrderByCreatedDateDesc(String email);
    @Query(value = "select count(*) " +
            "from email_history " +
            "where email = :email " +
            "and created_date + INTERVAL '1 MINUTE' > now() ", nativeQuery = true)
    Long countResend(@Param("email") String email);
}
