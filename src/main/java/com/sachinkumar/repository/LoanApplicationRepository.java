package com.sachinkumar.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sachinkumar.entity.LoanApplication;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {


}
