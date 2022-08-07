package br.com.estevam.ecommerce.produtos.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TagDto {
    @NotBlank
    @Size(max = 20)
    private String tag;
}
