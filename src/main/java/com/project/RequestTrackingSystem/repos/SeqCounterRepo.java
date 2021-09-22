package com.project.RequestTrackingSystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.RequestTrackingSystem.models.SequenceCounter;


@Repository
public interface SeqCounterRepo extends JpaRepository<SequenceCounter, Integer> {
	@Query(value="SELECT case when max(seq_id) is null then 1 else max(seq_id)+1 end from rts.sequence_counter", nativeQuery = true)
	public int getSeqNumber();
}	

//case 
//when user_Name is null then "default value" 
//else user_name 
//end

//max(seq_id) + 1
