package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQMYCLASSID")
    private Long movieID = 1L;

    private String title;

    private int releaseYear;

    private String genre;

    private String imageUrl;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Purchase> purchases = new ArrayList<>();

    public Movie() {

    }

    public Long getMovieID() {
        return movieID;
    }

    public void setMovieID(Long movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Collection<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(Collection<Purchase> purchases) {
        this.purchases = purchases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return releaseYear == movie.releaseYear && Objects.equals(movieID, movie.movieID)
            && Objects.equals(title, movie.title) && Objects.equals(genre, movie.genre)
            && Objects.equals(imageUrl, movie.imageUrl) && Objects.equals(purchases, movie.purchases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieID, title, releaseYear, genre, imageUrl, purchases);
    }

    @Override
    public String toString() {
        return "Movie{" +
            "movieID=" + movieID +
            ", title='" + title + '\'' +
            ", releaseYear=" + releaseYear +
            ", genre='" + genre + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", purchases=" + purchases +
            '}';
    }
}
