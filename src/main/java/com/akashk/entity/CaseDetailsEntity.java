package com.akashk.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

//import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "case_details")
@EqualsAndHashCode(of = { "planName", "createdBy" })
public class CaseDetailsEntity implements Serializable {

	private static final long serialVersionUID = -3592975847651206937L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "case_id")
	private Integer caseId;
	@Column(name = "plan_name")
	private String planName;

	@ManyToOne(fetch = FetchType.LAZY)
	// one citizen can apply for only one plan
	@JoinColumn(name = "citizen_id", unique = true)
	private CitizenEntity citizen;
	
	/*
	 * @OneToOne(mappedBy = "caseDetails", fetch = FetchType.LAZY)
	 * 
	 * private EducationDetailsEntity educationDetails;
	 * 
	 * @OneToOne(mappedBy = "caseDetails", fetch = FetchType.LAZY)
	 * // @JsonManagedReference private IncomeDetailsEntity incomeDetails;
	 * 
	 * @OneToMany(mappedBy = "caseDetails", fetch = FetchType.LAZY)
	 * // @JsonManagedReference private Set<KidDetailsEntity> kidsDetails;
	 */
	@CreationTimestamp
	private LocalDate createdDate;
	@UpdateTimestamp
	private LocalDate updatedDate;
	@Column(name = "create_by")
	private Integer createdBy;
	@Column(name = "updated_by")
	private Integer updatedBy;

	@Override
	public String toString() {
		return "CaseDetailsEntity [caseId=" + caseId + ", planName=" + planName + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + "]";
	}

}
