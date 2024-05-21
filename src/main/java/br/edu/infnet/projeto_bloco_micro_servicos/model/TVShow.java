package br.edu.infnet.projeto_bloco_micro_servicos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TVShow {
    private int id;
    private String title;
    private String genre;
    private int season;
    private int episode;

    // Constructors
    public TVShow(int id, String title, String genre, int season, int episode) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.season = season;
        this.episode = episode;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    // toString method
    @Override
    public String toString() {
        return "TVShow{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", season=" + season +
                ", episode=" + episode +
                '}';
    }
}
