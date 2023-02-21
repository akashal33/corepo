package com.akashk.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akashk.entity.CitizenEntity;

public interface CitizenRepository extends JpaRepository<CitizenEntity, Serializable>{

	
	
}
