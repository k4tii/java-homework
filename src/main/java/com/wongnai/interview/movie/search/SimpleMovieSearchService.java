package com.wongnai.interview.movie.search;

import java.lang.reflect.Array;
import java.util.List;
import java.util.stream.Collectors;

import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieDataService;

@Component("simpleMovieSearchService")
public class SimpleMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieDataService movieDataService;

	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 2 => Implement this method by using data from MovieDataService
		// All test in SimpleMovieSearchServiceIntegrationTest must pass.
		// Please do not change @Component annotation on this class
		MoviesResponse moviesResponse = movieDataService.fetchAll();
		List<Movie> movieList = moviesResponse.stream()
				.filter(movieData -> Arrays.asList(movieData.getTitle().toLowerCase().split("\\s+")).contains(queryText.toLowerCase()))
				.collect(Collectors.mapping(movieData -> mapMovieDataToMovie(movieData),Collectors.toList()));
		return movieList;
	}

	private Movie mapMovieDataToMovie(MovieData movieData){
		Movie movie = new Movie(movieData.getTitle());
		movie.getActors().addAll(movieData.getCast());
		return movie;
	}
}
