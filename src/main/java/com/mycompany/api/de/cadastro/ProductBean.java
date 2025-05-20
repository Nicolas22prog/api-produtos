/*Metodos para a interface do JSF2 
 * 
 */
package com.mycompany.api.de.cadastro;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager em;


    private Product product = new Product();

    public Product getProduto() {
        return product;
    }

    public List<Product> getProdutos() {
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public void salvar(Product product) {
        em.persist(product);
    }
    
    public void remover (Product product) {
      Product p = em.merge(product);
      em.remove(p);
    }
    
    public void editar ( Product produto) {
        em.merge(produto);
        
    }
    
    public Product buscarPorId(Long id) {
    return em.find(Product.class, id);
}

}