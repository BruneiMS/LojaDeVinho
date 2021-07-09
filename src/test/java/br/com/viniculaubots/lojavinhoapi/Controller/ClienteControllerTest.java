package br.com.viniculaubots.lojavinhoapi.Controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.viniculaubots.lojavinhoapi.controller.ClienteController;
import br.com.viniculaubots.lojavinhoapi.dto.ClienteComFidelidadeDto;
import br.com.viniculaubots.lojavinhoapi.dto.ClienteMaisCompraAnoDto;
import br.com.viniculaubots.lojavinhoapi.dto.ClienteValorTotalCompraDto;
import br.com.viniculaubots.lojavinhoapi.dto.RecomendacaoVinhoDto;
import br.com.viniculaubots.lojavinhoapi.service.ClienteServico;

public class ClienteControllerTest {

	@Mock
	public ClienteServico clienteServico;

	@InjectMocks
	public ClienteController clienteController;

	@Test
	public void deveRetornarListaClienteValorTotalCompra() {

		List<ClienteValorTotalCompraDto> resposta = new ArrayList<ClienteValorTotalCompraDto>();

		resposta.add(new ClienteValorTotalCompraDto(1, "teste1", "teste1", new BigDecimal(50)));
		resposta.add(new ClienteValorTotalCompraDto(2, "teste2", "teste2", new BigDecimal(150)));
		resposta.add(new ClienteValorTotalCompraDto(3, "teste3", "teste3", new BigDecimal(200)));
		resposta.add(new ClienteValorTotalCompraDto(4, "teste4", "teste4", new BigDecimal(250)));

		Mockito.when(clienteServico.listClienteValorTotalCompraDto()).thenReturn(resposta);

		ResponseEntity<?> resp = clienteController.alta();
		assertEquals(resp.getStatusCode(), HttpStatus.OK);
		assertNotNull(resp.getBody());
		assertEquals(4, ((ArrayList<?>) resp.getBody()).size());
	}

	@Test
	public void deveRetornarClienteCompraMaisAltaAno() {
		String ano = "2014";

		ClienteMaisCompraAnoDto responstaAno = new ClienteMaisCompraAnoDto(1, "test", "123", new BigDecimal(100));

        Mockito.when(clienteServico.listClienteValorTotalCompraDto()).thenReturn((List<ClienteValorTotalCompraDto>) responstaAno);

		ResponseEntity<?> resp2 = clienteController.altaPorAno(ano);
		assertEquals(resp2.getStatusCode(), HttpStatus.OK);
		assertNotNull(resp2.getBody());
	}
	
	@Test
    public void fidelidade() {
        List<ClienteComFidelidadeDto> response = new ArrayList<>();

        response.add(new ClienteComFidelidadeDto(1, "test1", "test1", 10));
        response.add(new ClienteComFidelidadeDto(2, "test2", "test2", 15));
        response.add(new ClienteComFidelidadeDto(3, "test3", "test3", 30));

        Mockito.when(clienteServico.listClienteComFidelidadeDto()).thenReturn(response);

        ResponseEntity<?> resp = clienteController.fidelidade();
        assertEquals(resp.getStatusCode(), HttpStatus.OK);
        assertNotNull(resp.getBody());
        assertEquals(3, ((ArrayList<?>) resp.getBody()).size());
    }

	@Test
    public void recomendacao() {
        int customerId = 1;

        RecomendacaoVinhoDto respontaRec = new RecomendacaoVinhoDto("test", "test", "test", "test", "test", new BigDecimal(10));

        Mockito.when(clienteServico.recomendacaoVinhoDto(customerId)).thenReturn(respontaRec);

        ResponseEntity<?> resp = clienteController.recomendacao(customerId);
        assertEquals(resp.getStatusCode(), HttpStatus.OK);
        assertNotNull(resp.getBody());
    }
}