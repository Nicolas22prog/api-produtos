/* Aqui ficam os endpoints para os metodos 
*   do Postman, existem GET, POST, PUT, DELETE.
*   
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
    
    @GET //endpoint get para receber os produtos presentes no banco de dados
    public List<Product> listar(){
        return productService.listarTodos(); 
        
    }
    
    @POST // endpoint para cadastrar um produto novo
    public Response adicionar(Product product) {
        productService.salvar(product);
        return Response.status(Response.Status.CREATED).entity(product).build(); 
    }
    
    @PUT //endpoint para atualizar um produto existente
    @Path("{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, Product product) {
        
           Product produtoExistente = em.find(Product.class, id) ; //verifica se o produto selecionado existe 
        if (produtoExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } 
           produtoExistente.setNome(product.getNome());  //atualiza o nome 
           produtoExistente.setPreco(product.getPreco()); //atualiza o preço
           produtoExistente.setQuantidade(product.getQuantidade()); //atualiza a quantidade
           
           em.merge(produtoExistente);
           return Response.ok().build();       
    }
    
    @DELETE //endpoint para deletar um produto existente
    @Path("{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) { 
          Product produtoExistente = em.find(Product.class, id); //verifica se o produto selecionado existe 
        if (produtoExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }  
            em.remove(produtoExistente); //remove o produto 
            return Response.ok().build();
    }
    
}
