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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Salesperson generated by hbm2java
 */
@Entity
@Table(name="SALESPERSON"
    ,schema="PUBLIC"
    ,catalog="SALES"
)
public class Salesperson  implements java.io.Serializable {


     private Integer salespersonId;
     private String displayName;
     private String firstName;
     private String lastName;
     private Set<Customer> customers = new HashSet<Customer>(0);

    public Salesperson() {
    }

    public Salesperson(String displayName, String firstName, String lastName, Set<Customer> customers) {
       this.displayName = displayName;
       this.firstName = firstName;
       this.lastName = lastName;
       this.customers = customers;
    }
   
     @SequenceGenerator(name="generator", sequenceName="SALESPERSON_NBR_SEQ")@Id @GeneratedValue(strategy=SEQUENCE, generator="generator")
    
    @Column(name="SALESPERSON_ID", nullable=false, precision=9, scale=0)
    public Integer getSalespersonId() {
        return this.salespersonId;
    }
    
    public void setSalespersonId(Integer salespersonId) {
        this.salespersonId = salespersonId;
    }
    
    @Column(name="DISPLAY_NAME", length=40)
    public String getDisplayName() {
        return this.displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    @Column(name="FIRST_NAME", length=16)
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    @Column(name="LAST_NAME", length=20)
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
@OneToMany(fetch=FetchType.LAZY, mappedBy="salesperson")
    public Set<Customer> getCustomers() {
        return this.customers;
    }
    
    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }




}


