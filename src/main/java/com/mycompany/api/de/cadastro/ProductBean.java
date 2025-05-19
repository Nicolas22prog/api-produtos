/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.api.de.cadastro;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;


@Named
@RequestScoped
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

    public String salvar() {
        em.persist(product);
        product = new Product(); // limpa o formulário
        return "index?faces-redirect=true";
    }
}