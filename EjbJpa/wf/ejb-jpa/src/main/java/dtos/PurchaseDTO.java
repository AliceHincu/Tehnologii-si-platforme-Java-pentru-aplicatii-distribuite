package dtos;

import java.io.Serializable;
import java.util.Objects;

public class PurchaseDTO implements Serializable {
    private Long purchaseID;

    private String buyerName;

    private String purchaseDate;

    private Long movieID;

    public PurchaseDTO() {
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

    public Long getMovieID() {
        return movieID;
    }

    public void setMovieID(Long movieID) {
        this.movieID = movieID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseDTO that = (PurchaseDTO) o;
        return Objects.equals(purchaseID, that.purchaseID) && Objects.equals(buyerName, that.buyerName)
            && Objects.equals(purchaseDate, that.purchaseDate) && Objects.equals(movieID, that.movieID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseID, buyerName, purchaseDate, movieID);
    }

    @Override
    public String toString() {
        return "PurchaseDTO{" +
            "purchaseID=" + purchaseID +
            ", buyerName='" + buyerName + '\'' +
            ", purchaseDate='" + purchaseDate + '\'' +
            ", movieID=" + movieID +
            '}';
    }
}
