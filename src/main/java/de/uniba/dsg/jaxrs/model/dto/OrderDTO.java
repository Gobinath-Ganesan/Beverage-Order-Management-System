package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.logic.Bottle;
import de.uniba.dsg.jaxrs.model.logic.Order;
import de.uniba.dsg.jaxrs.model.logic.OrderItem;
import de.uniba.dsg.jaxrs.model.logic.OrderStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
public class OrderDTO {

    private int orderId;
    private List<OrderItemDTO> positions;
    private double price;
    private OrderStatusDTO status;

    public OrderDTO(){

    }

    public OrderDTO(final Order order) {
        this.orderId = order.getOrderId();
        this.positions = new ArrayList<>();
        for(OrderItem item: order.getPositions())
            this.positions.add(new OrderItemDTO(item));
        this.price = order.getPrice();
        this.status = OrderStatusDTO.getDTO(order.getStatus());
    }

    public int getOrderId() {
        return orderId;
    }

    public List<OrderItemDTO> getPositions() {
        return positions;
    }

    public void setPositions(List<OrderItemDTO> positions) {
        this.positions = positions;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderStatusDTO getStatus() {
        return status;
    }

    public void setStatus(OrderStatusDTO status) {
        this.status = status;
    }

    public void setId(){
        this.orderId = 55;
    }
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", positions=" + positions +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
