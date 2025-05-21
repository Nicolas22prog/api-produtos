/* Getter e Setters
 *
 */
package com.mycompany.api.de.cadastro;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table( name = "produtos")
public class Product {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double preco;
    private Integer quantidade;
    
    public Long getId(){
        return id;
    }
    
    public void setId( Long id) {
        this.id = id;
    }
    
    public String getNome () {
        return nome;
    } 
    
    public void setNome (String nome) {
        this.nome = nome;
    }
    
    public Double getPreco () {
        return preco;
    }
    
    public void setPreco ( Double preco) {
        this.preco = preco;
    }
            
    public Integer getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade( Integer quantidade) {
        this.quantidade=quantidade;
    }
}
