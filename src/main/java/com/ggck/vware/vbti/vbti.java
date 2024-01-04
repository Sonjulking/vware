package com.ggck.vware.vbti;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class vbti {

  @Id
  @SequenceGenerator(name = "vbti_sequence_generator", sequenceName = "vbti_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vbti_sequence_generator")
  Long id;

  int solo;
  int team;
  int atk;
  int def;
  int brain;
  int physical;

}
