package Tiffinwala.App.Repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Tiffinwala.App.Entities.Role;
import Tiffinwala.App.Entities.User;



@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :pwd")
    Optional<User> getLogin( String email,  String pwd); 
    
    @Query("SELECT u FROM User u WHERE u.role = :rid")
    public User getByRole(Role rid);
    
    
    

}
