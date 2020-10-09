package com.inatel.Stockquotemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inatel.Stockquotemanager.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
	
	List<Quote> findByStockId(String id);

}
