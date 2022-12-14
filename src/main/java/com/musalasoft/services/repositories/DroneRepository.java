package com.musalasoft.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musalasoft.entities.Drones;

/**
 * 
 * @author mahmoud
 * drones repo
 */
@Repository
public interface DroneRepository extends JpaRepository<Drones, Long> {
	
}