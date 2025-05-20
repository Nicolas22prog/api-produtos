/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.api.de.cadastro;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/products")
public class ProductResource {
    
    @PersistenceContext
    private EntityManager em;
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
    
    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, Product product) {
        
           Product produtoExistente = em.find(Product.class, id) ;
        if (produtoExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } 
           produtoExistente.setNome(product.getNome());
           produtoExistente.setPreco(product.getPreco());
           produtoExistente.setQuantidade(product.getQuantidade());
           
           em.merge(produtoExistente);
           return Response.ok().build();       
    }
    
    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
          Product produtoExistente = em.find(Product.class, id) ;
        if (produtoExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }  
            em.remove(produtoExistente);
            return Response.ok().build();
    }
    
}
