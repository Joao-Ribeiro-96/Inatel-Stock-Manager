package com.inatel.Stockquotemanager.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.inatel.Stockquotemanager.config.validation.InvalidIDException;
import com.inatel.Stockquotemanager.config.validation.ValidationException;
import com.inatel.Stockquotemanager.controller.Dto.StockDto;
import com.inatel.Stockquotemanager.controller.form.QuoteForm;
import com.inatel.Stockquotemanager.controller.form.StockForm;
import com.inatel.Stockquotemanager.model.Quote;
import com.inatel.Stockquotemanager.model.Stock;
import com.inatel.Stockquotemanager.repository.QuoteRepository;
import com.inatel.Stockquotemanager.repository.StockRepository;

@Controller
public class StockQuoteManagerController {

	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private CacheManager cacheManager;


	@RequestMapping(value = "/stock", method = RequestMethod.POST)
	// Receives a JSON node to create Stock
	public ResponseEntity<StockDto> createStock(@RequestBody ObjectNode json, UriComponentsBuilder uriBuilder)
			throws InvalidIDException, ValidationException {

		String currentStockId = json.get("id").toString().replaceAll("\"", "");

		if (currentStockId.isEmpty()) {
			throw new InvalidIDException();
		}

		List<String> managerStocks = getAvailableStocks();
//		List<String> managerStocks = getAvailableStocks();

		if (managerStocks.contains(currentStockId)) {

			StockForm form = new StockForm(currentStockId);

			Stock stock = form.convertToStock(form.getId());

			stockRepository.save(stock);

			// verify if there is quotes to be created
			if (json.get("quotes") != null) {

				List<Quote> formatted = createNewQuotes(currentStockId, json, stock);
				quoteRepository.saveAll(formatted);
			}

//			Stock stock = form.convertToStock(form.getId(), quoteRepository);
			// stockRepository.save(stock);

			URI uri = uriBuilder.path("/stock/{id}").buildAndExpand(stock.getId()).toUri();

			// returns DTO with created stock and quotes (if any)
			return ResponseEntity.created(uri).body(new StockDto(stock));
		} else {
			throw new ValidationException();
		}

	}



	@Cacheable(value = "stockManagerStocks")
	@RequestMapping("/availableStocks")
	@ResponseBody
	public List<String> getAvailableStocks() {
		
		System.out.println("Not cached");

		RestTemplate restTemplate = new RestTemplate();

		String response = restTemplate.getForObject("http://localhost:8080/stock", String.class);
		Gson gson = new Gson();

		JsonObject[] json = gson.fromJson(response, JsonObject[].class);

		List<String> ids = new ArrayList<>();

		for (JsonObject entry : json) {
			ids.add(entry.get("id").toString().replaceAll("\"", ""));
		}
	

		return ids;
	}

	@RequestMapping(value = "/stockcache", method = RequestMethod.DELETE)
	@ResponseBody
	public String resetCache() {
		cacheManager.getCache("StockManagerStocks").clear();
		System.out.println("Cache Erased");
		return "Cache erased";
	}

	

	@PostConstruct
	public void register() {

		System.out.println(" ============ Registering to Stock Manager ============== ");

		String url = "http://localhost:8080/notification";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		Map<String, Object> map = new HashMap<>();
		map.put("host", "localhost");
		map.put("port", 8081);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			System.out.println("Request Successful");
			System.out.println(response.getBody());
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}

		System.out.println(" ======================= Complete! ====================== ");
	}

	// Create quotes with POST info from createStock
	private List<Quote> createNewQuotes(String currentStockId, ObjectNode json, Stock stock) {
		List<String> newQuotes = Arrays.asList(json.get("quotes").toString().split(","));
		List<Quote> formattedQuotes = new ArrayList<>();

		for (String quote : newQuotes) {
			QuoteForm qform = new QuoteForm(quote.replaceAll("\"", ""), currentStockId);
			formattedQuotes.add(qform.convertToQuote(currentStockId, stockRepository));
		}
		stock.setQuotes(formattedQuotes);
		return formattedQuotes;

	}



	@RequestMapping(value = "/stock", method = RequestMethod.GET)
	@ResponseBody
	public List<StockDto> getAllStockQuotes(String id) {

		if (id == null) {
			List<Stock> stocks = stockRepository.findAll();
			return StockDto.convert(stocks);
		} else {
			List<Stock> stocks = stockRepository.findByid(id);
			return StockDto.convert(stocks);
		}
	}



//		@RequestMapping("/getManagerStocks")
//		//View all StockManager's stocks
//		private static String getManagerStocks()
//		{
//		    final String uri = "http://localhost:8080/stock";
//
//		    RestTemplate restTemplate = new RestTemplate();
//		    String result = restTemplate.getForObject(uri, String.class);
//
//		    return result;
//		}



	@RequestMapping("/populate")
	// deprecated
	public void setAllQuotes() {

		System.out.println("populating");
		Stock stock = new Stock();
		stock.setId("petr43");

		Quote quote = new Quote();
		quote.setDate(LocalDateTime.now().toLocalDate());
		quote.setPrice(new BigDecimal(203));
		quote.setStock(stock);

		Quote quote2 = new Quote();
		quote2.setDate(LocalDateTime.now().toLocalDate());
		quote2.setPrice(new BigDecimal(207));
		quote2.setStock(stock);

		stock.setQuotes(Arrays.asList(quote, quote2));

		quoteRepository.saveAll(Arrays.asList(quote, quote2));

	}

}
