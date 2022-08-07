package br.com.estevam.ecommerce.produtos.service;

import br.com.estevam.ecommerce.produtos.dto.CategoriaAtualizacaoRequestDto;
import br.com.estevam.ecommerce.produtos.dto.CategoriaRequestDto;
import br.com.estevam.ecommerce.produtos.dto.CategoriaResponseDto;
import br.com.estevam.ecommerce.produtos.model.Categoria;
import br.com.estevam.ecommerce.produtos.repository.CategoriaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ModelMapper mapper;

    public CategoriaResponseDto save(CategoriaRequestDto dto) {

        Categoria categoria = mapper.map(dto, Categoria.class);

        categoria = categoriaRepository.save(categoria);

        return mapper.map(categoria, CategoriaResponseDto.class);

    }

    public List<CategoriaResponseDto> findAll() {
        return categoriaRepository.findAll().stream().map(c -> mapper.map(c, CategoriaResponseDto.class)).collect(Collectors.toList());
    }

    public CategoriaResponseDto findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada."));

        return mapper.map(categoria, CategoriaResponseDto.class);
    }

    public CategoriaResponseDto update(Long id, CategoriaAtualizacaoRequestDto dto) {

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada."));

        categoria.setNome(dto.getNome());

        categoriaRepository.save(categoria);

        return mapper.map(categoria, CategoriaResponseDto.class);
    }

    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    }

}
