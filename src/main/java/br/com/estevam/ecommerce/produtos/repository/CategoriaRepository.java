package br.com.estevam.ecommerce.produtos.repository;

import br.com.estevam.ecommerce.produtos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
