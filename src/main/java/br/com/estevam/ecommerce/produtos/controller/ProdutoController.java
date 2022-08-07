package br.com.estevam.ecommerce.produtos.controller;

import br.com.estevam.ecommerce.produtos.dto.ArquivoRequestDto;
import br.com.estevam.ecommerce.produtos.dto.ProdutoRequestDto;
import br.com.estevam.ecommerce.produtos.dto.ProdutoResponseDto;
import br.com.estevam.ecommerce.produtos.dto.ProdutoConsultaResponseDto;
import br.com.estevam.ecommerce.produtos.model.StatusProduto;
import br.com.estevam.ecommerce.produtos.service.ProdutoService;

import br.com.estevam.ecommerce.produtos.utils.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ImageStorageService imageStorageService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> create(@RequestPart("data") @Valid ProdutoRequestDto dto, @RequestPart(name = "file", required = false)  List<MultipartFile> files, UriComponentsBuilder uriBuilder) {

        if (files != null)
            dto.setArquivos(files.stream().map(this::mapToArquivoRequestDto).collect(Collectors.toList()));

        ProdutoResponseDto produtoCriado = produtoService.create(dto, uriBuilder.toUriString());
        imageStorageService.store(files, produtoCriado.getId());

        URI uriRecurso = uriBuilder.path("/produtos/{id}").buildAndExpand(produtoCriado.getId()).toUri();

        return ResponseEntity.created(uriRecurso).body(produtoCriado);
    }

    private ArquivoRequestDto mapToArquivoRequestDto(MultipartFile file) {
        ArquivoRequestDto dto = new ArquivoRequestDto();

        dto.setCaminho(imageStorageService.getRootPath());
        dto.setNome(file.getOriginalFilename());

        return dto;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoConsultaResponseDto>> list(@RequestParam(value = "titulo", required = false) String titulo
            , @RequestParam(value = "status", required = false) StatusProduto status
            , @RequestParam(value = "categoria", required = false) String categoria
            , UriComponentsBuilder uriBuilder
            , Sort sort) {
        return ResponseEntity.ok(produtoService.search(titulo, status, categoria, uriBuilder.toUriString(), sort));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProdutoResponseDto> list(@PathVariable @NotNull Long id, UriComponentsBuilder uriBuilder) {
        ProdutoResponseDto produtoConsultado = produtoService.findById(id, uriBuilder.toUriString());

        return ResponseEntity.ok(produtoConsultado);
    }

    @GetMapping("{id}/midias/{nome}")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable @NotNull Long id, @PathVariable @NotNull String nome) {

        InputStream is = imageStorageService.retrieve(nome, id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/" + nome))
                .header("Content-disposition", "attachment; filename=" + nome)
                .body(new InputStreamResource(is));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable @NotNull Long id) {

        try {
            produtoService.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Produto não encontrado.");
        }

        return ResponseEntity.noContent().build();
    }

}
