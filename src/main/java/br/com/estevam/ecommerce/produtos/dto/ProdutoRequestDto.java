package br.com.estevam.ecommerce.produtos.dto;

import br.com.estevam.ecommerce.produtos.model.StatusProduto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProdutoRequestDto {

    private Long Id;

    @NotBlank
    @Size(max = 100)
    private String titulo;

    @NotBlank
    @Size(max = 500)
    private String descricao;

    @NotNull
    private StatusProduto status;

    @Digits(integer = 15, fraction = 2)
    @NotNull
    private BigDecimal preco;

    @Digits(integer = 15, fraction = 2)
    private BigDecimal precoPromocional;

    private List<TagDto> tags;

    private List<ArquivoRequestDto> arquivos;

    private Long categoriaId;

}
