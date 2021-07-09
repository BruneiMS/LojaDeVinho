package br.com.viniculaubots.lojavinhoapi.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ClienteMaisCompraAnoDto extends ClienteDto {

	public ClienteMaisCompraAnoDto(int id, String nome, String cpf, BigDecimal maiorValorCompra) {
		super(id, nome, cpf);
		this.maiorValorCompra = maiorValorCompra;
	}
	
	private BigDecimal maiorValorCompra;
}