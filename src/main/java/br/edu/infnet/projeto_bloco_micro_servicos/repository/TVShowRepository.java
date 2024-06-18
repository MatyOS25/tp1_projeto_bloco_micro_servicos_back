package br.edu.infnet.projeto_bloco_micro_servicos.repository;

import br.edu.infnet.projeto_bloco_micro_servicos.model.TVShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TVShowRepository extends JpaRepository<TVShow, Integer> {

    @Query("SELECT t FROM TVShow t WHERE LOWER(t.title) LIKE %:name%")
    List<TVShow> findByTitleContainingIgnoreCase(String name);

}
