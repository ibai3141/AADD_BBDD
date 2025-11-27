package es.ciudadescolar.util;

public class SQL {
    protected  static final String RECUPERA_ALUMNOS = "SELECT expediente, nombre, fecha_nac FROM alumnos";

    protected static final String RECUPERA_ALUMNOS_EXP = "SELECT expediente,nombre, fecha_nac FROM alumnos WHERE expediente = ? AND nombre = ?";
}