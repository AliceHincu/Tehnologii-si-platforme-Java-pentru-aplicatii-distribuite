package interfaces;

import java.util.Collection;
import dtos.MovieDTO;
import dtos.PurchaseDTO;

public interface IRepositoryRemote {
    MovieDTO saveMovieRemote(final String title, final int releaseYear, final String genre, final String imageUrl);

    PurchaseDTO savePurchaseRemote(final Long movieId, final String buyerName, final String purchaseDate);

    MovieDTO findMovieRemote(final Long id);

    PurchaseDTO findPurchaseRemote(final Long id);

    Collection<MovieDTO> findAllMoviesRemote();

    Collection<PurchaseDTO> findAllPurchasesRemote();
}
