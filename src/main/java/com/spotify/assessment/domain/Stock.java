package com.spotify.assessment.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long stockId;

private String symbol;

private double lastSalePrice;

private int volume;

@ManyToMany(cascade=CascadeType.ALL)
private List <Account> account;
}
