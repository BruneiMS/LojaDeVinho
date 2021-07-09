package br.com.viniculaubots.lojavinhoapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

	private int id;
	private String cpf;
	private String nome;
}