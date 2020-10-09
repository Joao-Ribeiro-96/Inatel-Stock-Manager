package com.inatel.Stockquotemanager.controller.Dto;

import java.util.List;
import java.util.stream.Collectors;

import com.inatel.Stockquotemanager.model.Stock;

public class StockDto {
	
	private String id;
	
	private String quotes;
	
	public StockDto(Stock stock) {
		//handles a non-quoted stock creation to prevent null quotes
		try {
		this.quotes = stock.getQuotes().toString();
		}catch(NullPointerException e) {
		}finally {
			this.id = stock.getId();
			}
	}
	public StockDto() {
		
	}
	

	public String getId() {
		return id;
	}

	public String getQuotes() {
		return quotes;
	}


	public static List<StockDto> convert(List<Stock> stocks) {
		
		return stocks.stream().map(StockDto::new).collect(Collectors.toList());
	}

}
