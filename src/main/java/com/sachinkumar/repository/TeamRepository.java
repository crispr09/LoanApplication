package com.sachinkumar.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sachinkumar.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findByName(String name);
}
