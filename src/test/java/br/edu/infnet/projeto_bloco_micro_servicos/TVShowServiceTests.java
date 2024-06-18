package br.edu.infnet.projeto_bloco_micro_servicos.service;

import br.edu.infnet.projeto_bloco_micro_servicos.exception.ResourceNotFoundException;
import br.edu.infnet.projeto_bloco_micro_servicos.model.TVShow;
import br.edu.infnet.projeto_bloco_micro_servicos.model.TVShowHistory;
import br.edu.infnet.projeto_bloco_micro_servicos.repository.TVShowHistoryRepository;
import br.edu.infnet.projeto_bloco_micro_servicos.repository.TVShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TVShowServiceTest {

    @Mock
    private TVShowRepository tvShowRepository;

    @Mock
    private TVShowHistoryRepository tvShowHistoryRepository;

    @InjectMocks
    private TVShowService tvShowService;

    private List<TVShow> tvShowList;

    @BeforeEach
    void setUp() {
        tvShowList = new ArrayList<>();
        tvShowList.add(new TVShow(1, "Breaking Bad", "Drama", 5, 62));
        tvShowList.add(new TVShow(2, "Game of Thrones", "Fantasy", 8, 73));
        tvShowList.add(new TVShow(3, "The Office", "Comedy", 9, 201));
    }

    @Test
    void testGetAllTVShows() {
        when(tvShowRepository.findAll()).thenReturn(tvShowList);

        List<TVShow> result = tvShowService.getAllTVShows();

        assertEquals(3, result.size());
    }

    @Test
    void testGetTVShowById() {
        int id = 1;
        TVShow tvShow = new TVShow(id, "Breaking Bad", "Drama", 5, 62);
        when(tvShowRepository.findById(id)).thenReturn(Optional.of(tvShow));

        TVShow result = tvShowService.getTVShowById(id);

        assertEquals("Breaking Bad", result.getTitle());
    }

    @Test
    void testGetTVShowByIdNotFound() {
        int id = 99;
        when(tvShowRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tvShowService.getTVShowById(id));
    }

    @Test
    void testFilterTVShowByName() {
        String name = "Game";
        when(tvShowRepository.findByTitleContainingIgnoreCase(anyString())).thenReturn(List.of(tvShowList.get(1)));

        List<TVShow> result = tvShowService.filterTVShowByName(name);

        assertEquals(1, result.size());
        assertEquals("Game of Thrones", result.get(0).getTitle());
    }

    @Test
    void testAddTVShow() {
        TVShow newTVShow = new TVShow(4, "Friends", "Comedy", 10, 236);
        when(tvShowRepository.save(any(TVShow.class))).thenReturn(newTVShow);

        TVShow result = tvShowService.addTVShow(newTVShow);

        assertNotNull(result);
        assertEquals("Friends", result.getTitle());
    }

    @Test
    void testUpdateTVShow() {
        int id = 2;
        TVShow updatedTVShow = new TVShow(id, "New Title", "New Genre", 9, 80);
        when(tvShowRepository.findById(id)).thenReturn(Optional.of(tvShowList.get(1)));
        when(tvShowRepository.save(any(TVShow.class))).thenReturn(updatedTVShow);

        TVShow result = tvShowService.updateTVShow(id, updatedTVShow);

        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
        assertEquals("New Genre", result.getGenre());
    }

    @Test
    void testUpdateTVShowNotFound() {
        int id = 99;
        TVShow updatedTVShow = new TVShow(id, "New Title", "New Genre", 9, 80);
        when(tvShowRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tvShowService.updateTVShow(id, updatedTVShow));
    }

    @Test
    void testDeleteTVShow() {
        int id = 3;
        TVShow tvShowToDelete = tvShowList.get(2);
        when(tvShowRepository.findById(id)).thenReturn(Optional.of(tvShowToDelete));

        tvShowService.deleteTVShow(id);

        verify(tvShowRepository, times(1)).delete(tvShowToDelete);
    }

    @Test
    void testDeleteTVShowNotFound() {
        int id = 99;
        when(tvShowRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tvShowService.deleteTVShow(id));
    }

    @Test
    void testGetTVShowHistory() {
        int tvShowId = 1;
        List<TVShowHistory> historyList = new ArrayList<>();
        historyList.add(new TVShowHistory(1L, tvShowId, "TV Show updated: Breaking Bad", LocalDateTime.now()));
        when(tvShowHistoryRepository.findByTvShowIdOrderByModifiedDateDesc(tvShowId)).thenReturn(historyList);

        List<TVShowHistory> result = tvShowService.getTVShowHistory(tvShowId);

        assertEquals(1, result.size());
        assertEquals("TV Show updated: Breaking Bad", result.get(0).getAction());
    }

    @Test
    void testLogTVShowHistory() {
        int tvShowId = 1;
        String action = "Test action";
        TVShowHistory history = new TVShowHistory(1L, tvShowId, action, LocalDateTime.now());
        when(tvShowHistoryRepository.save(any(TVShowHistory.class))).thenReturn(history);

        tvShowService.logTVShowHistory(tvShowId, action);

        verify(tvShowHistoryRepository, times(1)).save(history);
    }
}
