package com.example.exercise1;

public class Game {
    private String title;
    private String platform;
    private String genre;
    private String year;

    public Game() {}

    public Game(String title, String platform, String genre, String year) {
        this.title= title;
        this.platform = platform;
        this.genre = genre;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getPlatform() {
        return platform;
    }

    public String getGenre () {
        return genre;
    }

    public String getYear() {
        return year;
    }

}
