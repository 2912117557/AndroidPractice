package com.example.myapplication4.film.detail;

import java.util.List;

public class FilmDetailDataBean_Network {
    public boolean is_tv;
    public String intro;
    public String year;
    public String id;
    public String original_title;
    public String title;
    public List<String> languages;
    public List<String> genres;
    public List<String> countries;
    public FHLScore rating;
    public FHLFilmPicture pic;

    public List<FilmDirectorInfo> directors;
    public List<FilmActorInfo> actors;

    public static class FHLScore {
        public int count;
        public float value;
    }

    public static class FHLFilmPicture{
        public String normal;
    }


    public static class FilmActorInfo {
        public String name;
        public List<String> roles;
        public String url;
        public String cover_url;
    }

    public static class FilmDirectorInfo {
        public String name;
        public List<String> roles;
        public String url;
        public String cover_url;
    }
}
