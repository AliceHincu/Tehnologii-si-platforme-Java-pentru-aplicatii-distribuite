package interfaces;

import java.util.Collection;
import entities.Movie;
import entities.Purchase;

public interface IRepository {
    Movie saveMovie(final String title, final int releaseYear, final String genre, final String imageUrl);

    Purchase savePurchase(final Long movieId, final String buyerName, final String purchaseDate);

    Movie findMovie(final Long id);

    Purchase findPurchase(final Long id);

    Collection<Movie> findAllMovies();

    Collection<Purchase> findAllPurchases();
    }
