package com.mycompany.api.de.cadastro;

/* Gerenciador dos métodos da interface JSF2 */

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

@Named
@ViewScoped
public class ProductManagedBean implements Serializable {

    @EJB
    private ProductBean productBean;
    
    @Inject
    private UserBean userBean;
    
    @Resource
    private ManagedExecutorService es;
    
    private Product product = new Product();
    private Product produtoSelecionado;

    private LazyDataModel<Product> lazyModel;
    

    // Getter para o LazyDataModel (usado no XHTML)
    public LazyDataModel<Product> getLazyModel() {
        return lazyModel;
    }

    // Inicializa o LazyDataModel com paginação
    @PostConstruct
    public void init() {
        lazyModel = new LazyDataModel<Product>() {
            @Override
            public List<Product> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                setRowCount(count(filterBy));
                return productBean.buscarPaginado(userBean.getUserLogado(), first, pageSize);
            }
            @Override
            public int count(Map<String, FilterMeta>filterBy) {
                return productBean.totalProdutos(userBean.getUserLogado());
            }
        };
    }

    // Getters e Setters padrão
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(Product produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

    // Ações
    public void salvar() {
        User userLogado = userBean.getUserLogado();
        if (userLogado != null) {
        productBean.salvar(product, userLogado);
        product = new Product();
        
    }else {
            System.out.println("Usuario nao logado");
        }
        User userGerenciado = productBean.findUserById(userLogado.getId());
        
        productBean.salvar(product, userGerenciado);
        product = new Product();
    }
    

    public void remover(Product product) {
        productBean.remover(product);
    }

    public String prepararEdicao(Long id) {
        this.produtoSelecionado = productBean.buscarPorId(id);
        return "editar.xhtml?faces-redirect=true";
    }

    public String editar(Product produto) {
        this.produtoSelecionado = produto;
        return "editar.xhtml?faces-redirect=true";
    }

    public String salvarEdicao() {
        productBean.editar(produtoSelecionado);
        produtoSelecionado = null;
        return "produtos?faces-redirect=true";
    }

    private boolean importando = false;
    public void importarJson() {
        if (importando) return;
        User userLogado = userBean.getUserLogado();
        if (userLogado != null) {
            importando = true;
            
        es.execute(()-> {
            try{
            productBean.importarJson(userLogado); 
        }finally {
                importando = false;
            }});
        }
                 
    }
    
    public void importarCsv() {
        if (importando) return;
        
        User userLogado = userBean.getUserLogado();
        if(userLogado != null) {
            importando = true;
        es.execute(()->{
            try{
                productBean.importarCsv(userLogado);
        } finally{
                importando = false;}}
        );
        }
    }
    
    public void deletarTodos() {
        if (importando) return;
        User userLogado = userBean.getUserLogado();
        if (userLogado != null){
            importando = true;
        
            es.execute(()-> {
            try{
            productBean.deleteAll(userLogado);       
                
            }finally{
                importando = false;
            }
            });            
    }
 }}
