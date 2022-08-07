package br.com.estevam.ecommerce.produtos.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CategoriaResponseDto {

    private Long id;
    private String nome;

}
