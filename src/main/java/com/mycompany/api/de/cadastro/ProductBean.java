
package com.mycompany.api.de.cadastro;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.opencsv.CSVReader;
import jakarta.inject.Inject;


import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.List;

@Stateless
public class ProductBean {

    @Inject
    private User user;
    
    @PersistenceContext
    private EntityManager em;
    
    
    public List<Product> getProdutos() {
        return em.createQuery("SELECT p FROM Product p WHERE p.user = :user ORDER BY p.id", Product.class)
                .setParameter("user", user)
                .setMaxResults(150)
                .setFirstResult(0)
                .getResultList();        
    }

    
    public void salvar(Product product, User user) {

        product.setUser(user);
        em.persist(product);
    }
    
    public void remover (Product product) {
      Product p = em.merge(product);
      em.remove(p);
    }
    public int deleteAll(User user) {
        System.out.println("deleteAll() foi chamado");
        return em.createQuery("DELETE FROM Product p WHERE p.user = :user",Product.class).setParameter("user", user).executeUpdate();
    }
    
    public void editar ( Product produto) {
        em.merge(produto);
        
    }
    
    public Product buscarPorId(Long id) {
    return em.find(Product.class, id);
}
public void importarCsv(User user) {
    System.out.println("importarCsv() foi chamado");
    User userGerenciado = em.find(User.class, user.getId());
    
    if ( userGerenciado == null) {
        throw new IllegalArgumentException("Usuario nao cadastrado");
    }
    
    InputStream input = getClass().getClassLoader().getResourceAsStream("produtos.csv");
    
    if (input != null) {
        try( 
            InputStreamReader isr = new InputStreamReader(input, "UTF-8");
            CSVReader csvReader = new CSVReader(isr);
                ){
            String [] linha;
            int count = 0;
            int batchSize = 1000;
            
            csvReader.readNext();
            
            while ((linha = csvReader.readNext()) != null) {
                Product produto = new Product();
                produto.setNome(linha[1]);
                produto.setPreco(Double.parseDouble(linha[2]));
                produto.setQuantidade(Integer.parseInt(linha[3]));
                
                
                
                produto.setUser(userGerenciado);
                em.persist(produto);
                count++;
                
                if (count %batchSize == 0) {
                    em.flush();
                    em.clear();
                }
            }
             
            if (count % batchSize != 0 ) {
                em.flush();
                em.clear();
            }
            System.out.println("Importação concluida com" + count + "produtos.");
}catch (Exception e) {
            e.printStackTrace();
        }
               
            
        } else {
        System.out.println("Arquivo CSV nao encontrado no classpath");
    }
    }
   
    public void importarJson(User user) {
         System.out.println("importarJson() foi chamado");
        User userGerenciado = em.find(User.class, user.getId());
        if ( userGerenciado == null) {
        throw new IllegalArgumentException("Usuario nao cadastrado");
    }
       Gson gson = new Gson();
        InputStream input = getClass().getClassLoader().getResourceAsStream("produtos.json");        
        if (input != null) {
           try (JsonReader jsonReader = new JsonReader(new InputStreamReader(input, "UTF-8"))) { 
               jsonReader.beginArray();
               
               int batchSize = 1000;
               int count = 0;
               
               while(jsonReader.hasNext()) {
                   
                   
                   Product produto = gson.fromJson(jsonReader, Product.class);
                            produto.setUser(userGerenciado);
                           em.persist(produto);
                           count++;
                           
                           if(count%batchSize == 0) {
                               em.flush();
                               em.clear();
                           }
               }               
                            if(count % batchSize != 0 ) {
                                em.flush();
                                em.clear();
                            }
                            System.out.println("Importação concluida com" + count + "produtos.");
               
               jsonReader.endArray();
                      
               
              
            } catch (Exception e) {
                e.printStackTrace();
           }
           } else {
            System.out.println("Arquivo json nao encontrado no classpath.");
        }
    
    }
  
    
    public List<Product> buscarPaginado (User user ,int offset, int tamanhoPagina) {
        
        return em.createQuery("SELECT p FROM Product p WHERE p.user = :user", Product.class)
                .setParameter("user", user)
                .setFirstResult(offset)
                .setMaxResults(tamanhoPagina)
                .getResultList();
        
    }
    
    public int totalProdutos(User user) {
        return ((Number) em.createQuery("SELECT COUNT(p) FROM Product p WHERE p.user = :user")
                .setParameter("user",user)
                .getSingleResult()).intValue();
    }
    
    public int getTotalPaginas (User user, int tamanhoPagina) {
        int totalProdutos = totalProdutos(user);
        return (int) Math.ceil((double) totalProdutos/ tamanhoPagina);
    }

   public User findUserById(Long id) {
        return em.find(User.class, id);
    }
 
    
    
    
}
