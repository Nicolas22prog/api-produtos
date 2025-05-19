/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.api.de.cadastro;


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

/**
 *
 * @author nicol
 */
@Named
@RequestScoped
public class ProductManagedBean {
    
    @Inject
    private ProductBean productBean;
    private Product product = new Product();
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct (Product product) {
        this.product = product;
        
    }
    
    public List<Product> getProdutos() {
        return productBean.getProdutos();
    }
    
    public String salvar() {
        productBean.salvar();
        product = new Product();
        return "index?faces-redirect=true";
    }
}
