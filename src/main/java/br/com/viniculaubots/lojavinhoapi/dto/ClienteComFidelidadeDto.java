package br.com.viniculaubots.lojavinhoapi.dto;

import lombok.Data;

@Data
public class ClienteComFidelidadeDto extends ClienteDto {

	public ClienteComFidelidadeDto(int id, String nome, String cpf, int qntDeCompras) {
		super (id,nome,cpf);
		this.qntDeCompras = qntDeCompras;
	}
	
	private int qntDeCompras;
}