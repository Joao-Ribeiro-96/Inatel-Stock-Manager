package com.inatel.Stockquotemanager.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.sun.istack.NotNull;

@Entity
public class Stock {

	@Id @NotNull 
	@Column(name = "PR_KEY")
	private String id;

	@OneToMany(mappedBy = "stock")
	private List<Quote> quotes;
	
	public Stock() {
		
	}
	
	public Stock(String id) {
		this.id = id;

		
	}
	
//	public Stock(String id, List<Quote> quotes) {
//		this.id = id;
//		this.quotes = quotes;
//		
//	}


	public void setId(String id) {
		this.id = id;
	}

	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}

	public String getId() {
		return id;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

	public void addQuotes(Quote quote) {
		this.quotes.add(quote);
		
	}
	


}
