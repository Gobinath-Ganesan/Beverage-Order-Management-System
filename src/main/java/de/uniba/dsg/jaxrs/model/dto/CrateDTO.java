package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.Resources.BeverageResource;
import de.uniba.dsg.jaxrs.model.logic.Bottle;
import de.uniba.dsg.jaxrs.model.logic.Crate;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "crate")
public class CrateDTO {

    private int id;
    private Bottle bottle;
    private int noOfBottles;
    private double price;
    private int inStock;
    //private URI href;

    public CrateDTO(){

    }
    public CrateDTO(final Crate crate) {
        this.id = crate.getId();
        this.bottle = crate.getBottle();
        this.noOfBottles = crate.getNoOfBottles();
        this.price = crate.getPrice();
        this.inStock = crate.getInStock();
      //  this.href = UriBuilder.fromUri(baseUri).path(BeverageResource.class).path(BeverageResource.class, "getCrate").build(this.id);
    }

//    public static List<CrateDTO> marshall(final List<Crate> crateList, final URI baseUri) {
//        final ArrayList<CrateDTO> crats = new ArrayList<>();
//        for (final Crate cra : crateList) {
//            crats.add(new CrateDTO(cra, baseUri));
//        }
//        return crats;
//    }

    public int getId() {
        return id;
    }

    public Bottle getBottle() {
        return bottle;
    }

    public void setBottle(Bottle bottle) {
        this.bottle = bottle;
    }

    public int getNoOfBottles() {
        return noOfBottles;
    }

    public void setNoOfBottles(int noOfBottles) {
        this.noOfBottles = noOfBottles;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    @Override
    public String toString() {
        return "Crate{" +
                "id=" + id +
                ", bottle=" + bottle +
                ", noOfBottles=" + noOfBottles +
                ", price=" + price +
                ", inStock=" + inStock +
                '}';
    }
}
