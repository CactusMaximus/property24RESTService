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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rss
 */
@Entity
@Table(name = "userProperty")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserProperty.findAll", query = "SELECT u FROM UserProperty u")
    , @NamedQuery(name = "UserProperty.findById", query = "SELECT u FROM UserProperty u WHERE u.id = :id")
    , @NamedQuery(name = "UserProperty.findByPropertyId", query = "SELECT u FROM UserProperty u WHERE u.propertyId = :propertyId")
    , @NamedQuery(name = "UserProperty.findByUserId", query = "SELECT u FROM UserProperty u WHERE u.userId = :userId")})
public class UserProperty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "propertyId")
    private int propertyId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "userId")
    private int userId;

    public UserProperty() {
    }

    public UserProperty(Integer id) {
        this.id = id;
    }

    public UserProperty(Integer id, int propertyId, int userId) {
        this.id = id;
        this.propertyId = propertyId;
        this.userId = userId;
    }
    
      public UserProperty(String propertyId, String userId) {
        this.propertyId = Integer.valueOf(propertyId);
        this.userId = Integer.valueOf(userId);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
        if (!(object instanceof UserProperty)) {
            return false;
        }
        UserProperty other = (UserProperty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.UserProperty[ id=" + id + " ]";
    }
    
}
