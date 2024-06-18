package br.edu.infnet.projeto_bloco_micro_servicos.repository;

import br.edu.infnet.projeto_bloco_micro_servicos.model.TVShowHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TVShowHistoryRepository extends JpaRepository<TVShowHistory, Long> {
    // Define custom query methods if needed
}

