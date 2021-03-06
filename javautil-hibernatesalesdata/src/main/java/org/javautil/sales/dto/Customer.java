package org.javautil.sales.dto;
// Generated Sep 8, 2011 2:57:23 PM by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Customer generated by hbm2java
 */
@Entity
@Table(name="CUSTOMER"
    ,schema="PUBLIC"
    ,catalog="SALES"
)
public class Customer  implements java.io.Serializable {


     private Integer customerId;
     private Salesperson salesperson;
     private String customerStatus;
     private String name;
     private String addr1;
     private String addr2;
     private String city;
     private String state;
     private String zipCd;
     private Integer outsideSalespersonId;
     private Set<Sale> sales = new HashSet<Sale>(0);

    public Customer() {
    }

	
    public Customer(Salesperson salesperson) {
        this.salesperson = salesperson;
    }
    public Customer(Salesperson salesperson, String customerStatus, String name, String addr1, String addr2, String city, String state, String zipCd, Integer outsideSalespersonId, Set<Sale> sales) {
       this.salesperson = salesperson;
       this.customerStatus = customerStatus;
       this.name = name;
       this.addr1 = addr1;
       this.addr2 = addr2;
       this.city = city;
       this.state = state;
       this.zipCd = zipCd;
       this.outsideSalespersonId = outsideSalespersonId;
       this.sales = sales;
    }
   
     @SequenceGenerator(name="generator", sequenceName="CUSTOMER_NBR_SEQ")@Id @GeneratedValue(strategy=SEQUENCE, generator="generator")
    
    @Column(name="CUSTOMER_ID", nullable=false, precision=9, scale=0)
    public Integer getCustomerId() {
        return this.customerId;
    }
    
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="INSIDE_SALESPERSON_ID", nullable=false)
    public Salesperson getSalesperson() {
        return this.salesperson;
    }
    
    public void setSalesperson(Salesperson salesperson) {
        this.salesperson = salesperson;
    }
    
    @Column(name="CUSTOMER_STATUS", length=1)
    public String getCustomerStatus() {
        return this.customerStatus;
    }
    
    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }
    
    @Column(name="NAME", length=30)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="ADDR_1", length=30)
    public String getAddr1() {
        return this.addr1;
    }
    
    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }
    
    @Column(name="ADDR_2", length=30)
    public String getAddr2() {
        return this.addr2;
    }
    
    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }
    
    @Column(name="CITY", length=25)
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    @Column(name="STATE", length=2)
    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    @Column(name="ZIP_CD", length=10)
    public String getZipCd() {
        return this.zipCd;
    }
    
    public void setZipCd(String zipCd) {
        this.zipCd = zipCd;
    }
    
    @Column(name="OUTSIDE_SALESPERSON_ID", precision=9, scale=0)
    public Integer getOutsideSalespersonId() {
        return this.outsideSalespersonId;
    }
    
    public void setOutsideSalespersonId(Integer outsideSalespersonId) {
        this.outsideSalespersonId = outsideSalespersonId;
    }
@OneToMany(fetch=FetchType.LAZY, mappedBy="customer")
    public Set<Sale> getSales() {
        return this.sales;
    }
    
    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }




}


