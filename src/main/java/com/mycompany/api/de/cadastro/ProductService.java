/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.api.de.cadastro;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductService {
    
    
    @PersistenceContext(unitName = "produtoPU")
    private EntityManager em;
    
    public List<Product> listarTodos() {
        return em.createQuery("SELECT p FROM Product p",Product.class ).getResultList();
    }
    
    public void salvar(Product product) {
        em.persist(product);
    }
    
    
}
