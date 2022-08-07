package br.com.estevam.ecommerce.produtos.config.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class GeneralExceptionMessageDto {
    private LocalDateTime timestamp;
    private Integer status;
    private String message;
}
