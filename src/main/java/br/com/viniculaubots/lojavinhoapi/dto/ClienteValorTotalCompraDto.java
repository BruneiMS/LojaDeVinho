package br.com.viniculaubots.lojavinhoapi.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ClienteValorTotalCompraDto extends ClienteDto{

	public ClienteValorTotalCompraDto(int id, String nome, String cpf, BigDecimal valorTotalCompra) {
		super(id, nome, cpf);
		this.valorTotalCompra = valorTotalCompra;
	}
	
	private BigDecimal valorTotalCompra;

}