package br.com.estevam.ecommerce.produtos.config;

import br.com.estevam.ecommerce.produtos.model.StatusProduto;
import org.springframework.core.convert.converter.Converter;

public class StatusProdutoConveter implements Converter<String, StatusProduto> {
    @Override
    public StatusProduto convert(String source) {
        return StatusProduto.valueOf(source.toUpperCase());
    }
}