/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.session;

import com.mycompany.entity.CustomerOrderHasProduct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author SangerZ
 */
@Stateless
public class CustomerOrderHasProductFacade extends AbstractFacade<CustomerOrderHasProduct> {

    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerOrderHasProductFacade() {
        super(CustomerOrderHasProduct.class);
    }
    
    //manually created
    public List<CustomerOrderHasProduct> findByOrderId(Object id){
        return em.createNamedQuery("CustomerOrderHasProduct.findByCustomerOrderId").setParameter("customerOrderId", id).getResultList();
    }
}
