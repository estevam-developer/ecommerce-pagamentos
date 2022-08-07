package br.com.estevam.ecommerce.produtos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "arquivos")
@Getter
@Setter
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String caminho;

    @Column(length = 100, nullable = false)
    private String nome;

    @ManyToOne
    private Produto produto;

}