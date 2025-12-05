
package es.ciudadescolar;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
        /* 
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
         int expediente = 67;
        if(!manager.muestraAlumnos(67)){
            LOG.warn("no se ha invocado conrrectamente el sp para mostrar el alumno con expediente: "+ expediente);
        }
             
        int numAlumnos = manager.recuperaAlumnosSP();
        if(numAlumnos < 0){
            LOG.warn("error durante el invacode del sp para recuperar los alumnos");

        }else{
            LOG.info("el numero de alumnos en la base de datos es :" + numAlumnos);
        }

        int notaAlumno = manager.getNotaAlumno(2);

        if(notaAlumno == 0){
            LOG.warn("no se ha recuperado nota  durante la invocacion de sp del alumno con el expediente " +2);

        }else{
            LOG.info("la nota del alumno es :" + notaAlumno);
        }
        manager.cerrarBd();
        */

        List<Alumno> nuevosAlumnos = new ArrayList<>();

        nuevosAlumnos.add(new Alumno(523, "juan", Date.valueOf(LocalDate.of(2000, 9, 23))));
        nuevosAlumnos.add(new Alumno(522, "jorge", Date.valueOf(LocalDate.of(2000, 9, 23))));
        nuevosAlumnos.add(new Alumno(521, "tilin insano", Date.valueOf(LocalDate.of(2000, 9, 23))));

        if(!manager.altaAlumnosTransac(nuevosAlumnos))

        manager.cerrarBd();

    }


}
