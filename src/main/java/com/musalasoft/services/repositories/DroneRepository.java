package com.musalasoft.services.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.musalasoft.entities.DroneState;
import com.musalasoft.entities.Drones;

/**
 * 
 * @author mahmoud
 * drones repo
 */
@Repository
public interface DroneRepository extends JpaRepository<Drones, Long> {
	
	@Query("select d from Drones d where d.state = :state and d.batteryCapacityPercentage > :batLvl")
	public List<Drones> getDronesByStatesandBatteryLevel(@Param("state") DroneState state , @Param("batLvl")int batteryLevel);
	
}
