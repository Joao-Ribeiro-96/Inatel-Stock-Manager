package com.inatel.Stockquotemanager.controller.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inatel.Stockquotemanager.model.Quote;


	public class QuoteDto {
		
		private Long id;
		
		@JsonFormat(pattern="yyyy-MM-dd")
		private LocalDate date;
		private BigDecimal price;
		private String stock;
		
		public QuoteDto(Quote quote) {
			this.id = quote.getId();
			this.date = quote.getDate();
			this.price = quote.getPrice();
			this.stock = quote.getStock().getId();
		}
		
		public Long getId() {
			return id;
		}
		public String getStock() {
			return stock;
		}
		
		public LocalDate getDate() {
			return date;
		}
		public BigDecimal getPrice() {
			return price;
		}

		public static List<QuoteDto> convert(List<Quote> quotes) {
		
		//maps a stream of Quote to create QuoteDTO's  
		return quotes.stream().map(QuoteDto::new).collect(Collectors.toList());
		}
		
		
		

	}


