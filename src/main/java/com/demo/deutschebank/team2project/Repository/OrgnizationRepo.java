package com.demo.deutschebank.team2project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.deutschebank.team2project.Entity.Orgnization;

import java.util.Optional;

public interface OrgnizationRepo extends JpaRepository<Orgnization, Integer> {

    Optional<Orgnization> findByName(String name);
    Optional<Orgnization> findByEmail(String email);

}
