package br.com.viniculaubots.lojavinhoapi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.viniculaubots.lojavinhoapi.dao.ClienteDao;
import br.com.viniculaubots.lojavinhoapi.dao.HistoricoCompraDao;
import br.com.viniculaubots.lojavinhoapi.dto.ClienteComFidelidadeDto;
import br.com.viniculaubots.lojavinhoapi.dto.ClienteMaisCompraAnoDto;
import br.com.viniculaubots.lojavinhoapi.dto.ClienteValorTotalCompraDto;
import br.com.viniculaubots.lojavinhoapi.dto.RecomendacaoVinhoDto;
import br.com.viniculaubots.lojavinhoapi.entity.Cliente;
import br.com.viniculaubots.lojavinhoapi.entity.Compra;
import br.com.viniculaubots.lojavinhoapi.entity.Vinho;

@Service
public class ClienteServicoImplementacao implements ClienteServico {
	
	private final ClienteDao clienteDao;
	private final HistoricoCompraDao historicoCompraDao;
	
	@Autowired
	public ClienteServicoImplementacao(ClienteDao clienteDao, HistoricoCompraDao historicoCompraDao) {
		this.clienteDao = clienteDao;
		this.historicoCompraDao = historicoCompraDao;
	}
	
	@Cacheable(value = ("maior-total-compras"))
	@Override
	public List<ClienteValorTotalCompraDto> listClienteValorTotalCompraDto(){
		
		List<ClienteValorTotalCompraDto> resposta = new ArrayList<ClienteValorTotalCompraDto>();
		
		Map<String, List<Compra>> agruparCompraPorCliente = listaCompraPorClientes(historicoCompraDao.compras());
		List<Cliente> clientes = clienteDao.clientes();
		
		BigDecimal totalClientes;
		for (Cliente cliente : clientes) {
			totalClientes = somaDeCompras(agruparCompraPorCliente.get(cliente.getCpf()));
			
			resposta.add(new ClienteValorTotalCompraDto(cliente.getId(), cliente.getNome(), cliente.getCpf(), totalClientes));
		}
		
		return resposta.stream()
				.sorted(Comparator.comparing(ClienteValorTotalCompraDto::getValorTotalCompra).reversed())
				.collect(Collectors.toList());
	}
	
	@Cacheable(value = "alta", key="#ano")
	@Override
	public ClienteMaisCompraAnoDto getClienteMaisCompraAnoDto(String ano) {
		
		List<Compra> unicaCompraAno = unicaCompraAno(historicoCompraDao.compras(), ano);
		
		Optional<Compra> altaCompra = unicaCompraAno.stream().max(Comparator.comparing(Compra::getValorTotal));
		
		if (altaCompra.isPresent()) {
			List<Cliente> clientes = clienteDao.clientes();
		
			Cliente clienteMaiorCompraAno = clientes.stream()
					.filter(cliente -> cliente.getCpf().equals(altaCompra.get().getCliente()))
					.findFirst().get();
			
			return new ClienteMaisCompraAnoDto(
				clienteMaiorCompraAno.getId(), 
				clienteMaiorCompraAno.getNome(), 
				clienteMaiorCompraAno.getCpf(), 
				altaCompra.get().getValorTotal());
		}
		
		return null;
	}

	@Cacheable(value = "fidelidade")
	@Override
	public List<ClienteComFidelidadeDto> listClienteComFidelidadeDto(){
		
		Map<String, List<Compra>> agruparCompraPorCliente = listaCompraPorClientes(historicoCompraDao.compras());
		List<Cliente> clientes = clienteDao.clientes();
		
		List<ClienteComFidelidadeDto> resposta = new ArrayList<ClienteComFidelidadeDto>();
		
		int contaDeCompra;
		for (Cliente cliente : clientes) {
			contaDeCompra = agruparCompraPorCliente.get(cliente.getCpf()).size();
			resposta.add(new ClienteComFidelidadeDto(cliente.getId(), cliente.getNome(), cliente.getCpf(), contaDeCompra));
		}
		
		return resposta.stream()
				.sorted(Comparator.comparing(ClienteComFidelidadeDto::getQntDeCompras).reversed())
				.collect(Collectors.toList());
	}
	
	
	@Cacheable(value = "recomendacao", key="#clienteId")
	@Override
	public RecomendacaoVinhoDto recomendacaoVinhoDto(int clienteId) {
		
		List<Cliente> clientes = clienteDao.clientes();
		Cliente clliente = clientes.stream().filter(c -> c.getId() == clienteId).findFirst().get();
		
		Map<String, List<Compra>> agruparCompraPorCliente = listaCompraPorClientes(historicoCompraDao.compras());
		List<Compra> compras = agruparCompraPorCliente.get(clliente.getCpf());
		
		Vinho vinhoMaisConsumido = getVinhoMaisConsumido(compras);
		
		return new RecomendacaoVinhoDto(
				vinhoMaisConsumido.getProduto(), vinhoMaisConsumido.getVariedade(), vinhoMaisConsumido.getPais(),vinhoMaisConsumido.getCategoria(),
				vinhoMaisConsumido.getSafra(),vinhoMaisConsumido.getPreco());
	}
	
	private Vinho getVinhoMaisConsumido(List<Compra> clienteCompra) {
		List<Vinho> vinhos = new ArrayList<Vinho>();
		for (Compra compra : clienteCompra) {
			vinhos.addAll(compra.getItens());
		}
		
		int vinhoConsumido = 0;
		Vinho vinhoMaisConsumido = null;
		
		Map<String, List<Vinho>> listaVinhos = vinhos.stream().collect(Collectors.groupingBy(Vinho::getProduto));
		for (var v : listaVinhos.entrySet()) {
			
			if(v.getValue().size() > vinhoConsumido) {
				vinhoConsumido = v.getValue().size();
				vinhoMaisConsumido = v.getValue().get(0);
			}
		}
		return vinhoMaisConsumido;
	}
	
	private Map<String, List<Compra>> listaCompraPorClientes(List<Compra> compras){
		return compras.stream().collect(Collectors.groupingBy(Compra::getCliente));
	}
	
	private BigDecimal somaDeCompras(List<Compra> compras) {
		return compras.stream().map(Compra::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	private List<Compra> unicaCompraAno(List<Compra> compras, String ano){
		
		Map<String, List<Compra>> compraPorAno = compras.stream().collect(Collectors.groupingBy(Compra::ano));
		
		return compraPorAno.get(ano).stream()
				.filter(compra -> compra.getItens().size() == 1)
				.collect(Collectors.toList());
	}
}