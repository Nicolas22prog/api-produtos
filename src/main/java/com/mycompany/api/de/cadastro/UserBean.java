/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.api.de.cadastro;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.io.Serializable;

@Named
@SessionScoped
public class UserBean implements Serializable {
   
    private User user = new User();
    private User userLogado;
    
    @PersistenceContext
    private EntityManager em;
    
    public User getUser () {
        return user;
    }
    
    public void setUser(User user) {
        this.user=user;
    }
    
    public User getUserLogado() {
        return userLogado;
    }
    
    public void setUserLogado (User userLogado) {
        this.userLogado=userLogado;
    }
    
   
    public String entrar() {
        try{
            User u = em.createQuery(
            "SELECT u FROM User u WHERE u.usuario = :usuario AND u.senha = :senha",User.class)
                    .setParameter("usuario", user.getUsuario())
                    .setParameter("senha", user.getSenha())
                    .getSingleResult();
            this.userLogado = u;
            
            return "produtos.xhtml?faces-redirect=true";
        } catch(Exception e) {
            return "erroLogin.xhtml?faces-redirect=true";
        }
    }
    @Transactional
    public String cadastrar() {
        
        if(user.getUsuario() == null || user.getUsuario().isEmpty() || 
                user.getSenha ()== null || user.getSenha().isEmpty()) {
            return "Campos obrigatorios";
        }
        try {
            User u =em.createQuery("SELECT u FROM User u WHERE u.usuario = :usuario ",User.class)
                    .setParameter("usuario", user.getUsuario())                  
                    .getSingleResult();
           return "Usuario ja cadastrado";
        } catch (NoResultException e) {
            em.persist(user);
            return "Usuario Cadastrado com sucesso!";
        }
    }
}
