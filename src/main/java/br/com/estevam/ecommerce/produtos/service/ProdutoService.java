package br.com.estevam.ecommerce.produtos.service;

import antlr.StringUtils;
import br.com.estevam.ecommerce.produtos.dto.ProdutoRequestDto;
import br.com.estevam.ecommerce.produtos.dto.ProdutoResponseDto;
import br.com.estevam.ecommerce.produtos.dto.ProdutoConsultaResponseDto;
import br.com.estevam.ecommerce.produtos.model.Arquivo;
import br.com.estevam.ecommerce.produtos.model.Categoria;
import br.com.estevam.ecommerce.produtos.model.Produto;
import br.com.estevam.ecommerce.produtos.model.StatusProduto;
import br.com.estevam.ecommerce.produtos.repository.ArquivoRepository;
import br.com.estevam.ecommerce.produtos.repository.CategoriaRepository;
import br.com.estevam.ecommerce.produtos.repository.ProdutoRepository;
import br.com.estevam.ecommerce.produtos.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ArquivoRepository arquivoRepository;

    @Autowired
    private ModelMapper mapper;

    public ProdutoResponseDto create(ProdutoRequestDto dto, String url) {

        Produto produto = mapper.map(dto, Produto.class);

        if (dto.getCategoriaId() != null)
            produto.setCategoria(categoriaRepository.findById(dto.getCategoriaId()).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada.")));

        if (dto.getTags() != null)
            produto.setTags(produto.getTags()
                    .stream()
                    .map(tag -> tagRepository.findByTag(tag.getTag())
                            .orElse(tag))
                    .collect(Collectors.toList()));

        produto = produtoRepository.save(produto);

        if (dto.getArquivos() != null)
            for (Arquivo arquivo : produto.getArquivos()) {
                arquivo.setProduto(produto);
                arquivoRepository.save(arquivo);
            }

        return mapToProdutoResponseDto(produto, url);

    }

    private ProdutoResponseDto mapToProdutoResponseDto(Produto produto, String url) {
        ProdutoResponseDto dto = mapper.map(produto, ProdutoResponseDto.class);

        if (produto.getCategoria() != null)
            dto.setCategoria(produto.getCategoria().getNome());

        if (produto.getArquivos() != null)
            dto.setMidias(produto.getArquivos().stream().map(arquivo -> url + "/produtos/" + produto.getId() + "/midias/" + arquivo.getNome()).collect(Collectors.toList()));

        return dto;
    }

    public ProdutoResponseDto findById(Long id, String url) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));

        return mapToProdutoResponseDto(produto, url);
    }

    public List<ProdutoConsultaResponseDto> search(String titulo, StatusProduto status, String categoria, String url, Sort sort) {

        List<Produto> produtos = produtoRepository.findAll(searchSpec(titulo, status, categoria), sort);

        return produtos.stream().map(produto ->  mapToProdutoConsultaResponseDto(produto, url)).collect(Collectors.toList());

    }

    private Specification<Produto> searchSpec(String titulo, StatusProduto status, String categoria) {

        return (Specification<Produto>) (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (titulo != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%")));
            }

            if (status != null) {
                predicates.add(criteriaBuilder.and((root.get("status").in(status))));
            }

            if (categoria != null) {
                Join<Categoria, Produto> categoriaProdutoJoin = root.join("categoria");
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(categoriaProdutoJoin.get("nome")), "%" + categoria.toLowerCase() + "%")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    private ProdutoConsultaResponseDto mapToProdutoConsultaResponseDto(Produto produto, String url) {
        ProdutoConsultaResponseDto dto = mapper.map(produto, ProdutoConsultaResponseDto.class);

        if (produto.getCategoria() != null)
            dto.setCategoria(produto.getCategoria().getNome());

        if (!produto.getArquivos().isEmpty())
            dto.setImagemUrl(url + "/produtos/" + produto.getId() + "/midias/" + produto.getArquivos().get(0).getNome());

        return  dto;
    }

    public void delete(Long id) {
        produtoRepository.deleteById(id);
    }
}
