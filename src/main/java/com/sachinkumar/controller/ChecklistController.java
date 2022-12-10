package com.sachinkumar.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sachinkumar.entity.LoanApplicationChecklistItem;
import com.sachinkumar.exception.LoanApplicationException;
import com.sachinkumar.repository.LoanApplicationChecklistItemRepository;

@RestController
public class ChecklistController {
	  private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LoanApplicationChecklistItemRepository loanApplicationChecklistItemRepository;


	@PreAuthorize("isTeamMember(#teamId)")
	@GetMapping("/checklists/{teamId}/{checklistId}")
	@ResponseBody
	public ResponseEntity<LoanApplicationChecklistItem> findChecklistItemById(@PathVariable final long teamId, @PathVariable final long checklistId) {

		final Optional<LoanApplicationChecklistItem> loanApplication = loanApplicationChecklistItemRepository.findById(checklistId);
		 return new ResponseEntity<LoanApplicationChecklistItem>(loanApplication.get(), HttpStatus.OK);
	}

	@PreAuthorize("isTeamMember(#teamId)")
	@GetMapping("/updatechecklist/{teamId}/{checklistId}")
	public ResponseEntity<LoanApplicationChecklistItem> updateChecklistItem(@PathVariable final long teamId,
			@PathVariable final long checklistId) {

		try {
			LoanApplicationChecklistItem loanApplicationChecklistItem = loanApplicationChecklistItemRepository.findById(checklistId).get();
			boolean flag = loanApplicationChecklistItem.isCompleted();
			logger.debug("ChecklistItem isCompleted " + flag);

			if (flag) {

				logger.info("ChecklistItem is already completed");
					
			} else {
				loanApplicationChecklistItem.setCompleted(true);
				loanApplicationChecklistItemRepository.save(loanApplicationChecklistItem);
				logger.info("ChecklistItem is completed now !");
			}

			return new ResponseEntity<LoanApplicationChecklistItem>(loanApplicationChecklistItem, HttpStatus.OK);
		
		} 
		catch (Exception e) {
			logger.debug("generic Exception for ChecklistItem " + e.getMessage());
			e.printStackTrace();
			throw new LoanApplicationException("generic Exception for ChecklistItem");
		}
	
	}
		
}
