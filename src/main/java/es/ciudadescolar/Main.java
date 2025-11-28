
package es.ciudadescolar;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.instituto.Alumno;
import es.ciudadescolar.util.DbManager;
public class Main 
{
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) 
    {
        DbManager manager = new DbManager();

        List<Alumno> alumnos = manager.mostrarAlumnos();

        for (Alumno al: alumnos )
        {
            LOG.info(al.toString());
        }

        Alumno buscado = manager.getAlumnoExpYNombre(1009, "paco");

        if (buscado != null) {
            LOG.info("alumno localizado: "+ buscado);           
        }

        Alumno alumnoNuevo = new Alumno(67, "ibai", Date.valueOf(LocalDate.of(2001, 10, 03)));
        if(!manager.altaAlumno(alumnoNuevo)){
            LOG.warn("no se ha podido dar de alta el alumno: "+ alumnoNuevo.toString());
        }

        Alumno alumnox = new Alumno(67, "Francisco",Date.valueOf(LocalDate.of(2004, 10, 6)));

        alumnoNuevo.setNombre("juan");

        manager.modificarNombreAlumno(alumnoNuevo);

        //manager.borrarAlumno(67);

        manager.cerrarBd();

    }


}
