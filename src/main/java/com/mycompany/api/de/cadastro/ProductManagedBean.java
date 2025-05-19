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

/**
 *
 * @author nicol
 */
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
        product = new Product();  // limpa o formulário
        return "index?faces-redirect=true";
    }
    
    public void remover(Product product) {
        productBean.remover(product);
    }
    
    public String editar(Product produto) {
        System.out.println("Editar produto :" +produto.getId());
        this.produtoSelecionado=produto;
        return "editar.xhtml?faces-redirect=true";
    }
    
    public String salvarEdicao() {
        System.out.println("Salvando edicao para produto ID: " + produtoSelecionado.getId());
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

