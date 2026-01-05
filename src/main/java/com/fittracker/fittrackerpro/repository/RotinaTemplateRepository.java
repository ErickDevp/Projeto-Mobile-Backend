package com.fittracker.fittrackerpro.repository;

import com.fittracker.fittrackerpro.entity.RotinaTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RotinaTemplateRepository extends JpaRepository<RotinaTemplate, Long> {
}
