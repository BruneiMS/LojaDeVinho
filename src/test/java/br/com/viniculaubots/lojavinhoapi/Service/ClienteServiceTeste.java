package br.com.viniculaubots.lojavinhoapi.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.viniculaubots.lojavinhoapi.dao.ClienteDao;
import br.com.viniculaubots.lojavinhoapi.dao.HistoricoCompraDao;
import br.com.viniculaubots.lojavinhoapi.dto.ClienteComFidelidadeDto;
import br.com.viniculaubots.lojavinhoapi.dto.ClienteMaisCompraAnoDto;
import br.com.viniculaubots.lojavinhoapi.dto.ClienteValorTotalCompraDto;
import br.com.viniculaubots.lojavinhoapi.dto.RecomendacaoVinhoDto;
import br.com.viniculaubots.lojavinhoapi.entity.Cliente;
import br.com.viniculaubots.lojavinhoapi.entity.Compra;
import br.com.viniculaubots.lojavinhoapi.entity.Vinho;
import br.com.viniculaubots.lojavinhoapi.service.ClienteServicoImplementacao;

public class ClienteServiceTeste {
	 @Mock
	    public ClienteDao clienteDao;

	    @Mock
	    public HistoricoCompraDao historicoCompraDao;

	    @InjectMocks
	    public ClienteServicoImplementacao clienteServico;

	    private List<Cliente> clientes;
	    private List<Compra> compras;

	    @Before
	    public void setup() {
	        this.compras = new ArrayList<>();
	        this.clientes = new ArrayList<>();

	        Cliente cliente1 = new Cliente(1, "123", "test1");
	        Cliente cliente2 = new Cliente(2, "456", "test2");
	        Cliente cliente3 = new Cliente(3, "789", "test3");

	        this.clientes.add(cliente1);
	        this.clientes.add(cliente2);
	        this.clientes.add(cliente3);

	        List<Vinho> clienteCompraVinho1 = new ArrayList<>();
	        List<Vinho> clienteCompraVinho2 = new ArrayList<>();
	        List<Vinho> clienteCompraVinho3 = new ArrayList<>();

	        clienteCompraVinho1.add(new Vinho("Casa Silva Reserva",
	                "Chardonnay",
	                "Chile",
	                "Branco",
	                "2016",
	                new BigDecimal(79)));
	        clienteCompraVinho2.add(new Vinho("Casa Silva Reserva",
	                "Chardonnay",
	                "Chile",
	                "Branco",
	                "2016",
	                new BigDecimal(79)));

	        clienteCompraVinho3.add(new Vinho("Casa Silva Reserva",
	                "Chardonnay",
	                "Chile",
	                "Branco",
	                "2016",
	                new BigDecimal(79)));

	        clienteCompraVinho1.add(new Vinho("Casa Silva Reserva",
	                "Chardonnay",
	                "Chile",
	                "Branco",
	                "2016",
	                new BigDecimal(79)));

	        for (int i = 0; i < 6; i++) {
	        	clienteCompraVinho2.add(new Vinho("Vinho Recomendado Test",
	                    "Chardonnay",
	                    "Chile",
	                    "Branco",
	                    "2020",
	                    new BigDecimal(199)));
	        }



	        //2016
	        this.compras.add(new Compra("125","03-02-2016","123", new BigDecimal(1000), clienteCompraVinho1));
	        this.compras.add(new Compra("986","27-02-2016","456", new BigDecimal(730), clienteCompraVinho2));
	        this.compras.add(new Compra("098","30-02-2016","789", new BigDecimal(690), clienteCompraVinho3));

	        //2021
	        this.compras.add(new Compra("125","03-02-2021","123", new BigDecimal(50), clienteCompraVinho1));
	        this.compras.add(new Compra("098","22-03-2021","123", new BigDecimal(500), clienteCompraVinho3));
	        this.compras.add(new Compra("986","17-04-2021","456", new BigDecimal(355), clienteCompraVinho2));
	        this.compras.add(new Compra("098","06-07-2021","789", new BigDecimal(200), clienteCompraVinho3));
	    }

	    @Test
	    public void deveRetornarNenhumClienteValorMaisAlto() {
	        List<ClienteValorTotalCompraDto> response = clienteServico.listClienteValorTotalCompraDto();
	        assertEquals(0, response.size());
	    }

	    @Test
	    public void deveRetornarListaValorTotalCompraCliente(){
	        Mockito.when(clienteDao.clientes()).thenReturn(this.clientes);
	        Mockito.when(historicoCompraDao.compras()).thenReturn(this.compras);

	        List<ClienteValorTotalCompraDto> response = clienteServico.listClienteValorTotalCompraDto();
	        assertEquals(3, response.size());
	        assertEquals("125", response.get(0).getCpf());
	        assertEquals(new BigDecimal(650), response.get(0).getValorTotalCompra());
	        assertEquals(new BigDecimal(400), response.get(1).getValorTotalCompra());
	        assertEquals(new BigDecimal(300), response.get(2).getValorTotalCompra());
	    }

	    @Test
	    public void deveRetornarClienteComMaiorCompraAno2016() {
	        Mockito.when(clienteDao.clientes()).thenReturn(clientes);
	        Mockito.when(historicoCompraDao.compras()).thenReturn(compras);

	        ClienteMaisCompraAnoDto response = clienteServico.getClienteMaisCompraAnoDto("2016");
	        assertEquals(new BigDecimal(200), response.getMaiorValorCompra());
	        assertEquals("986", response.getCpf());
	    }

	    @Test
	    public void listaClienteComFidelidade() {
	        Mockito.when(clienteDao.clientes()).thenReturn(clientes);
	        Mockito.when(historicoCompraDao.compras()).thenReturn(compras);

	        List<ClienteComFidelidadeDto> response = clienteServico.listClienteComFidelidadeDto();
	        assertEquals(3, response.size());
	        assertEquals("125", response.get(0).getCpf());
	        assertEquals(3, response.get(0).getQntDeCompras());
	        assertEquals(2, response.get(1).getQntDeCompras());
	    }

	    @Test
	    public void recomendacaoVinho() {
	        Mockito.when(clienteDao.clientes()).thenReturn(clientes);
	        Mockito.when(historicoCompraDao.compras()).thenReturn(compras);

	        RecomendacaoVinhoDto response = clienteServico.recomendacaoVinhoDto(125);
	        assertEquals("Vinho Recomendado Teste", response.getProduto());
	    }
}
