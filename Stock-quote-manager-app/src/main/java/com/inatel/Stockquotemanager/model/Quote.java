package com.inatel.Stockquotemanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Quote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	private BigDecimal price;
	
	@NonNull
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "stock_pr_key")
	private Stock stock;
	
	public Quote(LocalDate date, BigDecimal price, Stock stock) {
		this.date = date;
		this.price = price;
		this.stock = stock;
		
	}
	
	public Quote() {
		
	}

	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Long getId() {
		return id;
	}
	@Override
	public String toString() {
		 
		return "" + this.date.format(DateTimeFormatter.ISO_LOCAL_DATE) + " : " + this.price;
		
	}
	
	
	

}
