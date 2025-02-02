package com.Tiffinwala.TiffinwalaAuthService.Repository;

import com.Tiffinwala.TiffinwalaAuthService.Entities.Vendor;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

	

   //public Vendor findByUserUid(Integer uid);
	  @Query("SELECT v FROM Vendor v JOIN FETCH v.user u WHERE u.uid = :uid")
	    Optional<Vendor> findVendorByUserUid(Integer uid);  // Return Optional<Vendor> instead of Vendor
	  
	    List<Vendor> findByIsVerifiedTrue(); // Fetch only approved vendors

	    List<Vendor> findByIsVerifiedFalse(); // Fetch only unapproved vendors

}

