package es.ciudadescolar.instituto;

import java.sql.Date;
import java.time.LocalDate;

public class Alumno {
    
    private Integer expediente;
    private String nombre;
    private LocalDate fecha_nac;

    public Alumno(){

    }

    public Alumno(int exp, String nombre, Date fecha){
        this.setExpediente(Integer.valueOf(exp));
        this.setNombre(nombre);
        this.setFecha_nac(fecha.toLocalDate());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expediente == null) ? 0 : expediente.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((fecha_nac == null) ? 0 : fecha_nac.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Alumno other = (Alumno) obj;
        if (expediente == null) {
            if (other.expediente != null)
                return false;
        } else if (!expediente.equals(other.expediente))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (fecha_nac == null) {
            if (other.fecha_nac != null)
                return false;
        } else if (!fecha_nac.equals(other.fecha_nac))
            return false;
        return true;
    }
    public Integer getExpediente() {
        return expediente;
    }
    public void setExpediente(Integer expediente) {
        this.expediente = expediente;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public LocalDate getFecha_nac() {
        return fecha_nac;
    }
    public void setFecha_nac(LocalDate fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    @Override
    public String toString() {
        return "Alumno [expediente=" + expediente + ", nombre=" + nombre + ", fecha_nac=" + fecha_nac + "]";
    }

    
    

}
