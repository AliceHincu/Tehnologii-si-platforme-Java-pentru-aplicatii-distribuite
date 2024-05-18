package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "movie_purchase")
public class Purchase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQMYCLASSID")
    private Long purchaseID = -1L;

    private String buyerName;

    private String purchaseDate;

    @ManyToOne
    @JoinColumn(name = "movieID")
    private Movie movie;

    public Purchase() {
    }

    public Long getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(Long purchaseID) {
        this.purchaseID = purchaseID;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Purchase purchase = (Purchase) o;
        return Objects.equals(purchaseID, purchase.purchaseID) && Objects.equals(buyerName,
                                                                                 purchase.buyerName)
            && Objects.equals(purchaseDate, purchase.purchaseDate) && Objects.equals(movie,
                                                                                     purchase.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseID, buyerName, purchaseDate, movie);
    }

    @Override
    public String toString() {
        return "Purchase{" +
            "purchaseID=" + purchaseID +
            ", buyerName='" + buyerName + '\'' +
            ", purchaseDate='" + purchaseDate + '\'' +
            ", movie=" + movie +
            '}';
    }
}
