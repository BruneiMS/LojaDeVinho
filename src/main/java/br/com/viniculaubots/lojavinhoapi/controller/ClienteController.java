package br.com.viniculaubots.lojavinhoapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.viniculaubots.lojavinhoapi.dto.ClienteComFidelidadeDto;
import br.com.viniculaubots.lojavinhoapi.dto.ClienteMaisCompraAnoDto;
import br.com.viniculaubots.lojavinhoapi.dto.ClienteValorTotalCompraDto;
import br.com.viniculaubots.lojavinhoapi.dto.RecomendacaoVinhoDto;
import br.com.viniculaubots.lojavinhoapi.service.ClienteServico;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "cliente")
@RequestMapping(value = "api/v1/cliente", produces = "applicatio/json")
public class ClienteController {
	
	private final ClienteServico clienteServico;
	
	public ClienteController(ClienteServico clienteServico) {
		this.clienteServico = clienteServico;
	}
	
	@ApiOperation(value = "Lista de clientes ordenados pelo maior total valor de compras. ", response = ClienteValorTotalCompraDto[].class)
	@GetMapping("/maior-total-compras")
	public ResponseEntity<?> alta() {
		return ResponseEntity.ok(this.clienteServico.listClienteValorTotalCompraDto());
	}

	@ApiOperation(value = "Retorna o cliente com a compra única mais cara em um ano específico. ", response = ClienteMaisCompraAnoDto[].class)
	@GetMapping("/alta/{ano}")
	public ResponseEntity<?> altaPorAno(@PathVariable String ano) {
		return ResponseEntity.ok(this.clienteServico.getClienteMaisCompraAnoDto(ano));
	}
	
	@ApiOperation(value = "Lista de clientes ordenados por fidelidade. ", response = ClienteComFidelidadeDto[].class)
	@GetMapping("/fidelidade")
	public ResponseEntity<?> fidelidade() {
		return ResponseEntity.ok(this.clienteServico.listClienteComFidelidadeDto());
	}
	
	@ApiOperation(value = "Recomendação de vinho pelo histórico de compras de um cliente específico. ", response = RecomendacaoVinhoDto[].class)
	@GetMapping("/recomendacao/{clienteId}")
	public ResponseEntity<?> recomendacao(@PathVariable int clienteId) {
		return ResponseEntity.ok(this.clienteServico.recomendacaoVinhoDto(clienteId));
	}
}
