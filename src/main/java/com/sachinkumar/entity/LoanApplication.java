package com.sachinkumar.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "loan_application")
public class LoanApplication {
	
	@Id
	@Column(name = "id")
	private Long loanApplicationId;
	
	@Column(name = "application_name")
	private String applicantName;

	@Column(name = "status")
	private String status;

	@Column(name = "is_opened")
	private boolean isOpened;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="owner_id") 
	private User owner;

	
	@OneToMany(mappedBy="loanApplication_new",targetEntity = LoanApplicationChecklistItem.class, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@JsonIgnore
	Set<LoanApplicationChecklistItem> checkList ;

	public LoanApplication() {}
	public LoanApplication(String applicantName, Long loanApplicationId, String status, boolean isOpened,
			User owner) {
		super();
		this.applicantName = applicantName;
		this.loanApplicationId = loanApplicationId;
		this.status = status;
		this.isOpened = isOpened;
		this.owner = owner;
	}

	public boolean isOpened() {
		return isOpened;
	}
	public void setOpened(boolean isOpened) {
		this.isOpened = isOpened;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public Set<LoanApplicationChecklistItem> getCheckList() {
		return checkList;
	}
	public void setCheckList(Set<LoanApplicationChecklistItem> checkList) {
		this.checkList = checkList;
	}
	
	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Long getLoanApplicationId() {
		return loanApplicationId;
	}

	public void setLoanApplicationId(Long loanApplicationId) {
		this.loanApplicationId = loanApplicationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
