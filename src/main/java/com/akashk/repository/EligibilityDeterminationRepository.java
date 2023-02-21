package com.akashk.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akashk.entity.EligibilityDeterminationEntity;

public interface EligibilityDeterminationRepository extends JpaRepository<EligibilityDeterminationEntity, Serializable>{
	
	@Query("select e from EligibilityDeterminationEntity e where e.caseDetailsEntity.caseId = :caseId")
	public EligibilityDeterminationEntity findBycaseId(@Param("caseId") Integer caseId);

}
