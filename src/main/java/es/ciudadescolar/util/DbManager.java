package es.ciudadescolar.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.instituto.Alumno;

public class DbManager 
{
    private static final Logger LOG = LoggerFactory.getLogger(DbManager.class);
    private static final String DRIVER = "driver";
    private static final String URL = "url";
    private static final String USUARIO = "user";
    private static final String PWD = "password";

    private Connection con = null;
    
    public DbManager()
    {
        Properties prop = new Properties();

        try 
        {
            prop.load(new FileInputStream("conexionBD.properties"));   
        
            // registramos driver (en versiones actuales no es necesario)
            Class.forName(prop.getProperty(DRIVER));
            //con = DriverManager.getConnection("jdbc:mysql://192.168.203.77:3306/dam2_2425", "dam2", "dam2");
            con = DriverManager.getConnection(prop.getProperty(URL), prop.getProperty(USUARIO),prop.getProperty(PWD));
            LOG.debug("Establecida conexión satisfactoriamente");
        }
        catch (IOException e) 
        {
            LOG.error("Imposible cargar propiedades de la conexión");
        }
        catch (ClassNotFoundException e) 
        {
            LOG.error("Registro de driver con error: "+ e.getMessage());
        } catch (SQLException e) 
        {
            LOG.error("Imposible establecer conexion con la BD: "+e.getMessage());
        }
    }

    public List<Alumno> mostrarAlumnos()
    {
        List<Alumno> alumnos = null;

        Statement stAlumnos = null;

        ResultSet rstAlumno = null;

        Alumno alumno =null;

        if (con != null)
        {
            try 
            {
                stAlumnos = con.createStatement();
                rstAlumno = stAlumnos.executeQuery(SQL.RECUPERA_ALUMNOS);
                
                if (rstAlumno.next())
                {
                    alumnos = new ArrayList<Alumno>();
                    do
                    {
                        alumno = new Alumno();
                        alumno.setExpediente(Integer.valueOf(rstAlumno.getInt(1)));
                        alumno.setNombre(rstAlumno.getString(2));
                        
                        Date fecha = rstAlumno.getDate(3);
                        if (fecha != null)
                            alumno.setFecha_nac(fecha.toLocalDate());

                        alumnos.add(alumno);
                    }while(rstAlumno.next());
                }
                
                LOG.debug("Se ha ejecutado correctamente la sentencia SELECT");
            }
            catch (SQLException e) 
            {
                LOG.error("Imposible consultar alumnos: "+e.getMessage());
            }
            finally
            {
                try 
                {
                    if (rstAlumno != null) 
                        rstAlumno.close();
                    if (stAlumnos != null)
                        stAlumnos.close();
                } catch (SQLException e) 
                {
                    LOG.error("Error durante el cierre de la conexión");
                }
            }
        }
        return alumnos;
        
    }
    public boolean cerrarBd()
    {
        boolean status = false;

        if (con != null)
        {
            try 
            {
                con.close();
                LOG.debug("Cerrada conexión satisfactoriamente");
                status = true;
            } 
            catch (SQLException e) 
            {
                LOG.error("Error cerrando la conexion: "+e.getMessage());
            }
        }
        return status;
    }
}