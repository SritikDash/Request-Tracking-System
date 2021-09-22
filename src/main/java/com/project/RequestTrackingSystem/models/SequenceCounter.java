package com.project.RequestTrackingSystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="sequence_counter")
@Data
@Getter
@Setter
public class SequenceCounter {

	@Id
	@Column(name="seq_id")
	int id;

}
