package com.sachinkumar.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sachinkumar.entity.LoanApplication;
import com.sachinkumar.entity.LoanApplicationChecklistItem;
import com.sachinkumar.entity.Team;
import com.sachinkumar.entity.Privilege;
import com.sachinkumar.entity.User;
import com.sachinkumar.repository.LoanApplicationChecklistItemRepository;
import com.sachinkumar.repository.LoanApplicationRepository;
import com.sachinkumar.repository.TeamRepository;
import com.sachinkumar.repository.PrivilegeRepository;
import com.sachinkumar.repository.UserRepository;

@Component
public class SetupData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private LoanApplicationChecklistItemRepository loanApplicationChecklistItemRepository;
    @Autowired
    PasswordEncoder encoder;

    @Bean
    BCryptPasswordEncoder passwordEncoder() 
    {
        return new BCryptPasswordEncoder();
    }
    

    @PostConstruct
    public void init() {
        initOrganizations();
        initPrivileges();
        initUsers();
        initLoanApplication();
        initCheckList();
    }



	private void initUsers() {
        final Privilege privilege1 = privilegeRepository.findByName("READ_PRIVILEGE");
        final Privilege privilege2 = privilegeRepository.findByName("WRITE_PRIVILEGE");

        final User user1 = new User();
        user1.setId(new Long(1));
        user1.setUsername("sachin");
        user1.setPassword(encoder.encode("111"));
        user1.setPrivileges(new HashSet<>(Arrays.asList(privilege1)));
        user1.setOrganization(teamRepository.findByName("FirstTeam"));
        userRepository.save(user1);

        final User user2 = new User();
        user2.setId(new Long(2));
        user2.setUsername("kumar");
        user2.setPassword(encoder.encode("222"));
        user2.setPrivileges(new HashSet<>(Arrays.asList(privilege1, privilege2)));
        user2.setOrganization(teamRepository.findByName("SecondTeam"));
        userRepository.save(user2);
    }

    private void initOrganizations() {
        final Team org1 = new Team("FirstTeam");
        teamRepository.save(org1);

        final Team org2 = new Team("SecondTeam");
        teamRepository.save(org2);
    }

    private void initPrivileges() {
        final Privilege privilege1 = new Privilege("READ_PRIVILEGE");
        privilegeRepository.save(privilege1);

        final Privilege privilege2 = new Privilege("WRITE_PRIVILEGE");
        privilegeRepository.save(privilege2);
    }
    
    private void initLoanApplication() {
        final LoanApplication app = new LoanApplication("Loan app 1", new Long(1234), "NEW", false, null);
       
        loanApplicationRepository.save(app);
        Optional<User> u= userRepository.findById(new Long(5));
        final LoanApplication app2 = new LoanApplication("Loan app 2", new Long(1235), "NEW", false, u.get());
       
        loanApplicationRepository.save(app2);
        Optional<User> u1= userRepository.findById(new Long(6));
        final LoanApplication app3 = new LoanApplication("Loan app 3", new Long(1236), "NEW", false, u1.get());
      
        loanApplicationRepository.save(app3);
        
    }

    private void initCheckList() {
    	{
    	 Optional<LoanApplication> app1= loanApplicationRepository.findById(new Long(1234));
    	LoanApplicationChecklistItem check1 = new LoanApplicationChecklistItem(new Long(1),"check list 1", true, app1.get());
    	LoanApplicationChecklistItem check2 = new LoanApplicationChecklistItem(new Long(2),"check list 2", true, app1.get());
    	LoanApplicationChecklistItem check3 = new LoanApplicationChecklistItem(new Long(3),"check list 3", true, app1.get());
    	Set<LoanApplicationChecklistItem> list = new HashSet<LoanApplicationChecklistItem>();
    	list.add(check3);
    	list.add(check2);
    	list.add(check1);
    	loanApplicationChecklistItemRepository.save(check1);
    	loanApplicationChecklistItemRepository.save(check2);
    	loanApplicationChecklistItemRepository.save(check3);
    	LoanApplication lapp1 = app1.get();
    	lapp1.setCheckList(list);
    	 loanApplicationRepository.save(lapp1);
    	}
    	{
    	 Optional<LoanApplication> app11= loanApplicationRepository.findById(new Long(1235));
     	LoanApplicationChecklistItem check21 = new LoanApplicationChecklistItem(new Long(4),"check list 1", true, app11.get());
     	LoanApplicationChecklistItem check22 = new LoanApplicationChecklistItem(new Long(5),"check list 2", true, app11.get());
     	LoanApplicationChecklistItem check23 = new LoanApplicationChecklistItem(new Long(6),"check list 3", true, app11.get());
     	Set<LoanApplicationChecklistItem> list1 = new HashSet<LoanApplicationChecklistItem>();
     	list1.add(check21);
     	list1.add(check22);
     	list1.add(check23);
     	loanApplicationChecklistItemRepository.save(check21);
     	loanApplicationChecklistItemRepository.save(check22);
     	loanApplicationChecklistItemRepository.save(check23);
     	LoanApplication lapp2 = app11.get();
     	lapp2.setCheckList(list1);
     	 loanApplicationRepository.save(lapp2);
    	}
    	{
     	 Optional<LoanApplication> app31= loanApplicationRepository.findById(new Long(1236));
      	LoanApplicationChecklistItem check31 = new LoanApplicationChecklistItem(new Long(7),"check list 1", false, app31.get());
      	LoanApplicationChecklistItem check32 = new LoanApplicationChecklistItem(new Long(8),"check list 2", true, app31.get());
      	Set<LoanApplicationChecklistItem> list31 = new HashSet<LoanApplicationChecklistItem>();
      	list31.add(check31);
      	list31.add(check32);
      	loanApplicationChecklistItemRepository.save(check31);
      	loanApplicationChecklistItemRepository.save(check32);
      	LoanApplication lapp3 = app31.get();
      	lapp3.setCheckList(list31);
    	}
	}
}
