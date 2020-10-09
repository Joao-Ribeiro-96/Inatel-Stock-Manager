package com.inatel.Stockquotemanager.controller.form;

import java.util.List;

import com.inatel.Stockquotemanager.model.Stock;
import com.sun.istack.NotNull;

public class StockForm {

	
	public StockForm(String id) {
		this.id = id;
	}

	private String id;
	private List<String> quotes;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getQuote() {
		return quotes;
	}
	public void setQuote(List<String> quotes) {
		this.quotes = quotes;
	}
	public Stock convertToStock(String id) {
		return new Stock(id);
	}
	
	
//	public Stock convertToStock(String id, QuoteRepository repository) {
//		List<Quote> quotes = repository.findByStockId(id);
//		return new Stock(id);
//	}
	
	public String ToString() {
		return "" + this.id + this.quotes;
		
	}
	
	

}
