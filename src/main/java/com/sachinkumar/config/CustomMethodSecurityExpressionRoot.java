package com.sachinkumar.config;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import com.sachinkumar.entity.LoanApplication;
import com.sachinkumar.entity.User;
import com.sachinkumar.repository.LoanApplicationRepository;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

	 private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LoanApplicationRepository loanApplicationRepository;
    private Object filterObject;
    private Object returnObject;

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean isMember(Long OrganizationId) {
        final User user = ((MyUserPrincipal) this.getPrincipal()).getUser();
       boolean flag = user.getOrganization().getId().longValue() == OrganizationId.longValue();
        logger.info("LoanApplication isOpened : " +flag);
        if (!flag) {
            throw new AccessDeniedException("User is not Authorized!");
        }
        return flag;
    }
    public boolean isOpened(Long loanId) {
        final Optional<LoanApplication> user = loanApplicationRepository.findById(loanId);
       boolean flag = user.get().isOpened();
        logger.info("LoanApplication isOpened : " +flag);
        if (!flag) {
        	logger.info("Application is already opened by user ID " +user.get().getOwner());
            throw new AccessDeniedException("Application is already opened! ");
        }
        return flag;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public void setFilterObject(Object obj) {
        this.filterObject = obj;
    }

    @Override
    public void setReturnObject(Object obj) {
        this.returnObject = obj;
    }
}
