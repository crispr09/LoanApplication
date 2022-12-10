package com.sachinkumar.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sachinkumar.entity.LoanApplicationChecklistItem;

public interface LoanApplicationChecklistItemRepository extends JpaRepository<LoanApplicationChecklistItem, Long> {


}
