package es.ciudadescolar.util;

public class SQL {
    protected  static final String RECUPERA_ALUMNOS = "SELECT expediente, nombre, fecha_nac FROM alumnos";

    protected static final String RECUPERA_ALUMNOS_EXP = "SELECT expediente,nombre, fecha_nac FROM alumnos WHERE expediente = ? AND nombre = ?";

    protected static final String ALTA_NUEVO_ALUMNO = "INSERT INTO alumnos(expediente, nombre, fecha_nac) values (?,?,?)";
    
    protected static final String CAMBIO_NUEVO_ALUMNO = "UPDATE alumnos SET nombre = ? WHERE expediente = ?";

    protected static final String BORRAR_ALUMNO = "DELETE FROM alumnos WHERE expediente = ?";


}