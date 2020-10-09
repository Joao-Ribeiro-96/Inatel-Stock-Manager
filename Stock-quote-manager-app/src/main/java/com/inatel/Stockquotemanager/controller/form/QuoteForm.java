package com.inatel.Stockquotemanager.controller.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.inatel.Stockquotemanager.model.Quote;
import com.inatel.Stockquotemanager.model.Stock;
import com.inatel.Stockquotemanager.repository.QuoteRepository;
import com.inatel.Stockquotemanager.repository.StockRepository;

public class QuoteForm {
	
	private Long id;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	private double price;
	private String stock;
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	
	public QuoteForm(String newQuote, String stock) {
		String quoteArray [] = newQuote.split(":");
		this.date = LocalDate.parse(quoteArray[0].replace("{",""), formatter);
		this.price = Double.parseDouble(quoteArray[1].replace("}",""));
		this.stock = stock;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	//Convert quote form to quote object
	public Quote convertToQuote(String stockId, StockRepository repository) {
		List<Stock> stocks = repository.findByid(stockId);
		return new Quote(date, new BigDecimal(price), stocks.get(0));
		
	}
	
	
	

}
