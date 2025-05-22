package com.mycompany.api.de.cadastro;

/* Gerenciador dos metodos da interface JSF2*/
 

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProductManagedBean implements Serializable {
    
    @EJB
    private ProductBean productBean;
    private Product product = new Product();
    private Product produtoSelecionado;
    private ProductService productService;
    
        
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
    
    public void importarJson() {
        productBean.importarJson();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Importação iniciada em segundo plano."));
    }
    
    public void deletarTodos () {
        productBean.deleteAll();
    }
}
