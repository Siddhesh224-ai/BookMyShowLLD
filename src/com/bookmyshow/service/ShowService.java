package com.bookmyshow.service;

import com.bookmyshow.enums.SeatStatus;
import com.bookmyshow.exception.ShowNotFoundException;
import com.bookmyshow.model.City;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.Screen;
import com.bookmyshow.model.Seat;
import com.bookmyshow.model.Show;
import com.bookmyshow.model.Theater;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShowService {
    private List<Show> allShows = new ArrayList<>();
    private List<Movie> allMovies = new ArrayList<>();
    private List<Theater> allTheaters = new ArrayList<>();

    public void addMovie(Movie movie) {
        allMovies.add(movie);
        System.out.println("Movie added: " + movie.getTitle() + ".");
    }

    public void addTheater(Theater theater) {
        allTheaters.add(theater);
        System.out.println("Theater added: " + theater.getName() + ".");
    }

    public Show scheduleShow(Movie movie, Screen screen, LocalDateTime startTime) {
        String id = UUID.randomUUID().toString();
        Show show = new Show(id, movie, screen, startTime);
        allShows.add(show);
        System.out.println("Show scheduled: " + movie.getTitle() + " at " + startTime + ".");
        return show;
    }

    public List<Show> getShowsForMovieInCity(Movie movie, City city) {
        List<Show> result = new ArrayList<>();
        for (Show show : allShows) {
            boolean sameMovie = show.getMovie().getId().equals(movie.getId());
            boolean sameCity = show.getScreen().getTheater().getCity().getId().equals(city.getId());
            if (sameMovie && sameCity) {
                result.add(show);
            }
        }
        return result;
    }

    public List<Seat> getAvailableSeatsForShow(Show show) {
        List<Seat> result = new ArrayList<>();
        for (Seat seat : show.getScreen().getSeats()) {
            if (show.getSeatStatus(seat.getId()) == SeatStatus.AVAILABLE) {
                result.add(seat);
            }
        }
        return result;
    }

    public Show findShowById(String id) {
        for (Show show : allShows) {
            if (show.getId().equals(id)) {
                return show;
            }
        }
        throw new ShowNotFoundException("Show not found with id: " + id);
    }
}
