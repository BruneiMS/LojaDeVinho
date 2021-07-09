package br.com.viniculaubots.lojavinhoapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecomendacaoVinhoDto implements Serializable {

	private String produto;
	private String variedade;
	private String pais;
	private String categoria;
	private String safra;
	private BigDecimal preco;
}