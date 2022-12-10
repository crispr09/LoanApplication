package com.sachinkumar.exception;

public class UserNotAuthorizedException  extends RuntimeException {
	String msg;
	   private static final long serialVersionUID = 1L;
	   public UserNotAuthorizedException () {
		   
	   }

	   public UserNotAuthorizedException(String a) {
		   this.msg=a;
	   }
	   public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

}
