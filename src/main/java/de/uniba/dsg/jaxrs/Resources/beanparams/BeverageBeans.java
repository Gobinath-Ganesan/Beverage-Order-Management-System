package de.uniba.dsg.jaxrs.Resources.beanparams;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class BeverageBeans {

    private @QueryParam("start") @DefaultValue("1") int start;
    private @QueryParam("end") @DefaultValue("5") int size;
    private @Context UriInfo uriinf;



    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public UriInfo getUriinf() {
        return uriinf;
    }

    public void setUriinf(UriInfo uriinf) {
        this.uriinf = uriinf;
    }


}
