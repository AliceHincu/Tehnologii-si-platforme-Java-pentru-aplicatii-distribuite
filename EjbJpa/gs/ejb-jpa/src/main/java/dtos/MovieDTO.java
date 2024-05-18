package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class MovieDTO implements Serializable {
    private Long movieID;

    private String title;

    private int releaseYear;

    private String genre;

    private String imageUrl;
    private Collection<PurchaseDTO> purchaseDTOs = new ArrayList<>();


    public MovieDTO() {
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

    public Collection<PurchaseDTO> getPurchaseDTOs() {
        return purchaseDTOs;
    }

    public void setPurchaseDTOs(Collection<PurchaseDTO> purchaseDTOs) {
        this.purchaseDTOs = purchaseDTOs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MovieDTO movieDTO = (MovieDTO) o;
        return releaseYear == movieDTO.releaseYear && Objects.equals(movieID, movieDTO.movieID)
            && Objects.equals(title, movieDTO.title) && Objects.equals(genre, movieDTO.genre)
            && Objects.equals(imageUrl, movieDTO.imageUrl) && Objects.equals(purchaseDTOs,
                                                                             movieDTO.purchaseDTOs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieID, title, releaseYear, genre, imageUrl, purchaseDTOs);
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
            "movieID=" + movieID +
            ", title='" + title + '\'' +
            ", releaseYear=" + releaseYear +
            ", genre='" + genre + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", purchases=" + purchaseDTOs +
            '}';
    }
}
