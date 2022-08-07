package br.com.estevam.ecommerce.produtos.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CategoriaRequestDto {

    private Long id;

    @NotBlank
    @Size(max = 50)
    private String nome;

}
