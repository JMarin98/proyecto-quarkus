package com.pruebaconcepto;

import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/procesar")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GreetingResource {

    @Inject
    ProcessResourceService processResourceService;

     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.APPLICATION_XML)
    public Response procesarEntrada(StratusDTO request) {
        System.out.println("Request recibido: " + request);

        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El cuerpo de la solicitud está vacío"))
                    .build();
        }else{
            return processResourceService.ProcessData(request);
        }
    }
}