package com.akashk.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akashk.entity.CorrespondenceEntity;

public interface CorrespondceRepository extends JpaRepository<CorrespondenceEntity, Serializable>{
	
	public List<CorrespondenceEntity> findByTriggerStatus(String status);

}
