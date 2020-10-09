package com.inatel.Stockquotemanager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.inatel.Stockquotemanager.model.Quote;
import com.inatel.Stockquotemanager.model.Stock;

public class TestCreateListOfQuotesToStock {
	
public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("stock");
		EntityManager em = emf.createEntityManager();
		
		
		Stock stock = new Stock();
		stock.setId("petr2");
		
		Quote quote = new Quote();
		quote.setDate(LocalDateTime.now());
		quote.setPrice(new BigDecimal(200));
		quote.setStock(stock);
		
		Quote quote2 = new Quote();
		quote2.setDate(LocalDateTime.now());
		quote2.setPrice(new BigDecimal(201));
		quote2.setStock(stock);
		
		stock.setQuotes(Arrays.asList(quote,quote2));
		
		

		
		em.getTransaction().begin();
		em.persist(stock);
		em.persist(quote);
		em.persist(quote2);
		
		em.getTransaction().commit();
		
	}

}
