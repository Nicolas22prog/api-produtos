package com.mycompany.api.de.cadastro;

/* Gerenciador dos métodos da interface JSF2 */

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

@Named
@SessionScoped
public class ProductManagedBean implements Serializable {

    @EJB
    private ProductBean productBean;

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
                return productBean.buscarPaginado(first, pageSize);
            }
            @Override
            public int count(Map<String, FilterMeta>filterBy) {
                return productBean.totalProdutos();
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
        this.produtoSelecionado = produto;
        return "editar.xhtml?faces-redirect=true";
    }

    public String salvarEdicao() {
        productBean.editar(produtoSelecionado);
        produtoSelecionado = null;
        return "index?faces-redirect=true";
    }

    public void importarJson() {
        new Thread() {
            public void run () {
                productBean.importarJson();
            }
        }.start();
    }

    public void deletarTodos() {
        productBean.deleteAll();
    }
}
