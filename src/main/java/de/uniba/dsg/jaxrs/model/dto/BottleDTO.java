package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.Resources.BeverageResource;
import de.uniba.dsg.jaxrs.model.logic.Bottle;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bottle")
public class BottleDTO {

    private int id;
    private String name;
    private double volume;
    private boolean isAlcoholic;
    private double volumePercent;
    private double price;
    private String supplier;
    private int inStock;
    private URI href;

    public BottleDTO(){

    }
    public BottleDTO(final Bottle bottle) {
        this.id = bottle.getId();
        this.name = bottle.getName();
        this.volume = bottle.getVolume();
        this.isAlcoholic = bottle.isAlcoholic();
        this.volumePercent = bottle.getVolumePercent();
        this.price = bottle.getPrice();
        this.supplier = bottle.getSupplier();
        this.inStock = bottle.getInStock();
       // this.href = UriBuilder.fromUri(baseUri).path(BeverageResource.class).path(BeverageResource.class, "getBottle").build(this.id);
    }

//    public static List<BottleDTO> marshall(final List<Bottle> botList, final URI baseUri) {
//        final ArrayList<BottleDTO> bots = new ArrayList<>();
//        for (final Bottle bot : botList) {
//            bots.add(new BottleDTO(bot, baseUri));
//        }
//        return bots;
//    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        isAlcoholic = alcoholic;
    }

    public double getVolumePercent() {
        return volumePercent;
    }

    public void setVolumePercent(double volumePercent) {
        this.volumePercent = volumePercent;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }



    @Override
    public String toString() {
        return "Bottle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", volume=" + volume +
                ", isAlcoholic=" + isAlcoholic +
                ", volumePercent=" + volumePercent +
                ", price=" + price +
                ", supplier='" + supplier + '\'' +
                ", inStock=" + inStock +
                '}';
    }
}
