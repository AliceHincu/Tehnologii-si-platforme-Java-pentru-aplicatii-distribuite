package repositories;


import java.util.Collection;
import java.util.stream.Collectors;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import dtos.MovieDTO;
import dtos.PurchaseDTO;
import dtos.Utils;
import entities.Movie;
import entities.Purchase;
import interfaces.IRepository;
import interfaces.IRepositoryRemote;

@Stateless
@Local(IRepository.class)
@Remote(IRepositoryRemote.class)
public class Repository implements IRepository, IRepositoryRemote {

    @PersistenceContext(unitName = "tpjad_ejbjpa")
    private EntityManager entityManager;

    @Override
    public Movie saveMovie(final String title, final int releaseYear, final String genre, final String imageUrl) {
        final Movie movie = new Movie();
        movie.setTitle(title);
        movie.setReleaseYear(releaseYear);
        movie.setGenre(genre);
        movie.setImageUrl(imageUrl);
        entityManager.persist(movie);
        return movie;
    }

    public Purchase savePurchase(final Long movieId, final String buyerName, final String purchaseDate) {
        final Purchase purchase = new Purchase();
        purchase.setBuyerName(buyerName);
        purchase.setPurchaseDate(purchaseDate);
        final Movie movie = findMovie(movieId);
        if (movie != null) {
            purchase.setMovie(movie);
            movie.getPurchases().add(purchase);
            entityManager.merge(movie);
        }
        entityManager.persist(purchase);
        return purchase;
    }

    @Override
    public Movie findMovie(final Long id) {
        return entityManager.find(Movie.class, id);
    }

    @Override
    public Purchase findPurchase(final Long id) {
        return entityManager.find(Purchase.class, id);
    }

    @Override
    public Collection<Movie> findAllMovies() {
        return entityManager.createQuery("SELECT g FROM Movie g", Movie.class).getResultList();
    }

    @Override
    public Collection<Purchase> findAllPurchases() {
        return entityManager.createQuery("SELECT p FROM movie_purchase p", Purchase.class).getResultList();
    }

    @Override
    public MovieDTO saveMovieRemote(final String title,
                                    final int releaseYear,
                                    final String genre,
                                    final String imageUrl) {
        return Utils.movieToDTO(saveMovie(title, releaseYear, genre, imageUrl));
    }

    @Override
    public PurchaseDTO savePurchaseRemote(final Long movieId, final String buyerName, final String purchaseDate) {
        return Utils.purchaseToDTO(savePurchase(movieId, buyerName, purchaseDate));
    }

    @Override
    public MovieDTO findMovieRemote(final Long id) {
        return Utils.movieToDTO(findMovie(id));
    }

    @Override
    public PurchaseDTO findPurchaseRemote(final Long id) {
        return Utils.purchaseToDTO(findPurchase(id));
    }

    @Override
    public Collection<MovieDTO> findAllMoviesRemote() {
        return findAllMovies().stream().map(Utils::movieToDTO).collect(Collectors.toList());
    }

    @Override
    public Collection<PurchaseDTO> findAllPurchasesRemote() {
        return findAllPurchases().stream().map(Utils::purchaseToDTO).collect(Collectors.toList());
    }

}