package br.com.estevam.ecommerce.produtos.repository;

import br.com.estevam.ecommerce.produtos.model.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

}
