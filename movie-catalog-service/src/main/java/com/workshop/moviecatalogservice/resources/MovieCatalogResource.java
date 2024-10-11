package com.workshop.moviecatalogservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.workshop.moviecatalogservice.models.CatalogItem;
import com.workshop.moviecatalogservice.models.Movie;
import com.workshop.moviecatalogservice.models.Rating;
import com.workshop.moviecatalogservice.models.RatingResponse;

import java.util.*;
import java.util.stream.*;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        // List<Rating> ratings = Arrays.asList(
        //         new Rating("1234", 4),
        //         new Rating("1234", 4),
        //         new Rating("3456", 3));

        RatingResponse response = new RatingResponse();
        response = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, RatingResponse.class);
                
                return response.getRatings().stream().map(r -> {
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + r.getMovieId(), Movie.class);

                    // 
                    // Movie movie = webClientBuilder.build()
                    // .get()
                    // .uri("http://localhost:8082/movies/"+ r.getMovieId())
                    // .retrieve()
                    // .bodyToMono(Movie.class)
                    // .block();

                    return new CatalogItem(movie.getName(), "Test Description", r.getRating());
                }).collect(Collectors.toList());
    }
}
