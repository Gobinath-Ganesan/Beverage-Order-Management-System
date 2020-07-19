package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.logic.OrderStatus;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "OrderStatus")
@XmlEnum

public enum OrderStatusDTO {
        SUBMITTED, PROCESSED,CANCELLED;

    static OrderStatusDTO getDTO(OrderStatus orderStatus) {
        switch (orderStatus) {
            case SUBMITTED:
                return SUBMITTED;
            case PROCESSED:
                return PROCESSED;
            case CANCELLED:
                return CANCELLED;
            default:
                return null;
        }
    }
}



