package es.ciudadescolar.util;

public class SQL {
    protected  static final String RECUPERA_ALUMNOS = "SELECT expediente, nombre, fecha_nac FROM alumnos";

    protected static final String RECUPERA_ALUMNOS_EXP = "SELECT expediente,nombre, fecha_nac FROM alumnos WHERE expediente = ? AND nombre = ?";

    protected static final String ALTA_NUEVO_ALUMNO = "INSERT INTO alumnos(expediente, nombre, fecha_nac) values (?,?,?)";
    
    protected static final String CAMBIO_NUEVO_ALUMNO = "UPDATE alumnos SET nombre = ? WHERE expediente = ?";

    protected static final String BORRAR_ALUMNO = "DELETE FROM alumnos WHERE expediente = ?";

    //procedimientos con un parametro de entrada que sera el expediente
    protected static final String INVOCION_SP_INFO_ALUMNO = "{CALL sp_getalumno(?)}";

    //salida de la cuenta de numero de alumnos
    protected static final String INVOCION_SP_GET_NUM_ALUMNOS = "{CALL getNumAlumnos(?)}";

    // funcion que recive un parametro de entrada y es el expediente y devuelve un entero con el numero de la nota
    protected static final String INVOCION_FUNC_GET_NOTA_ALUMNO = "{? = CALL fun_getnotaalumno(?)}";



}