/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rss
 */
@Entity
@Table(name = "property")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Property.findAll", query = "SELECT p FROM Property p")
    , @NamedQuery(name = "Property.findById", query = "SELECT p FROM Property p WHERE p.id = :id")
    , @NamedQuery(name = "Property.findByToLet", query = "SELECT p FROM Property p WHERE p.toLet = :toLet")
    , @NamedQuery(name = "Property.findByPrice", query = "SELECT p FROM Property p WHERE p.price = :price")
    , @NamedQuery(name = "Property.findByBedrooms", query = "SELECT p FROM Property p WHERE p.bedrooms = :bedrooms")
    , @NamedQuery(name = "Property.findByBathrooms", query = "SELECT p FROM Property p WHERE p.bathrooms = :bathrooms")
    , @NamedQuery(name = "Property.findByGarages", query = "SELECT p FROM Property p WHERE p.garages = :garages")
    , @NamedQuery(name = "Property.findByShortDescription", query = "SELECT p FROM Property p WHERE p.shortDescription = :shortDescription")
    , @NamedQuery(name = "Property.findByLongDescription", query = "SELECT p FROM Property p WHERE p.longDescription = :longDescription")
    , @NamedQuery(name = "Property.findByThumbnailRef", query = "SELECT p FROM Property p WHERE p.thumbnailRef = :thumbnailRef")
    , @NamedQuery(name = "Property.findByLocation", query = "SELECT p FROM Property p WHERE p.location = :location")})
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "toLet")
    private boolean toLet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private long price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bedrooms")
    private int bedrooms;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bathrooms")
    private int bathrooms;
    @Basic(optional = false)
    @NotNull
    @Column(name = "garages")
    private int garages;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "shortDescription")
    private String shortDescription;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "longDescription")
    private String longDescription;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "thumbnailRef")
    private String thumbnailRef;
    @Size(max = 200)
    @Column(name = "location")
    private String location;

    public Property() {
    }

    public Property(Integer id) {
        this.id = id;
    }

    public Property(Integer id, boolean toLet, long price, int bedrooms, int bathrooms, int garages, String shortDescription, String longDescription, String thumbnailRef) {
        this.id = id;
        this.toLet = toLet;
        this.price = price;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.garages = garages;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.thumbnailRef = thumbnailRef;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getToLet() {
        return toLet;
    }

    public void setToLet(boolean toLet) {
        this.toLet = toLet;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public int getGarages() {
        return garages;
    }

    public void setGarages(int garages) {
        this.garages = garages;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getThumbnailRef() {
        return thumbnailRef;
    }

    public void setThumbnailRef(String thumbnailRef) {
        this.thumbnailRef = thumbnailRef;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Property)) {
            return false;
        }
        Property other = (Property) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Property[ id=" + id + " ]";
    }
    
}
