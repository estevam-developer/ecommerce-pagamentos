package br.com.estevam.ecommerce.produtos.dto;

import br.com.estevam.ecommerce.produtos.model.StatusProduto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProdutoResponseDto {

    private Long Id;

    private String titulo;

    private String descricao;

    private StatusProduto status;

    private BigDecimal preco;

    private BigDecimal precoPromocional;

    private List<TagDto> tags;

    private List<String> midias;

    private String categoria;

}
