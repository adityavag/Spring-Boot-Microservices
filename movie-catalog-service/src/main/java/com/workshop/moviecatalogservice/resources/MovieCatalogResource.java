package com.workshop.moviecatalogservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.workshop.moviecatalogservice.models.CatalogItem;
import com.workshop.moviecatalogservice.models.Movie;
import com.workshop.moviecatalogservice.models.Rating;

import java.util.*;
import java.util.stream.*;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        RestTemplate restTemplate = new RestTemplate();
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("1234", 4),
                new Rating("3456", 3));
                
                return ratings.stream().map(r -> {
                    Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + r.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), "Test Description", r.getRating());
                }).collect(Collectors.toList());
    }
}
