package com.sachinkumar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "loan_application_checklist_item")
public class LoanApplicationChecklistItem {
	
	@Id
	@Column(name = "id")
	private Long checklistId;
	
	
	@Column(name = "checklist_task")
	private String checklistTask;

	@Column(name = "is_complete")
	private boolean completed;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	private LoanApplication loanApplication_new;

	public LoanApplicationChecklistItem() {
	}
	
	public LoanApplicationChecklistItem(Long checklistId, String checklistTask, boolean isCompleted,
			LoanApplication loanApplication) {
		super();
		this.checklistId = checklistId;
		this.checklistTask = checklistTask;
		this.completed = isCompleted;
		this.loanApplication_new = loanApplication;
	}

	public Long getChecklistId() {
		return checklistId;
	}

	public void setChecklistId(Long checklistId) {
		this.checklistId = checklistId;
	}

	public String getChecklistTask() {
		return checklistTask;
	}

	public void setChecklistTask(String checklistTask) {
		this.checklistTask = checklistTask;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean isCompleted) {
		this.completed = isCompleted;
	}

	public LoanApplication getLoanApplication() {
		return loanApplication_new;
	}

	public void setLoanApplication(LoanApplication loanApplication) {
		this.loanApplication_new = loanApplication;
	}
	

}
