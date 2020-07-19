package de.uniba.dsg.jaxrs.Resources;

import de.uniba.dsg.jaxrs.Controller.OrderService;
import de.uniba.dsg.jaxrs.model.dto.BottleDTO;
import de.uniba.dsg.jaxrs.model.dto.OrderDTO;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;
import de.uniba.dsg.jaxrs.model.logic.*;

import javax.swing.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("Order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private static final Logger logger = Logger.getLogger("OrderResource");
    OrderService ords = new OrderService();

    @GET
    public Response getAllOrders(){
        logger.info("Returning all the orders info");
        List<Order> allOrders = ords.getAllOrders();
        List<OrderDTO> allOrderDTO = new ArrayList<>();
        for (Order o:allOrders
             ) {
            allOrderDTO.add(new OrderDTO(o));
        }
        GenericEntity<List<OrderDTO>> list = new GenericEntity<List<OrderDTO>>(allOrderDTO) {};
        if(list.getEntity().size() == 0)
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.DATA_NOT_FOUND,"Orders not available")).build();
        else
            return Response.ok(list).build();
    }

    @POST
    public Response addOrder(OrderDTO order,@Context UriInfo uriinfo){
        logger.info("Adding new order");
        if(order == null)
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,"Request body is empty")).build();

        //Convert OrderDTO status into Orderstatus
        OrderStatus ordStatus = OrderStatus.convertOrderDTOStatusToOrderStatus(order.getStatus());

        //Convert OrderItemsDTO into OrderItem
        List<OrderItem> ordItem = OrderItem.convertDTOOrderItemToOrderItem(order.getPositions());

        final Order ord = new Order(
                order.getOrderId(),
                ordItem,
                order.getPrice(),
                ordStatus
        );

        Order newOrder= ords.addOrder(ord);
        String newId = String.valueOf(ord.getOrderId());
        URI uri = uriinfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(uri)
                .entity(new OrderDTO(newOrder))
                .build();
    }

    @DELETE
    @Path("{orderId}")
    public Response cancelOrder(@PathParam("orderId") int orderId){
        logger.info("Cancel the order");
        Object cancelorder = (Order) ords.cancelOrder(orderId);
        if(cancelorder.equals(null)){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.DATA_NOT_FOUND,"Order not available")).build();
        }
        else{
            return Response.ok(cancelorder).build();
        }
    }

    @PUT
    @Path("{orderId}")
    public Response updateOrder(OrderDTO updateOrder, @Context UriInfo uriinfo ,@PathParam("orderId") int ordid){
        logger.info("Updating the order info by id");
        if(updateOrder == null)
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,"Incorrect request received")).build();

        //Convert OrderDTO status into Orderstatus
        OrderStatus ordStatus = OrderStatus.convertOrderDTOStatusToOrderStatus(updateOrder.getStatus());

        //Convert OrderItemsDTO into OrderItem
        List<OrderItem> ordItem = OrderItem.convertDTOOrderItemToOrderItem(updateOrder.getPositions());

        final Order ord = new Order(
                updateOrder.getOrderId(),
                ordItem,
                updateOrder.getPrice(),
                ordStatus
        );

        Order updatedOrder = ords.updateOrderInfo(ordid,ord);
        return  Response.ok().entity(new OrderDTO(updatedOrder)).build();
    }

    @GET
    @Path("{orderiId}")
    public Response getOrderById(@PathParam("orderiId") String orderId){
        //GenericEntity<List<Order>> list = new GenericEntity<List<Order>>(ords.getByID(orderId)) {};
        logger.info("Returning a particular order info");
        try{
            int argId = Integer.parseInt(orderId);
            final Order order = ords.getByID(argId);
            if(order == null)
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.DATA_NOT_FOUND,"Order not available")).build();
            return Response.ok().entity(new OrderDTO(order)).build();
        }
        catch (NumberFormatException e) {
            logger.log(Level.SEVERE,e.getStackTrace().toString());
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,"Incorrect request")).build();
        } catch(Exception e) {
//            logger.log(Level.SEVERE,e.getStackTrace().toString());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
