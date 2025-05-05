package com.team5.career_progression_app.repository;

import com.team5.career_progression_app.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, 
    JpaSpecificationExecutor<User> {
    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(@Param("email") String email);
    
    List<User> findByActiveFalse();
    List<User> findByActiveTrue();

    @Query(
        value = """
        SELECT * FROM users u
        WHERE (:active IS NULL OR u.active = :active)
        AND (:name IS NULL OR LOWER(u.first_name) LIKE LOWER(CONCAT('%', :name, '%')))
        """,
        nativeQuery = true
    )
    List<User> filterUsers(Boolean active,String name);
}
