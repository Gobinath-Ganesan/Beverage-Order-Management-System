package de.uniba.dsg.jaxrs.Resources;

import de.uniba.dsg.jaxrs.Controller.BottleService;
import de.uniba.dsg.jaxrs.Controller.CrateService;
import de.uniba.dsg.jaxrs.Resources.beanparams.BeverageBeans;
import de.uniba.dsg.jaxrs.model.api.PaginatedItems;
import de.uniba.dsg.jaxrs.model.dto.BottleDTO;
import de.uniba.dsg.jaxrs.model.dto.CrateDTO;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;
import de.uniba.dsg.jaxrs.model.logic.Bottle;
import de.uniba.dsg.jaxrs.model.logic.Crate;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("Beverage")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BeverageResource {
    private static final Logger logger = Logger.getLogger("BeverageResource");

    BottleService bots = new BottleService();
    CrateService crats = new CrateService();

    @GET
    @Path("bottle")
    public Response getAllBottles(@BeanParam BeverageBeans bevBean){
        logger.info("Returning all available bottles info");
        final List<Bottle> bottles = bots.getAllBottles();
        final URI uri = bevBean.getUriinf().getRequestUri();
        final PaginationHelper<Bottle> helper = new PaginationHelper<>(bottles);

        if (bevBean.getSize() < 1 || bevBean.getStart() < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,"Incorrect pagindation values")).build();
        }
        List<BottleDTO> bottleDTO = new ArrayList<>();
        int start_index =  (bevBean.getStart()-1) * bevBean.getSize();
        for(Bottle b: bottles.subList(Math.max(0, start_index), Math.min(bottles.size(), start_index+bevBean.getSize())))
            bottleDTO.add(new BottleDTO(b));

        final PaginatedItems<BottleDTO> response = new PaginatedItems<>(
                helper.getPagination(bevBean.getUriinf(), bevBean.getStart(), bevBean.getSize()),
                bottleDTO,
                uri);
        return Response.ok(response).build();

    }

    @POST
    @Path("bottle")
    public Response addBottle(BottleDTO bottle, @BeanParam BeverageBeans bevBean){
        logger.info("Returning new bottle info");
        //bots.addBottle(bottle);
        if(bottle == null)
            return  Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,"Incoming data is empty")).build();

        final Bottle bot = new Bottle(
                bottle.getId(),
                bottle.getName(),
                bottle.getVolume(),
                bottle.isAlcoholic(),
                bottle.getVolumePercent(),
                bottle.getPrice(),
                bottle.getSupplier(),
                bottle.getInStock()
        );

        Bottle newlyAddedBottle = bots.addBottle(bot);
        //GenericEntity<List<Bottle>> list = new GenericEntity<List<Bottle>>(bots.addBottle(bot)) {};
        String newId = String.valueOf(newlyAddedBottle.getId());
        URI uri = bevBean.getUriinf().getAbsolutePathBuilder().path(newId).build();
        return Response.created(uri)
                .entity(new BottleDTO(newlyAddedBottle))
                .build();
    }

    @GET
    @Path("bottle/{bottleId}")
    public Response getBottleById(@PathParam("bottleId") String botId){
        logger.info("Returning a particular bottle info");
        try{
            int argIn = Integer.parseInt(botId);
            logger.info("Returning a particular bottle info");
            final Bottle bottle = bots.getByID(argIn);
            if(bottle == null)
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.DATA_NOT_FOUND,"Unable to find the info")).build();
            return Response.ok().entity(new BottleDTO(bottle)).build();
        }
        catch (NumberFormatException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,"Incorrect parameter type sent")).build();
        }
    }

    @GET
    @Path("bottle/maxPrice")
    public Response getBottleByMaxPrice(){
        logger.info("Returning bottle info with max price");
        List<Bottle> maxPriceBottle = bots.getByMaxPrice();
        List<BottleDTO> bottleDTO = new ArrayList<>();
        for (Bottle bottle:maxPriceBottle
             ) {
            bottleDTO.add(new BottleDTO(bottle));
        }
        GenericEntity<List<BottleDTO>> entity = new GenericEntity<List<BottleDTO>>(bottleDTO) {};
        if(entity == null)
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.DATA_NOT_FOUND,"Unable to find the info")).build();
        else
            return  Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("bottle/minPrice")
    public Response getBottleByMinPrice(){
        logger.info("Returning bottle info with max price");
        List<Bottle> minPriceBottle = bots.getByMinPrice();
        List<BottleDTO> bottleDTO = new ArrayList<>();
        for (Bottle bottle:minPriceBottle
        ) {
            bottleDTO.add(new BottleDTO(bottle));
        }
        GenericEntity<List<BottleDTO>> entity = new GenericEntity<List<BottleDTO>>(bottleDTO) {};
        if(entity == null)
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.DATA_NOT_FOUND,"Unable to find the info")).build();
        else
            return  Response.status(Response.Status.OK).entity(entity).build();
    }

    @PUT
    @Path("bottle/{bottleId}")
    public Response updateBottle(BottleDTO updatebottle, @Context UriInfo uriinfo ,@PathParam("bottleId") int botid){
        logger.info("Updating the bottle info by id");
        if(updatebottle == null)
            return  Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,"Bottle data not found in the body")).build();
        final Bottle bot = new Bottle(
                updatebottle.getId(),
                updatebottle.getName(),
                updatebottle.getVolume(),
                updatebottle.isAlcoholic(),
                updatebottle.getVolumePercent(),
                updatebottle.getPrice(),
                updatebottle.getSupplier(),
                updatebottle.getInStock()
        );
        Bottle updateedBttle = bots.updateBottle(botid,bot);
        return  Response.ok().entity(new BottleDTO(updateedBttle)).build();
    }

    @DELETE
    @Path("bottle/{bottleId}")
    public Response removeBottle(@PathParam("bottleId") String botId){
        logger.info("Deleting the bottle by id " + botId);
        try{
            int argIn = Integer.parseInt(botId);
            final boolean isDeleted = bots.removeBottle(argIn);
            if(!isDeleted)
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.DATA_NOT_FOUND,"Unable to find the bottel info for deletion")).build();
            return Response.ok().build();
        }
        catch (NumberFormatException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,"Incorrect parameter type sent")).build();
        }

    }
    //--------------------------------------------------------------------------------------------------//

    @GET
    @Path("crate")
    public Response getAllCrates(@QueryParam("start") int start, @QueryParam("end") int size){
        logger.info("Returning all available crate info");
        GenericEntity<List<Crate>> list;
        if((start > 0)){
            logger.info("Inside pagination method");
            list = new GenericEntity<List<Crate>>(crats.getAllCratesPaginated(start,size)) {};
        }
        else{
            logger.info("Inside normal method");
            list = new GenericEntity<List<Crate>>(crats.getAllCrates()) {};
        }
        if(list.getEntity().size() == 0)
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        else
            return Response.ok(list).build();
    }

    @POST
    @Path("crate")
    public Response addCrate(CrateDTO crate, @Context UriInfo uriinfo){
        logger.info("Returning new crate info");
        //bots.addBottle(bottle);
        if(crate == null)
            return  Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.DATA_NOT_FOUND,"Request body is emprty")).build();

        final Crate crat = new Crate(
                crate.getId(),
                crate.getBottle(),
                crate.getNoOfBottles(),
                crate.getPrice(),
                crate.getInStock()
        );

        Crate addnewCrate = crats.addCrate(crat);
        String newId = String.valueOf(crat.getId());
        URI uri = uriinfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(uri)
                .entity(new CrateDTO(addnewCrate))
                .build();
    }

    @GET
    @Path("crate/{crateId}")
    public Response getCrateeById(@PathParam("crateId") String cratId){
        logger.info("Returning a particular crate info");
        try{
            int argIn = Integer.parseInt(cratId);
            final Crate crate = crats.getByID(argIn);
            if(crate == null)
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.DATA_NOT_FOUND,"Unable to retrive the data fro the given id")).build();
            return Response.ok().entity(new CrateDTO(crate)).build();
        }
        catch (NumberFormatException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,"Invalid input type")).build();
        }
    }

    @GET
    @Path("crate/maxPrice")
    public Response getCrateByMaxPrice(){
        logger.info("Returning a particular crate info with max price");
        GenericEntity<List<Crate>> entity = new GenericEntity<List<Crate>>(crats.getByMaxPrice()) {};
        if(entity == null)
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.DATA_NOT_FOUND,"Unable to find the required crate info")).build();
        else
            return  Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("crate/minPrice")
    public Response getCrateByMinPrice(){
        logger.info("Returning a particular bottle info with max price");
        GenericEntity<List<Crate>> entity = new GenericEntity<List<Crate>>(crats.getByMinPrice()) {};
        if(entity == null)
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.DATA_NOT_FOUND,"Unable to find the required crate info")).build();
        else
            return  Response.status(Response.Status.OK).entity(entity).build();
    }

    @PUT
    @Path("crate/{crateId}")
    public Response updateCrate(CrateDTO updatecrate, @Context UriInfo uriinfo ,@PathParam("crateId") int cratid){
        logger.info("Updating the bottle info by id");
        if(updatecrate == null)
            return  Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.DATA_NOT_FOUND,"Request body does not contain required information")).build();
        final Crate crat = new Crate(
                updatecrate.getId(),
                updatecrate.getBottle(),
                updatecrate.getNoOfBottles(),
                updatecrate.getPrice(),
                updatecrate.getInStock()
        );
        Crate updateedCrate = crats.updateCrate(cratid,crat);
        return  Response.ok().entity(new CrateDTO(updateedCrate)).build();
    }

    @DELETE
    @Path("crate/{crateId}")
    public Response removeCrate(@PathParam("crateId") String cratId){
        logger.info("Deleting the crate by id " + cratId);
        try{
            int argIn = Integer.parseInt(cratId);
            final Boolean isDeleted = crats.removeCrate(argIn);
            if(!isDeleted)
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.DATA_NOT_FOUND,"Unable to find the crate info for deletion")).build();
            return Response.ok().build();
        }
        catch (NumberFormatException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,"Incorrect parameter type sent")).build();
        }

    }
}
