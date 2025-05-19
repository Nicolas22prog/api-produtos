/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.api.de.cadastro;


import jakarta.enterprise.context.SessionScoped;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProductManagedBean implements Serializable {
    
    @Inject
    private ProductBean productBean;
    private Product product = new Product();
    private Product produtoSelecionado;
    
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
        productBean.salvar(product);
        product = new Product();  
        return "index?faces-redirect=true";
    }
    
    public void remover(Product product) {
        productBean.remover(product);
    }
    
    
    public String prepararEdicao(Long id) {
    this.produtoSelecionado = productBean.buscarPorId(id);
    return "editar.xhtml?faces-redirect=true";
}
    
    public String editar(Product produto) {
        
        this.produtoSelecionado=produto;
        return "editar.xhtml?faces-redirect=true";
    }
    
    public String salvarEdicao() {
        
        productBean.editar(produtoSelecionado);
        produtoSelecionado = null;
        return "index?faces-redirect=true";
    }
    
        
    public Product getProdutoSelecionado () {
        return produtoSelecionado;
    }
    
    public void setProdutoSelecionado(Product produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }
    
}

