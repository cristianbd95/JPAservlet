
package factoria2;


public class Consulta2 {
    int idPedido;
    double importe;
    String nombre;
    String empresa;

    public Consulta2() {
    }

    public Consulta2(int idPedido, double importe, String nombre, String empresa) {
        this.idPedido = idPedido;
        this.importe = importe;
        this.nombre = nombre;
        this.empresa = empresa;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return "Consulta2{" + "idPedido=" + idPedido + ", importe=" + importe + ", nombre=" + nombre + ", empresa=" + empresa + '}';
    }
    
    
}
