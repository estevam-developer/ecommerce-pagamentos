package br.com.estevam.ecommerce.produtos.config.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BeanValidationMessageDto {
    private String campo;
    private String erro;
}
