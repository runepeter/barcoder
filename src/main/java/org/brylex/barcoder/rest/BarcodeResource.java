package org.brylex.barcoder.rest;

import org.brylex.barcoder.service.BarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URL;

@Component
@Path("barcode")
public class BarcodeResource
{
    @Autowired
    private BarcodeService service;

    @PUT
    @Path("{url}")
    public Response create(@PathParam("url") String url) throws Exception
    {
        service.create(new URL("http://www.vg.no"));

        return Response.created(new URI("urn:jalla")).build();
    }

    @GET
    @Path("qr/{url}")
    @Produces("application/barcode+qr")
    public Response getQr(@PathParam("url") URL url)
    {
        return Response.ok("jalla").build();
    }

    @GET
    @Path("datamatrix/{url}")
    @Produces("application/barcode+datamatrix")
    public Response getDatamatrix(@PathParam("url") URL url)
    {
        return Response.ok("jalla").build();
    }
}
