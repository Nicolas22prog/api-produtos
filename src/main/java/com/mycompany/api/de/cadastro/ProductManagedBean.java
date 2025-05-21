/* Gerenciador dos metodos da interface JSF2*/
 
package com.mycompany.api.de.cadastro;


import com.google.gson.Gson;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;


@Named
@SessionScoped
public class ProductManagedBean implements Serializable {
    
    @Inject
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
        Gson gson = new Gson();
        
        InputStream input = getClass().getClassLoader().getResourceAsStream("produtos.json");
        
        if (input != null) {
           try (InputStreamReader reader = new InputStreamReader(input, "UTF-8")) {
               Product [] produtos = gson.fromJson(reader, Product[].class);
               
               for (Product produto : produtos) {
                   productBean.salvar(produto);
               }
            } catch (Exception e) {
                e.printStackTrace();
           }
           } else {
            System.out.println("Arquivo json nao encontrado no classpath.");
        }
    }
    
}

