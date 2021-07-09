package br.com.viniculaubots.lojavinhoapi.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto  implements Serializable{

	private int id;
	private String nome;
	private String cpf;
}