package br.com.viniculaubots.lojavinhoapi.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vinho {

	private String produto;
	private String variedade;
	private String pais;
	private String categoria;
	private String safra;
	private BigDecimal preco;
	
}