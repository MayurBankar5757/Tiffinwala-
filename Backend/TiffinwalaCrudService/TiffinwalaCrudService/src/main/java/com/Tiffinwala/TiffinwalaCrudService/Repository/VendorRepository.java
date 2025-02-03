package com.Tiffinwala.TiffinwalaCrudService.Repository;
import com.Tiffinwala.TiffinwalaCrudService.Entities.Vendor;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

	

   //public Vendor findByUserUid(Integer uid);
	  @Query("SELECT v FROM Vendor v JOIN FETCH v.user u WHERE u.uid = :uid")
	    Vendor findVendorByUserUid(@Param("uid") Integer uid);
	  
	    List<Vendor> findByIsVerifiedTrue(); // Fetch only approved vendors

	    List<Vendor> findByIsVerifiedFalse(); // Fetch only unapproved vendors

}

