/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Campus FP
 */
@Embeddable
public class ProductoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IdFabrica")
    private String idFabrica;
    @Basic(optional = false)
    @Column(name = "IdProducto")
    private String idProducto;

    public ProductoPK() {
    }

    public ProductoPK(String idFabrica, String idProducto) {
        this.idFabrica = idFabrica;
        this.idProducto = idProducto;
    }

    public String getIdFabrica() {
        return idFabrica;
    }

    public void setIdFabrica(String idFabrica) {
        this.idFabrica = idFabrica;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFabrica != null ? idFabrica.hashCode() : 0);
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoPK)) {
            return false;
        }
        ProductoPK other = (ProductoPK) object;
        if ((this.idFabrica == null && other.idFabrica != null) || (this.idFabrica != null && !this.idFabrica.equals(other.idFabrica))) {
            return false;
        }
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ProductoPK[ idFabrica=" + idFabrica + ", idProducto=" + idProducto + " ]";
    }
    
}
