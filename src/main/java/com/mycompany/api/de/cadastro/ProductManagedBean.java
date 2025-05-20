/* Gerenciador dos metodos da interface JSF2*/
 
package com.mycompany.api.de.cadastro;


import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.Part;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@Named
@SessionScoped
public class ProductManagedBean implements Serializable {
    
    @Inject
    private ProductBean productBean;
    private Product product = new Product();
    private Product produtoSelecionado;
    private ProductService productService;
    
    private Part arquivo;
    
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
    
    
    public void importarProdutos() {
        try (InputStream input = arquivo.getInputStream()) {
            String caminhoTemp = System.getProperty("java.io.tmpdir")+"/"+arquivo.getSubmittedFileName();
            Files.copy(input, Paths.get(caminhoTemp));
            productService.importarProdutos(caminhoTemp);
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso", "Produtos importados com sucesso!"));
        } catch(IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro","Falha ao importar: " +e.getMessage()));
            
        
        } 
        
    }
    public Part getArquivo() {
        return arquivo;
    }
    public void setArquivo(Part arquivo){
        this.arquivo=arquivo;
    }
}

