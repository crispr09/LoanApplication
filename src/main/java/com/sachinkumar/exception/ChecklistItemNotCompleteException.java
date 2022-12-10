package com.sachinkumar.exception;

public class ChecklistItemNotCompleteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String msg;
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ChecklistItemNotCompleteException(String msg) {
		this.msg=msg;
		// TODO Auto-generated constructor stub
	}

}
