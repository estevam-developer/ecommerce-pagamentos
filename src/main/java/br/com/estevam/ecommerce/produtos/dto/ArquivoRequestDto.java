package br.com.estevam.ecommerce.produtos.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ArquivoRequestDto {

    private Long id;

    @NotBlank
    @Size(max = 200)
    private String caminho;

    @NotBlank
    @Size(max = 100)
    private String nome;

}
