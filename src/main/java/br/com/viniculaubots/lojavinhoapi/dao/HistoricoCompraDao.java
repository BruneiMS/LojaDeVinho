package br.com.viniculaubots.lojavinhoapi.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.viniculaubots.lojavinhoapi.entity.Compra;

@Service
public class HistoricoCompraDao {

	private final Environment environment;
	private final RestTemplate restTemplate;
	
	public HistoricoCompraDao(Environment environment, RestTemplate restTemplate) {
		this.environment = environment;
		this.restTemplate = restTemplate;
	}
	
	public List<Compra> compras(){
		String servicoUrl = Objects.requireNonNull(environment.getProperty("compra.hitorico.servico.url"));
		
		ResponseEntity<Compra[]> resposta = restTemplate.exchange(servicoUrl, HttpMethod.GET, null, Compra[].class);
		return Arrays.asList(resposta.getBody());
	}
}