package br.edu.infnet.projeto_bloco_micro_servicos.service;

import br.edu.infnet.projeto_bloco_micro_servicos.exception.ResourceNotFoundException;
import br.edu.infnet.projeto_bloco_micro_servicos.model.TVShow;
import br.edu.infnet.projeto_bloco_micro_servicos.model.TVShowHistory;
import br.edu.infnet.projeto_bloco_micro_servicos.repository.TVShowHistoryRepository;
import br.edu.infnet.projeto_bloco_micro_servicos.repository.TVShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TVShowService {

    private final TVShowRepository tvShowRepository;
    private final TVShowHistoryRepository tvShowHistoryRepository;

    @Autowired
    public TVShowService(TVShowRepository tvShowRepository, TVShowHistoryRepository tvShowHistoryRepository) {
        this.tvShowRepository = tvShowRepository;
        this.tvShowHistoryRepository = tvShowHistoryRepository;
    }

    public List<TVShow> getAllTVShows() {
        return tvShowRepository.findAll();
    }

    public TVShow getTVShowById(int id) {
        return tvShowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TV Show not found with id " + id));
    }

    public List<TVShow> filterTVShowByName(String name) {
        return tvShowRepository.findByTitleContainingIgnoreCase(name);
    }

    public TVShow addTVShow(TVShow tvShow) {
        TVShow savedTVShow = tvShowRepository.save(tvShow);
        logTVShowHistory(savedTVShow.getId(), "TV Show added: " + savedTVShow.getTitle());
        return savedTVShow;
    }

    public TVShow updateTVShow(int id, TVShow tvShow) {
        TVShow existingTVShow = tvShowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TV Show not found with id " + id));

        existingTVShow.setTitle(tvShow.getTitle());
        existingTVShow.setGenre(tvShow.getGenre());
        existingTVShow.setSeason(tvShow.getSeason());
        existingTVShow.setEpisode(tvShow.getEpisode());

        TVShow updatedTVShow = tvShowRepository.save(existingTVShow);
        logTVShowHistory(updatedTVShow.getId(), "TV Show updated: " + updatedTVShow.getTitle());
        return updatedTVShow;
    }

    public void deleteTVShow(int id) {
        TVShow tvShowToDelete = tvShowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TV Show not found with id " + id));

        tvShowRepository.delete(tvShowToDelete);
        logTVShowHistory(id, "TV Show deleted: " + tvShowToDelete.getTitle());
    }

    public List<TVShowHistory> getTVShowHistory(int tvShowId) {
        return tvShowHistoryRepository.findByTvShowIdOrderByModifiedDateDesc(tvShowId);
    }

    private void logTVShowHistory(int tvShowId, String action) {
        TVShowHistory history = new TVShowHistory();
        history.setTvShowId(tvShowId);
        history.setAction(action);
        history.setModifiedDate(LocalDateTime.now());
        tvShowHistoryRepository.save(history);
    }
}
