package com.akashk.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "citizen")
public class CitizenEntity implements Serializable{
	
	
	private static final long serialVersionUID = -4693049389791830554L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "c_id")
	private Integer citizenId;
	@Column(name = "full_name")
	private String fullName;
	@Column(name = "email_id")
	private String emailId;
	@Column(name = "mobile_no")
	private String mobileNo;
	@Column(name = "dob")
	private LocalDate dob;
	@Column(name = "gender")
	private String gender;
	@Column(name = "ssn_no")
	private String ssnNo;
	@Column(name = "created_date")

	@CreationTimestamp
	private LocalDate createdDate;
	@Column(name = "updated_date")
	@UpdateTimestamp
	private LocalDate updatedDate;
	@Column(name = "created_by")
	private Integer createdBy;
	@Column(name = "updated_by")
	private Integer updatedBy;
	
}
