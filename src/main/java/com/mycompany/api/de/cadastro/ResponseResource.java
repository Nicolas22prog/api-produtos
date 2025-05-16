/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.api.de.cadastro;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collections;

@Path("/evenValue")
public class ResponseResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{intVal}")
    public Response testValue(@PathParam("intVal") Integer value) {
        if (value % 2 == 0) {
            return Response.ok("Value is a correct even number").build();
        } else {
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }
}
