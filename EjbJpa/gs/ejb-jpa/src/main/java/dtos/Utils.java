package dtos;

import java.util.stream.Collectors;
import entities.Movie;
import entities.Purchase;

public class Utils {
    public static MovieDTO movieToDTO(final Movie movie) {
        if (movie == null) {
            return null;
        }
        final MovieDTO movieDTO = new MovieDTO();
        movieDTO.setMovieID(movie.getMovieID());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setReleaseYear(movie.getReleaseYear());
        movieDTO.setGenre(movie.getGenre());
        movieDTO.setImageUrl(movie.getImageUrl());
        movieDTO.getPurchaseDTOs().addAll(movie.getPurchases().stream().map(Utils::purchaseToDTO).collect(Collectors.toList()));
        return movieDTO;
    }

    public static PurchaseDTO purchaseToDTO(final Purchase purchase) {
        if (purchase == null) {
            return null;
        }
        final PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setPurchaseID(purchase.getPurchaseID());
        purchaseDTO.setBuyerName(purchase.getBuyerName());
        purchaseDTO.setPurchaseDate(purchase.getPurchaseDate());
        purchaseDTO.setMovieID(purchase.getMovie().getMovieID());
        return purchaseDTO;
    }
}
