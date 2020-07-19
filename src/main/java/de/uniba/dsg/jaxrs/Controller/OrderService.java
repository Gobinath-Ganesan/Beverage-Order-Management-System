package de.uniba.dsg.jaxrs.Controller;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.logic.Bottle;
import de.uniba.dsg.jaxrs.model.logic.Order;
import de.uniba.dsg.jaxrs.model.logic.OrderStatus;

import java.util.*;

public class OrderService {
    DB fromDB = new DB();

    public Order getByID(int id){

        System.out.println("Inside get order by id methos");
        boolean toContinue = true;
        Order order = null;
        // Getting an iterator
        Iterator orderIterator = DB.orderInfo.entrySet().iterator();

        // Iterate through the hashmap
        while (orderIterator.hasNext() && toContinue ) {
            Map.Entry mapElement = (Map.Entry)orderIterator.next();
            if(mapElement.getKey().equals(id)){
                toContinue = false;
                System.out.println(mapElement.getKey());
                return (Order) mapElement.getValue();
                //return order;
            }
        }
        return order;
    }

    public List<Order> getAllOrders(){
        List<Order> listOrderToReturn = new ArrayList<>();
        // Getting an iterator
        Iterator orderIterator = DB.orderInfo.entrySet().iterator();

        // Iterate through the hashmap
        while (orderIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)orderIterator.next();
            listOrderToReturn.add((Order)(mapElement.getValue()));
        }
        return listOrderToReturn;
    }

    public Order addOrder(Order order){
        List<Order> listOrder = null;

        // Getting an iterator
        Iterator orderIterator = DB.orderInfo.entrySet().iterator();

        // Iterate through the hashmap
        while (orderIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)orderIterator.next();
            listOrder.add((Order)(mapElement.getValue()));
        }

        final int orderId = fromDB.orderId(listOrder);
        order.setId(orderId);

        DB.orderInfo.put(order.getOrderId(),order);

        //listOrder.add(order);
        //Collections.reverse(listOrder);
        return order;
    }

    public Order updateOrderInfo(int ordid,Order orderToUpdate){
            Order retrivedOrder = getByID(ordid);

            if(retrivedOrder == null){
                return null;
            }
            Optional.of(orderToUpdate.getPositions()).ifPresent(retrivedOrder::setPositions);
            Optional.of(orderToUpdate.getPrice()).ifPresent(retrivedOrder::setPrice);
            Optional.of(orderToUpdate.getStatus()).ifPresent(retrivedOrder::setStatus);
            return retrivedOrder;
    }

    public Object cancelOrder(int orderId){
        Order orderID = (Order) getByID(orderId);
        if(!(orderID.getStatus() == OrderStatus.PROCESSED))
            orderID.setStatus(OrderStatus.CANCELLED);
        else
            orderID = null;
        return orderID;
    }

}
