package br.com.estevam.ecommerce.produtos.controller;

import br.com.estevam.ecommerce.produtos.dto.CategoriaAtualizacaoRequestDto;
import br.com.estevam.ecommerce.produtos.dto.CategoriaRequestDto;
import br.com.estevam.ecommerce.produtos.dto.CategoriaResponseDto;
import br.com.estevam.ecommerce.produtos.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaResponseDto> list() {
        return categoriaService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoriaResponseDto> list(@PathVariable @NotNull Long id) {
        CategoriaResponseDto categoriaConsultada = categoriaService.findById(id);

        return ResponseEntity.ok(categoriaConsultada);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDto> create(@RequestBody @Valid CategoriaRequestDto dto, UriComponentsBuilder uriBuilder) {

        CategoriaResponseDto categoriaCriada = categoriaService.save(dto);

        URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoriaCriada.getId()).toUri();

        return ResponseEntity.created(uri).body(categoriaCriada);
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoriaResponseDto> update(@PathVariable @NotNull Long id, @RequestBody @Valid CategoriaAtualizacaoRequestDto dto) {

        CategoriaResponseDto categoriaCriada = categoriaService.update(id, dto);

        return ResponseEntity.ok(categoriaCriada);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable @NotNull Long id) {

        try {
            categoriaService.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Categoria não encontrado.");
        }

        return ResponseEntity.noContent().build();
    }

}
