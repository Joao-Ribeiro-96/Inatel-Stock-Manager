package com.inatel.Stockquotemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inatel.Stockquotemanager.model.Stock;

public interface StockRepository extends JpaRepository<Stock, String>{
		
		List<Stock> findByid (String id);
		
		

}
