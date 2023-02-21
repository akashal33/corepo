package com.akashk.entity;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "co_triggers")
public class CorrespondenceEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "co_trg_id")
	private Long correspondenceId;
	
	@ManyToOne
	@JoinColumn(name = "case_id")
	private CaseDetailsEntity caseDetailsEntity;
	
	@Lob
	@Column(name = "co_pdf")
	private byte[] CorrespodencePdf;
	@Column(name = "trg_status")
	private String triggerStatus;
	
	@CreationTimestamp
	private LocalDate createdDate;
	@UpdateTimestamp
	private LocalDate updatedDate;
	@Column(name = "create_by")
	private Integer createdBy;
	@Column(name = "updated_by")
	private Integer updatedBy;
}
