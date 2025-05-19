/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.api.de.cadastro;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/products")
public class ProductResource {
    
    @Inject
    private ProductService productService;
    
    @GET
    public List<Product> listar(){
        return productService.listarTodos();
    }
    
    @POST
    public Response adicionar(Product product) {
        productService.salvar(product);
        return Response.status(Response.Status.CREATED).entity(product).build();
    }
    
    
   
}
