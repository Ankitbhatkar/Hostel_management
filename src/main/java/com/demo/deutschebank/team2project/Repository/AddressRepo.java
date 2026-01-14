package com.demo.deutschebank.team2project.Repository;

import com.demo.deutschebank.team2project.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address,Integer> {
}
