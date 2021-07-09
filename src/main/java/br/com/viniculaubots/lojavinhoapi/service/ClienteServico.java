package br.com.viniculaubots.lojavinhoapi.service;

import java.util.List;

import br.com.viniculaubots.lojavinhoapi.dto.ClienteComFidelidadeDto;
import br.com.viniculaubots.lojavinhoapi.dto.ClienteMaisCompraAnoDto;
import br.com.viniculaubots.lojavinhoapi.dto.ClienteValorTotalCompraDto;
import br.com.viniculaubots.lojavinhoapi.dto.RecomendacaoVinhoDto;

public interface ClienteServico {

	List<ClienteValorTotalCompraDto> listClienteValorTotalCompraDto();
	ClienteMaisCompraAnoDto getClienteMaisCompraAnoDto(String ano);
	List<ClienteComFidelidadeDto> listClienteComFidelidadeDto();
	RecomendacaoVinhoDto recomendacaoVinhoDto(int clienteId);
	
}