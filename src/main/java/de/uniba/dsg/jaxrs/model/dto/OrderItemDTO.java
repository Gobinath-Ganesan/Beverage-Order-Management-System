package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.logic.Beverage;
import de.uniba.dsg.jaxrs.model.logic.OrderItem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderItem")
public class OrderItemDTO {

    private int number;
    private Beverage beverage;
    private int quantity;

    public OrderItemDTO(){

    }
    public OrderItemDTO(final OrderItem orderItem) {
        this.number = orderItem.getNumber();
        this.beverage = orderItem.getBeverage();
        this.quantity = orderItem.getQuantity();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "number=" + number +
                ", beverage=" + beverage +
                ", quantity=" + quantity +
                '}';
    }
}
