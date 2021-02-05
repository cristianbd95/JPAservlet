
package factoria2;


public class Consulta1 {
    int idVendedor;
    String vendedor;
    String ciudad;
    String region;

    public Consulta1() {
    }

    public Consulta1(int idVendedor, String vendedor, String ciudad, String region) {
        this.idVendedor = idVendedor;
        this.vendedor = vendedor;
        this.ciudad = ciudad;
        this.region = region;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Consulta1{" + "idVendedor=" + idVendedor + ", vendedor=" + vendedor + ", ciudad=" + ciudad + ", region=" + region + '}';
    }
    
    
}
