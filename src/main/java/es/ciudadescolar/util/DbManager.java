package es.ciudadescolar.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.instituto.Alumno;

public class DbManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(DbManager.class);
    private static final String DRIVER = "driver"; 
    private static final String USER = "user"; 
    private static final String URL = "url";
    private static final String PASS = "password"; 

    


    private Connection con = null;

    public DbManager(){

        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("conexionBD.properties"));
        
            Class.forName(prop.getProperty(DRIVER));
            //con = DriverManager.getConnection("jdbc:mysql://192.168.203.77:3306/dam2_2425", "dam2", "dam2");
            con = DriverManager.getConnection(prop.getProperty(URL),prop.getProperty(USER), prop.getProperty(PASS));
            LOG.debug("abierta satisfactoriamente");
        }catch (IOException e) {
            LOG.error("imposible cargar propiedades de la conexion");
        } catch (ClassNotFoundException e) {
            LOG.error("registro de driver con error:" + e.getMessage());   
        } catch (SQLException e) {
            LOG.error("imposible establecer la conexion con la BD:"+ e.getMessage());
        }
    }


    public List<Alumno> mostarAlumnos(){

        List<Alumno> listaAlumnos= new ArrayList<>();



        Statement stAlumnos = null;

        ResultSet rstAlmunos = null;
        Alumno alumno = null;

        if(con != null){

            try {
                stAlumnos = con.createStatement();
                rstAlmunos = stAlumnos.executeQuery(SQL.RECUPERA_ALUMNOS);

                if(rstAlmunos.next()){

                    do { 
                        alumno = new Alumno();
                        alumno.setExpediente(Integer.valueOf(rstAlmunos.getInt(1)));
                        alumno.setNombre(rstAlmunos.getString(2));
                        if (rstAlmunos.getDate(3) != null) {
                            alumno.setFecha_nac(rstAlmunos.getDate(3).toLocalDate());

                        }
                        listaAlumnos.add(alumno);
                    } while (rstAlmunos.next());

                }
                LOG.debug("se ha ejecutado la select");
            } catch (SQLException e) {

                LOG.error("imposible consultar alumnos "+ e.getMessage());
            }
            finally{

                try {
                    if(rstAlmunos != null){
                    rstAlmunos.close();

                    }
                    if(stAlumnos != null){
                        stAlumnos.close();
                    }


                } catch (SQLException e) {
                    LOG.error("error durante el cierre"+ e.getMessage());
                }
            }
        }

        return  listaAlumnos;

    }

    public boolean cerrar_bd(){

        boolean status = false;

        if(con != null){
            try {
                con.close();
                LOG.debug("cerrada satisfactoriamente");
                status = true;
            } catch (SQLException e) {
                LOG.error("error cerrando la conexion"+ e.getMessage());
            }
        }

        return status;
    }
}
