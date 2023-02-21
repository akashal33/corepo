package com.akashk.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "elig_deter_table")
public class EligibilityDeterminationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "elig_deter_id")
	private Long EligibilityDeterminationId;
	
	/*
	 * @Column(name = "citizen_name") private String citizenName;
	 * 
	 * @Column(name = "citizen_ssn") private String citizenSsn;
	 * 
	 * @Column(name = "case_id") private Long caseId;
	 * 
	 * @Column(name = "plan_name") private String planName;
	 */
	
	@ManyToOne
	@JoinColumn(name = "case_id")
	private CaseDetailsEntity caseDetailsEntity;
	
	@Column(name = "plan_status")
	private String planStatus;
	@Column(name = "start_dt")
	private LocalDate startDate;
	@Column(name = "end_dt")
	private LocalDate endDate;
	@Column(name = "benefit_amt")
	private Double benefitAmount;
	@Column(name = "denial_reason")
	private String DenialReason;
	
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDate createAt;
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDate updatedAt;
	
	@Column(name = "created_by")
	private Integer createdBy;
	@Column(name = "updated_by")
	private Integer updatedBy;

}
