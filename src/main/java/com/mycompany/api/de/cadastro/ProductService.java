/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.api.de.cadastro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class ProductService {
    
    private static final Logger LOGGER = Logger.getLogger(ProductService.class.getName());
    
    @PersistenceContext(unitName = "produtoPU")
    private EntityManager em;
    
    public List<Product> listarTodos() {
        return em.createQuery("SELECT p FROM Product p",Product.class ).getResultList();
    }
    
    public void salvar(Product product) {
        em.persist(product);
    }
    
    public void importarProdutos (String caminhoArquivo) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File arquivoJson = new File(caminhoArquivo);
            List<Product> produtos = mapper.readValue(arquivoJson,new TypeReference<List<Product>>(){});
            
            for (Product produto : produtos) {
                if(produto.getNome() != null && !produto.getNome().isEmpty() && produto.getPreco()>0) {
                    em.persist(produto);
                } else {
                    LOGGER.warning("Produto inválido " + produto);
                }
                
            }
            em.flush();
        } catch (IOException e) {
        LOGGER.severe("Erro ao importar JSON: ");
        throw e;
    }
    }
    
}
