package Tiffinwala.App.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Tiffinwala.App.Entities.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

	

   //public Vendor findByUserUid(Integer uid);
	  @Query("SELECT v FROM Vendor v JOIN FETCH v.user u WHERE u.uid = :uid")
	    Vendor findVendorByUserUid(@Param("uid") Integer uid);

}
