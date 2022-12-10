package com.sachinkumar.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sachinkumar.config.MyUserPrincipal;
import com.sachinkumar.entity.LoanApplication;
import com.sachinkumar.entity.LoanApplicationChecklistItem;
import com.sachinkumar.entity.User;
import com.sachinkumar.exception.ChecklistItemNotCompleteException;
import com.sachinkumar.exception.LoanApplicationException;
import com.sachinkumar.repository.LoanApplicationRepository;
import com.sachinkumar.util.LoanApplicationStatus;

@RestController
public class LoanController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LoanApplicationRepository loanApplicationRepository;

	@PreAuthorize("isTeamMember(#teamId)")
	@GetMapping("/loans/{teamId}")
	@ResponseBody
	public ResponseEntity<List<LoanApplication>> findAllApplications(@PathVariable final long teamId) {

		return new ResponseEntity<List<LoanApplication>>(loanApplicationRepository.findAll(), HttpStatus.OK);
	}

	@PreAuthorize("isTeamMember(#teamId)")
	@GetMapping("/loans/{teamId}/{loanApplicationId}")
	@ResponseBody
	public ResponseEntity<LoanApplication> findLoanById(@PathVariable final long teamId,
			@PathVariable final long loanApplicationId) {

		final Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
		boolean flag = loanApplication.get().isOpened();
		logger.info("isOpened " + flag);
		if (flag) {
			logger.info("Application is already opened by user : " + loanApplication.get().getOwner());
			throw new AccessDeniedException("Application is already opened by other user!");
		}
		return new ResponseEntity<LoanApplication>(loanApplication.get(), HttpStatus.OK);
	}

	@PreAuthorize("isTeamMember(#teamId)")
	@GetMapping("/closeloan/{teamId}/{loanApplicationId}/{loanStatusId}")
	public ResponseEntity<LoanApplication> closeLoanApplication(@PathVariable final long teamId,
			@PathVariable final long loanApplicationId, @PathVariable final int loanStatusId) {

		try {
			if (!LoanApplicationStatus.isValid(loanStatusId)) {
				logger.info("Loan application status not Valid!");
				LoanApplicationException e = new LoanApplicationException();
				e.setMsg("Loan application status not Valid! Please provide correct and try again.");
				throw e;
			}
			LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId).get();
			boolean flag = loanApplication.isOpened();
			logger.info("isOpened " + flag);
			User user = null;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (principal instanceof MyUserPrincipal) {
				user = ((MyUserPrincipal) principal).getUser();
			}
			if (flag) {

				if (user.getId() == loanApplication.getOwner().getId()) {
					logger.info("Application is already opened by you, trying to close now");
					Set<LoanApplicationChecklistItem> set = loanApplication.getCheckList();
					for (LoanApplicationChecklistItem item : set) {
						if (item.isCompleted() == false)
							throw new ChecklistItemNotCompleteException(
									"check list must be completed, ChecklistItem ID: " + item.getChecklistId());
					}

					loanApplication.setOpened(false);
					loanApplication.setOwner(null);
					loanApplication.setStatus(LoanApplicationStatus.PENDING.toString());

					loanApplicationRepository.save(loanApplication);
					return new ResponseEntity<LoanApplication>(loanApplication, HttpStatus.OK);
				} else {

					logger.info("No Authority to CLOSE Application.It is opened by user : "
							+ loanApplication.getOwner().getUsername());
					LoanApplicationException e = new LoanApplicationException();
					e.setMsg("No Authority to CLOSE Application.It is opened by user : "
							+ loanApplication.getOwner().getUsername());
					throw e;
				}
			} else {
				String status = LoanApplicationStatus.get(loanStatusId);
				if (LoanApplicationStatus.PENDING.toString().equals(loanApplication.getStatus())
						&& (!LoanApplicationStatus.OPEN.toString().equals(status)
								&& !LoanApplicationStatus.PENDING.toString().equals(status))) {
					logger.info("Changing Application status to:  " + status);
					loanApplication.setOpened(false);
					loanApplication.setOwner(null);
					loanApplication.setStatus(status);
					loanApplicationRepository.save(loanApplication);
					return new ResponseEntity<LoanApplication>(loanApplication, HttpStatus.OK);
				} else {
					logger.info("Application is in " + loanApplication.getStatus() + " status!");
					LoanApplicationException e = new LoanApplicationException();
					e.setMsg("Application is in " + loanApplication.getStatus() + " status!");
					throw e;

				}
			}
		} catch (ChecklistItemNotCompleteException e) {
			throw new ChecklistItemNotCompleteException(e.getMsg());
		} catch (LoanApplicationException e) {
			throw new LoanApplicationException(e.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			throw new LoanApplicationException("generic Exception for loan closing " + e.getMessage());
		}

	}

	@PreAuthorize("isTeamMember(#teamId)")
	@GetMapping("/openloan/{teamId}/{loanApplicationId}")
	public ResponseEntity<LoanApplication> openLoanApplication(@PathVariable final long teamId,
			@PathVariable final long loanApplicationId) {
		try {
			LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId).get();
			boolean flag = loanApplication.isOpened();
			logger.info("isOpened " + flag);
			User user = null;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (principal instanceof MyUserPrincipal) {
				user = ((MyUserPrincipal) principal).getUser();
			}
			if (flag) {

				if (user.getId() == loanApplication.getOwner().getId()) {
					logger.info("Application is already opened by you ");
					return new ResponseEntity<LoanApplication>(loanApplication, HttpStatus.OK);
				} else {

					logger.info("Application is already opened by user: " + loanApplication.getOwner().getUsername());
					LoanApplicationException e = new LoanApplicationException();
					e.setMsg("Application is already opened by user: " + loanApplication.getOwner().getUsername());
					throw e;
				}
			} else {
				loanApplication.setOpened(true);
				loanApplication.setOwner(user);
				loanApplication.setStatus("OPEN");
				loanApplicationRepository.save(loanApplication);
				logger.info("Application is now opened by you : " + user.getUsername());
			}
			return new ResponseEntity<LoanApplication>(loanApplication, HttpStatus.OK);
		} catch (LoanApplicationException e) {
			throw new LoanApplicationException(e.getMsg());
		} catch (Exception e) {
			throw new LoanApplicationException("generic Exception for loan opening");
		}
	}

}
