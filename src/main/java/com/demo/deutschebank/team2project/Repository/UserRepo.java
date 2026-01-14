package com.demo.deutschebank.team2project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.deutschebank.team2project.Entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	User findByUsername(String username);

	User findByUsernameAndPassword(String username, String password);

	User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.username = :username AND u.email = :email")
	User findByUsernameAndEmail(@Param("username") String username, @Param("email") String email);

	@Query("SELECT u FROM User u WHERE u.username = :uNameOrEmail OR u.email = :uNameOrEmail")
	List<User> findByUsernameOrEmail(@Param("uNameOrEmail") String uNameOrEmail);

	@Query("SELECT u FROM User u WHERE (u.username = :uNameOrEmail OR u.email = :uNameOrEmail) AND u.password = :password")
	User loginByUsernameOrEmail(@Param("uNameOrEmail") String uNameOrEmail, @Param("password") String password);
	
    boolean existsByEmail(String email);

}
