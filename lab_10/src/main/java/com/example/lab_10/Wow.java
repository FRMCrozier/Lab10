package com.example.lab_10;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "movie",
        "year",
        "release_date",
        "director",
        "character",
        "movie_duration",
        "timestamp",
        "full_line",
        "current_wow_in_movie",
        "total_wows_in_movie",
        "poster",
        "video",
        "audio"
})

public class Wow {
    @JsonProperty("movie")
    private String movie;
    @JsonProperty("year")
    private Integer year;
    @JsonProperty("release_date")
    private LocalDate releaseDate;
    @JsonProperty("director")
    private String director;
    @JsonProperty("character")
    private String movieCharacter;
    @JsonProperty("movie_duration")
    private LocalTime movieDuration;
    @JsonProperty("timestamp")
    private LocalTime timestamp;
    @JsonProperty("full_line")
    private String fullLine;
    @JsonProperty("current_wow_in_movie")
    private Integer currentWowInMovie;
    @JsonProperty("total_wows_in_movie")
    private Integer totalWowsInMovie;
    @JsonProperty("poster")
    private String poster;
    @JsonProperty("video")
    private String video;
    @JsonProperty("audio")
    private String audio;

    private int WowID;

    public int getWowID() {
        return WowID;
    }

    public void setWowID(int wowID) {
        WowID = wowID;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }


    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }


    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {

        this.releaseDate = LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }


    public String getCharacter() {
        return movieCharacter;
    }

    public void setCharacter(String character) {
        this.movieCharacter = character;
    }

    public LocalTime getMovieDuration() {
        return movieDuration;
    }


    public void setMovieDuration(String movieDuration) {

        this.movieDuration = LocalTime.parse(movieDuration, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }


    public LocalTime getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(String timestamp) {

        this.timestamp = LocalTime.parse(timestamp, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }


    public String getFullLine() {
        return fullLine;
    }


    public void setFullLine(String fullLine) {
        this.fullLine = fullLine;
    }


    public Integer getCurrentWowInMovie() {
        return currentWowInMovie;
    }


    public void setCurrentWowInMovie(Integer currentWowInMovie) {
        this.currentWowInMovie = currentWowInMovie;
    }


    public Integer getTotalWowsInMovie() {
        return totalWowsInMovie;
    }

    public void setTotalWowsInMovie(Integer totalWowsInMovie) {
        this.totalWowsInMovie = totalWowsInMovie;
    }


    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }


    public String getVideo() {
        return video;
    }


    public void setVideo(Video video) {
        List<String> list = Stream.of(video).map(Video::toString).collect(Collectors.toList());
        String [] videoArray = list.toArray(new String[0]);
        this.video = Arrays.toString(videoArray);
    }


    public String getAudio() {
        return audio;
    }


    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Wow (){

    }

    public Wow(int id, String movie, int year, LocalDate releaseDate, String director, String character, LocalTime duration,
               LocalTime timestamp, String fullLine, int current, int total, String poster, String video, String audio) {
        WowID = id;
        this.movie = movie;
        this.year = year;
        this.releaseDate = releaseDate;
        this.director = director;
        this.movieCharacter = character;
        this.movieDuration = duration;
        this.timestamp = timestamp;
        this.fullLine = fullLine;
        this.currentWowInMovie = current;
        this.totalWowsInMovie = total;
        this.poster = poster;
        this.video = video;
        this.audio = audio;
    }

    /**
     * Сравнения, по названию фильма и по дате.
     */
    public static Comparator<Wow> byNameAsc = Comparator.comparing(o -> o.movie);
    public static Comparator<Wow> byNameDesc = (o1, o2) -> o2.movie.compareTo(o1.movie);
    public static Comparator<Wow> byYearAsc = (o1, o2) -> o1.year > o2.year ? 1 : o1.year < o2.year ? -1 : 0;

    public static Comparator<Wow> byNumberWowAsc = (o1, o2) -> o1.totalWowsInMovie > o2.totalWowsInMovie ? 1 : o1.totalWowsInMovie < o2.totalWowsInMovie ? -1 : 0;
    public static Comparator<Wow> byYearDesc = (o1, o2) -> o1.year < o2.year ? 1 : o1.year > o2.year ? -1 : 0;
}
