package de.uniba.dsg.jaxrs.model.logic;

import de.uniba.dsg.jaxrs.model.dto.OrderStatusDTO;

public enum OrderStatus {
    SUBMITTED, PROCESSED,CANCELLED;

    public static OrderStatus convertOrderDTOStatusToOrderStatus(OrderStatusDTO argOrderStatus)
    {
        switch (argOrderStatus) {
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
