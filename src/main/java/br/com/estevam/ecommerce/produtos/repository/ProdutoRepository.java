package br.com.estevam.ecommerce.produtos.repository;

import br.com.estevam.ecommerce.produtos.model.Produto;
import br.com.estevam.ecommerce.produtos.model.StatusProduto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

}
