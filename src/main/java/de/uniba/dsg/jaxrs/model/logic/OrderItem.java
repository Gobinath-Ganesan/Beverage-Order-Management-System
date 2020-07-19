package de.uniba.dsg.jaxrs.model.logic;

import de.uniba.dsg.jaxrs.model.dto.OrderDTO;
import de.uniba.dsg.jaxrs.model.dto.OrderItemDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

public class OrderItem {
    private int number;
    private Beverage beverage;
    private int quantity;

    public OrderItem(){

    }
    public OrderItem(int number, Beverage beverage, int quantity) {
        this.number = number;
        this.beverage = beverage;
        this.quantity = quantity;
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

    public static List<OrderItem> convertDTOOrderItemToOrderItem(List<OrderItemDTO> argOrderItem){

        List<OrderItem> orderItemsToReturn = new ArrayList<>();
        for (OrderItemDTO ordList:argOrderItem
             ) {
            orderItemsToReturn.add(new OrderItem(ordList.getNumber(),ordList.getBeverage(),ordList.getQuantity()));
        }

        return orderItemsToReturn;
        //OrderItem(argOrderItem.getNumber(),argOrderItem.getBeverage(),argOrderItem.getQuantity());

    }
}
