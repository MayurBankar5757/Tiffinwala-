package Tiffinwala.App.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Tiffinwala.App.Entities.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

	Optional<Vendor> findByUserUid(Integer uid);

	Optional<Vendor> findByUser_Uid(Integer uid);
}

