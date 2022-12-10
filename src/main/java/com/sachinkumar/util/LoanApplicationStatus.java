package com.sachinkumar.util;

public enum LoanApplicationStatus{
	REJECTED(103, "REJECTED"),
	APPROVED(102, "APPROVED"),
	PENDING(101, "PENDING"),
	OPEN(100, "OPEN");

	static public boolean isValid(int code) {
		LoanApplicationStatus[] aFruits = LoanApplicationStatus.values();
	       for (LoanApplicationStatus aFruit : aFruits)
	           if (aFruit.code==code)
	               return true;
	       return false;
	   }
	static public String get(int code) {
		String status = "PENDING";
		LoanApplicationStatus[] aFruits = LoanApplicationStatus.values();
	       for (LoanApplicationStatus aFruit : aFruits)
	           if (aFruit.code==code)
	               status= aFruit.status;
	       return status;
	   }
	private final int code;

	private final String status;

	LoanApplicationStatus(int code, String status) {
		this.status = status;
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String getStatus() {
		return status;
	}
}
