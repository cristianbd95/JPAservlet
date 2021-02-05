package factoria2;

import java.sql.Date;

public class Vendedor {
    private int idVendedor;
    private String nombre;
    private int edad;
    private int idOficina;
    private String titulo;
    private Date contrato;
    private double cuota;
    private double ventas;
    private int idVendedorJefe;

    public Vendedor() {
    }

    public Vendedor(int idVendedor, String nombre, int edad, int idOficina, String titulo, Date contrato, double cuota, double ventas, int idVendedorJefe) {
        this.idVendedor = idVendedor;
        this.nombre = nombre;
        this.edad = edad;
        this.idOficina = idOficina;
        this.titulo = titulo;
        this.contrato = contrato;
        this.cuota = cuota;
        this.ventas = ventas;
        this.idVendedorJefe = idVendedorJefe;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
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

    public int getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(int idOficina) {
        this.idOficina = idOficina;
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

    public double getCuota() {
        return cuota;
    }

    public void setCuota(double cuota) {
        this.cuota = cuota;
    }

    public double getVentas() {
        return ventas;
    }

    public void setVentas(double ventas) {
        this.ventas = ventas;
    }

    public int getIdVendedorJefe() {
        return idVendedorJefe;
    }

    public void setIdVendedorJefe(int idVendedorJefe) {
        this.idVendedorJefe = idVendedorJefe;
    }

    @Override
    public String toString() {
        return "Vendedor{" + "idVendedor=" + idVendedor + ", nombre=" + nombre + ", edad=" + edad + ", idOficina=" + idOficina + ", titulo=" + titulo + ", contrato=" + contrato + ", cuota=" + cuota + ", ventas=" + ventas + ", idVendedorJefe=" + idVendedorJefe + '}';
    }
    
}
