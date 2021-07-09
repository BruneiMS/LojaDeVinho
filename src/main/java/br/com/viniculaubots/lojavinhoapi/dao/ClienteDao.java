package br.com.viniculaubots.lojavinhoapi.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.viniculaubots.lojavinhoapi.entity.Cliente;

@Service
public class ClienteDao {

	private final Environment environment;
	private final RestTemplate restTemplate;
	
	@Autowired
	public ClienteDao(Environment environment, RestTemplate restTemplate) {
		this.environment = environment;
		this.restTemplate = restTemplate;
	}
	
	public List<Cliente> clientes(){
		String servicoUrl = Objects.requireNonNull(environment.getProperty("cliente.servico.url"));
		
		ResponseEntity<Cliente[]> respota = restTemplate.exchange(servicoUrl, HttpMethod.GET, null, Cliente[].class);
		return Arrays.asList(respota.getBody());
	}
}