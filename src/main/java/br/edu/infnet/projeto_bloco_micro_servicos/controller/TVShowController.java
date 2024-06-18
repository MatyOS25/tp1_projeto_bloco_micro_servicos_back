package br.edu.infnet.projeto_bloco_micro_servicos.controller;

import br.edu.infnet.projeto_bloco_micro_servicos.exception.ResourceNotFoundException;
import br.edu.infnet.projeto_bloco_micro_servicos.model.TVShow;
import br.edu.infnet.projeto_bloco_micro_servicos.payload.MessagePayload;
import br.edu.infnet.projeto_bloco_micro_servicos.service.TVShowService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/tvshows")
public class TVShowController {

    private final TVShowService tvShowService;

    @Autowired
    public TVShowController(TVShowService tvShowService) {
        this.tvShowService = tvShowService;
    }

    @GetMapping
    public ResponseEntity<List<TVShow>> getAll(@RequestParam(required = false) Optional<String> name) {
        if (name.isEmpty()) {
            return ResponseEntity.ok(tvShowService.getAllTVShows());
        }
        else{
            List<TVShow> tvShows = tvShowService.filterTVShowByName(name.get());
            if(tvShows.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(tvShows);
        } 
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try{
            return ResponseEntity.ok(tvShowService.getTVShowById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<TVShow> addTVShow(@RequestBody TVShow tvShow) {
        tvShowService.addTVShow(tvShow);
        return ResponseEntity.status(HttpStatus.CREATED).body(tvShow);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTVShow(@PathVariable int id, @RequestBody TVShow tvShow) {
        try{
            return ResponseEntity.ok(tvShowService.updateTVShow(id, tvShow));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTVShow(@PathVariable int id) {
        try{
            tvShowService.deleteTVShow(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }


    
}