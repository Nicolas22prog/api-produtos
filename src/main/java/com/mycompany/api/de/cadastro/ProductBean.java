/*Metodos para a interface do JSF2 
 * 
 */
package com.mycompany.api.de.cadastro;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.List;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager em;
    
    
    public List<Product> getProdutos() {
        return em.createQuery("SELECT p FROM Product p ORDER BY p.id", Product.class)
                .setMaxResults(150)
                .setFirstResult(0)
                .getResultList();        
    }

    
    public void salvar(Product product) {
        em.persist(product);
    }
    
    public void remover (Product product) {
      Product p = em.merge(product);
      em.remove(p);
    }
    public int deleteAll() {
        return em.createQuery("DELETE FROM Product p",Product.class).executeUpdate();
    }
    
    public void editar ( Product produto) {
        em.merge(produto);
        
    }
    
    public Product buscarPorId(Long id) {
    return em.find(Product.class, id);
}
    
    
    public void importarJson() {       
       Gson gson = new Gson();
        InputStream input = getClass().getClassLoader().getResourceAsStream("produtos.json");        
        if (input != null) {
           try (JsonReader jsonReader = new JsonReader(new InputStreamReader(input, "UTF-8"))) { 
               jsonReader.beginArray();
               
               int batchSize = 100;
               int count = 0;
               
               while(jsonReader.hasNext()) {
                   
                   
                   Product produto = gson.fromJson(jsonReader, Product.class);
                           em.persist(produto);
                           count++;
                           
                           if(count%batchSize == 0) {
                               em.flush();
                               em.clear();
                           }
               }               
                            if(count % batchSize != 0 ) {
                                em.flush();
                                em.clear();
                            }
                            
                            
               jsonReader.endArray();
               
              
            } catch (Exception e) {
                e.printStackTrace();
           }
           } else {
            System.out.println("Arquivo json nao encontrado no classpath.");
        }
    
    }
  
    
    public List<Product> buscarPaginado (int offset, int tamanhoPagina) {
        
        return em.createQuery("SELECT p FROM Product p", Product.class)
                .setFirstResult(offset)
                .setMaxResults(tamanhoPagina)
                .getResultList();
        
    }
    
    public int totalProdutos() {
        return ((Number) em.createQuery("SELECT COUNT(p) FROM Product p").getSingleResult()).intValue();
    }
    
    public int getTotalPaginas (int tamanhoPagina) {
        int totalProdutos = totalProdutos();
        return (int) Math.ceil((double) totalProdutos/ tamanhoPagina);
    }
 
    
    
    
}
