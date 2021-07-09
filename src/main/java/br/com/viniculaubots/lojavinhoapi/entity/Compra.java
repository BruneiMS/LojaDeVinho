package br.com.viniculaubots.lojavinhoapi.entity;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Compra {

	private String codigo;
	private String data;
	private String cliente;
	private BigDecimal valorTotal;
	private List<Vinho> itens;
	
	public String ano() {
		return this.data.split("-")[2];
	}
	
}