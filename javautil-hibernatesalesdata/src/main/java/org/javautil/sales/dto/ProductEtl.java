package org.javautil.sales.dto;
// Generated Sep 8, 2011 2:57:23 PM by Hibernate Tools 3.2.2.GA


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ProductEtl generated by hbm2java
 */
@Entity
@Table(name="PRODUCT_ETL"
    ,schema="PUBLIC"
    ,catalog="SALES"
)
public class ProductEtl  implements java.io.Serializable {


     private ProductEtlId id;

    public ProductEtl() {
    }

    public ProductEtl(ProductEtlId id) {
       this.id = id;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="productEtlId", column=@Column(name="PRODUCT_ETL_ID", nullable=false, precision=9, scale=0) ), 
        @AttributeOverride(name="upc10", column=@Column(name="UPC10", nullable=false, length=10) ), 
        @AttributeOverride(name="productStatus", column=@Column(name="PRODUCT_STATUS", nullable=false, length=1) ), 
        @AttributeOverride(name="descr", column=@Column(name="DESCR", nullable=false, length=50) ), 
        @AttributeOverride(name="narrative", column=@Column(name="NARRATIVE", nullable=false) ) } )
    public ProductEtlId getId() {
        return this.id;
    }
    
    public void setId(ProductEtlId id) {
        this.id = id;
    }




}


