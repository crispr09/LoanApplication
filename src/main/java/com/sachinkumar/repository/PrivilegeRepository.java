package com.sachinkumar.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sachinkumar.entity.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    void delete(Privilege privilege);

}
