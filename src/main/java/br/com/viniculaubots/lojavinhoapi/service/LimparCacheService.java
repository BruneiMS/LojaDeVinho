package br.com.viniculaubots.lojavinhoapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;

public class LimparCacheService {

	Logger logger = LoggerFactory.getLogger(LimparCacheService.class);
	
	@Scheduled(fixedDelayString = "${cache.expiration.in.milliseconds}")
	@CacheEvict(value = {"recomendacao", "total-compras", "alta", "fidelidade"}, allEntries = true)
	
	public void limparAllCache() {
		logger.info("Todo o cache foi limpo.");
	}
}