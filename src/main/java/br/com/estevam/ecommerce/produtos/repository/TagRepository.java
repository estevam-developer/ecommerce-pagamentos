package br.com.estevam.ecommerce.produtos.repository;

import br.com.estevam.ecommerce.produtos.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    public Optional<Tag> findByTag(String tag);

}
