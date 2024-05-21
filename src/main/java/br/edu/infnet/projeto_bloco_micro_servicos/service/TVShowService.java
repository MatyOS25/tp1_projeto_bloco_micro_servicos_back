package br.edu.infnet.projeto_bloco_micro_servicos.service;

import br.edu.infnet.projeto_bloco_micro_servicos.model.TVShow;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import br.edu.infnet.projeto_bloco_micro_servicos.exception.ResourceNotFoundException;


import java.util.ArrayList;
import java.util.List;

@Service
public class TVShowService {
    private List<TVShow> tvShows = initValues();

    private List<TVShow> initValues() {
        List<TVShow> tvShows = new ArrayList<>();
        tvShows.add(new TVShow(1, "Breaking Bad", "Drama", 5, 62));
        tvShows.add(new TVShow(2, "Game of Thrones", "Fantasy", 8, 73));
        tvShows.add(new TVShow(3, "The Office", "Comedy", 9, 201));

        return tvShows;
    }


    public List<TVShow> getTVShows() {
        return this.tvShows;
    }

    public void addTVShow(TVShow tvShow) {
        tvShows.add(tvShow);
    }

    //getByid
    public TVShow getTVShowById(int id) {
        if (id <= 0) {
            throw new ResourceNotFoundException("Invalid ID");
        }
        else {
            Optional<TVShow> tvShow = tvShows.stream().filter(tv -> tv.getId() == id).findFirst();
            if(tvShow.isEmpty()) {
                throw new ResourceNotFoundException("TV Show not found");
            }
            return tvShow.get();
        }
    }

    //filterByName
    public List<TVShow> filterTVShowByName(String name) {
        if (name == null) {
            throw new ResourceNotFoundException("Invalid name");
        }
        else {
            return tvShows.stream().filter(tv -> tv.getTitle().contains(name)).collect(Collectors.toList());
        }
    }


    //update
    public TVShow updateTVShow(int id, TVShow tvShow) {
        if (id <= 0) {
            throw new ResourceNotFoundException("Invalid ID");
        }
        else {
            Optional<TVShow> tvShowToUpdate = tvShows.stream().filter(tv -> tv.getId() == id).findFirst();
            if(tvShowToUpdate.isEmpty()) {
                throw new ResourceNotFoundException("TV Show not found");
            }
            tvShowToUpdate.get().setTitle(tvShow.getTitle());
            tvShowToUpdate.get().setGenre(tvShow.getGenre());
            tvShowToUpdate.get().setSeason(tvShow.getSeason());
            tvShowToUpdate.get().setEpisode(tvShow.getEpisode());
            return tvShowToUpdate.get();
        }
    }

    //delete
    public TVShow deleteTVShow(int id) {
        if (id <= 0) {
            throw new ResourceNotFoundException("Invalid ID");
        }
        else {
            Optional<TVShow> tvShowToDelete = tvShows.stream().filter(tv -> tv.getId() == id).findFirst();
            if(tvShowToDelete.isEmpty()) {
                throw new ResourceNotFoundException("TV Show not found");
            }
            tvShows.remove(tvShowToDelete.get());
            return tvShowToDelete.get();
        }
    }

}