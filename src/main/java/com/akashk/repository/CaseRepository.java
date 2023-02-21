package com.akashk.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akashk.entity.CaseDetailsEntity;

public interface CaseRepository extends JpaRepository<CaseDetailsEntity, Serializable>{

}
