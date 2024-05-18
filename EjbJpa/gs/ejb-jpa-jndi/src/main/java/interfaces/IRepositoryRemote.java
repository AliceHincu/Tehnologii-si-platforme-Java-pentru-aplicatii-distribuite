package interfaces;

import dtos.MovieDTO;
import dtos.PurchaseDTO;
import java.util.Collection;

public interface IRepositoryRemote {
    MovieDTO saveMovieRemote(final String title, final int releaseYear, final String genre, final String imageUrl);

    PurchaseDTO savePurchaseRemote(final Long movieId, final String buyerName, final String purchaseDate);

    MovieDTO findMovieRemote(final Long id);

    PurchaseDTO findPurchaseRemote(final Long id);

    Collection<MovieDTO> findAllMoviesRemote();

    Collection<PurchaseDTO> findAllPurchasesRemote();
}
