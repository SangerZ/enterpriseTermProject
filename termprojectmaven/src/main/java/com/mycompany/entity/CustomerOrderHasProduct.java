/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SangerZ
 */
@Entity
@Table(name = "customer_order_has_product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerOrderHasProduct.findAll", query = "SELECT c FROM CustomerOrderHasProduct c")
    , @NamedQuery(name = "CustomerOrderHasProduct.findByCustomerOrderId", query = "SELECT c FROM CustomerOrderHasProduct c WHERE c.customerOrderHasProductPK.customerOrderId = :customerOrderId")
    , @NamedQuery(name = "CustomerOrderHasProduct.findByProductId", query = "SELECT c FROM CustomerOrderHasProduct c WHERE c.customerOrderHasProductPK.productId = :productId")
    , @NamedQuery(name = "CustomerOrderHasProduct.findByQuantity", query = "SELECT c FROM CustomerOrderHasProduct c WHERE c.quantity = :quantity")})
public class CustomerOrderHasProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CustomerOrderHasProductPK customerOrderHasProductPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private short quantity;
    @JoinColumn(name = "customer_order_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CustomerOrder customerOrder;
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;

    public CustomerOrderHasProduct() {
    }

    public CustomerOrderHasProduct(CustomerOrderHasProductPK customerOrderHasProductPK) {
        this.customerOrderHasProductPK = customerOrderHasProductPK;
    }

    public CustomerOrderHasProduct(CustomerOrderHasProductPK customerOrderHasProductPK, short quantity) {
        this.customerOrderHasProductPK = customerOrderHasProductPK;
        this.quantity = quantity;
    }

    public CustomerOrderHasProduct(int customerOrderId, int productId) {
        this.customerOrderHasProductPK = new CustomerOrderHasProductPK(customerOrderId, productId);
    }

    public CustomerOrderHasProductPK getCustomerOrderHasProductPK() {
        return customerOrderHasProductPK;
    }

    public void setCustomerOrderHasProductPK(CustomerOrderHasProductPK customerOrderHasProductPK) {
        this.customerOrderHasProductPK = customerOrderHasProductPK;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerOrderHasProductPK != null ? customerOrderHasProductPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerOrderHasProduct)) {
            return false;
        }
        CustomerOrderHasProduct other = (CustomerOrderHasProduct) object;
        if ((this.customerOrderHasProductPK == null && other.customerOrderHasProductPK != null) || (this.customerOrderHasProductPK != null && !this.customerOrderHasProductPK.equals(other.customerOrderHasProductPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CustomerOrderHasProduct[ customerOrderHasProductPK=" + customerOrderHasProductPK + " ]";
    }
    
}
