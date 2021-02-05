/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Campus FP
 */
@Entity
@Table(name = "vendedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vendedor.findAll", query = "SELECT v FROM Vendedor v")
    , @NamedQuery(name = "Vendedor.findByIdVendedor", query = "SELECT v FROM Vendedor v WHERE v.idVendedor = :idVendedor")
    , @NamedQuery(name = "Vendedor.findByNombre", query = "SELECT v FROM Vendedor v WHERE v.nombre = :nombre")
    , @NamedQuery(name = "Vendedor.findByEdad", query = "SELECT v FROM Vendedor v WHERE v.edad = :edad")
    , @NamedQuery(name = "Vendedor.findByTitulo", query = "SELECT v FROM Vendedor v WHERE v.titulo = :titulo")
    , @NamedQuery(name = "Vendedor.findByContrato", query = "SELECT v FROM Vendedor v WHERE v.contrato = :contrato")
    , @NamedQuery(name = "Vendedor.findByCuota", query = "SELECT v FROM Vendedor v WHERE v.cuota = :cuota")
    , @NamedQuery(name = "Vendedor.findByVentas", query = "SELECT v FROM Vendedor v WHERE v.ventas = :ventas")
    , @NamedQuery(name = "Vendedor.findByIdVendedorJefe", query = "SELECT v FROM Vendedor v WHERE v.idVendedorJefe = :idVendedorJefe")})
public class Vendedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdVendedor")
    private Integer idVendedor;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Edad")
    private int edad;
    @Basic(optional = false)
    @Column(name = "Titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "Contrato")
    @Temporal(TemporalType.DATE)
    private Date contrato;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Cuota")
    private Double cuota;
    @Basic(optional = false)
    @Column(name = "Ventas")
    private double ventas;
    @Column(name = "IdVendedorJefe")
    private Integer idVendedorJefe;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVendedor")
    private List<Cliente> clienteList;
    @JoinColumn(name = "IdOficina", referencedColumnName = "IdOficina")
    @ManyToOne
    private Oficina idOficina;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVendedor")
    private List<Pedido> pedidoList;

    public Vendedor() {
    }

    public Vendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Vendedor(Integer idVendedor, String nombre, int edad, String titulo, Date contrato, double ventas) {
        this.idVendedor = idVendedor;
        this.nombre = nombre;
        this.edad = edad;
        this.titulo = titulo;
        this.contrato = contrato;
        this.ventas = ventas;
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getContrato() {
        return contrato;
    }

    public void setContrato(Date contrato) {
        this.contrato = contrato;
    }

    public Double getCuota() {
        return cuota;
    }

    public void setCuota(Double cuota) {
        this.cuota = cuota;
    }

    public double getVentas() {
        return ventas;
    }

    public void setVentas(double ventas) {
        this.ventas = ventas;
    }

    public Integer getIdVendedorJefe() {
        return idVendedorJefe;
    }

    public void setIdVendedorJefe(Integer idVendedorJefe) {
        this.idVendedorJefe = idVendedorJefe;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public Oficina getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(Oficina idOficina) {
        this.idOficina = idOficina;
    }

    @XmlTransient
    public List<Pedido> getPedidoList() {
        return pedidoList;
    }

    public void setPedidoList(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVendedor != null ? idVendedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vendedor)) {
            return false;
        }
        Vendedor other = (Vendedor) object;
        if ((this.idVendedor == null && other.idVendedor != null) || (this.idVendedor != null && !this.idVendedor.equals(other.idVendedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Vendedor{" + "idVendedor=" + idVendedor + ", nombre=" + nombre + ", edad=" + edad + ", titulo=" + titulo + ", contrato=" + contrato + ", cuota=" + cuota + ", ventas=" + ventas + ", idVendedorJefe=" + idVendedorJefe + ", clienteList=" + clienteList + ", idOficina=" + idOficina + ", pedidoList=" + pedidoList + '}';
    }


}
