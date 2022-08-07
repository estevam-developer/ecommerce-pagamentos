package br.com.estevam.ecommerce.produtos.dto;

import br.com.estevam.ecommerce.produtos.model.StatusProduto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoConsultaResponseDto {

    private Long Id;

    private String titulo;

    private StatusProduto status;

    private String categoria;

    private String imagemUrl;

}
