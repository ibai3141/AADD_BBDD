package es.ciudadescolar.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

    public Alumno getAlumnoExpYNombre(int exped, String nombre){

        Alumno al = null;
        PreparedStatement pstAlumno = null;
        ResultSet rstAlumno = null;
        try {
            pstAlumno = con.prepareStatement(SQL.RECUPERA_ALUMNOS_EXP);
            pstAlumno.setInt(1, exped);
            pstAlumno.setString(2, nombre);
            rstAlumno = pstAlumno.executeQuery();
            
            if (rstAlumno.next()) {

                al = new Alumno();

                al = new Alumno(rstAlumno.getInt(1), rstAlumno.getString(2),rstAlumno.getDate(3));
                LOG.debug("recuperado alumno con expediente: ["+exped +"]y nombre ["+ nombre+"]");
            }
        } catch (SQLException e) {
           LOG.error("error durante la consuslta: " + e.getMessage());
        }finally{

            try {
                if (rstAlumno != null) 
                    rstAlumno.close();
                if (pstAlumno != null)
                    pstAlumno.close();
            } catch (SQLException e) {
                LOG.error("error liberando recurrsos de la consulta parametrizada");
            }
        }

        return al;
    }


    public boolean altaAlumno(Alumno alumno){
        boolean status = false;

        PreparedStatement pstNuevoAlumno = null;

        if (con != null) {
            
            try {
                pstNuevoAlumno = con.prepareStatement(SQL.ALTA_NUEVO_ALUMNO);
                pstNuevoAlumno.setInt(1, alumno.getExpediente());
                pstNuevoAlumno.setString(2, alumno.getNombre());
                pstNuevoAlumno.setDate(3, Date.valueOf(alumno.getFecha_nac()));

                // vale para insert update delete 
                if(pstNuevoAlumno.executeUpdate() == 1){

                    LOG.debug("inserccion realizada conrectamente: "+ alumno.toString());
                    status = true;
                };
            } catch (SQLException e) {
                LOG.error("error durante el alta del alumno: "+ e.getMessage());
            }finally{
                if (pstNuevoAlumno!= null) {
                    
                    try {
                        pstNuevoAlumno.close();
                        LOG.debug("la liberacion de recursos ha ido bien");
                    } catch (Exception e) {
                        LOG.error("error liberando recurrsos de la consulta parametrizada");
                    }
                }
            }
        }


        return status;
    }

    public boolean modificarNombreAlumno(Alumno alumno){
        boolean status = false;

        PreparedStatement pstCambioAlumno = null;

        if (con != null) {
            
            try {
                pstCambioAlumno = con.prepareStatement(SQL.ALTA_NUEVO_ALUMNO);
                pstCambioAlumno.setString(1, alumno.getNombre());
                pstCambioAlumno.setInt(2, alumno.getExpediente());
                
                // vale para insert update delete 
                if(pstCambioAlumno.executeUpdate() == 1){

                    LOG.debug("actualizacion realizada conrectamente: "+ alumno.toString());
                    status = true;
                };
            } catch (SQLException e) {
                LOG.error("error durante el alta del alumno: "+ e.getMessage());
            }finally{
                if (pstCambioAlumno!= null) {
                    
                    try {
                        pstCambioAlumno.close();
                        LOG.debug("la liberacion de recursos ha ido bien");
                    } catch (Exception e) {
                        LOG.error("error liberando recurrsos de la consulta parametrizada");
                    }
                }
            }
        }


        return status;
    }


    public boolean borrarAlumno(int expediente){
        boolean status = false;

        PreparedStatement pstboradoAlumno = null;

        if (con != null) {
            
            try {
                pstboradoAlumno = con.prepareStatement(SQL.BORRAR_ALUMNO);
                pstboradoAlumno.setInt(1, expediente);
                
                // vale para insert update delete 
                if(pstboradoAlumno.executeUpdate() == 1){

                    LOG.debug("actualizacion realizada conrectamente: "+ expediente);
                    status = true;
                };
            } catch (SQLException e) {
                LOG.error("error durante borrado: "+ e.getMessage());
            }finally{
                if (pstboradoAlumno!= null) {
                    
                    try {
                        pstboradoAlumno.close();
                        LOG.debug("la liberacion de recursos ha ido bien");
                    } catch (Exception e) {
                        LOG.error("error liberando recurrsos de la consulta parametrizada");
                    }
                }
            }
        }


        return status;
    }

    // muestra el alumno por expediente
    public boolean muestraAlumnos(int expediente){

        boolean status = false;
        //se necesita para sp y func
        CallableStatement cs = null;

        if (con != null) {

            try {
                cs = con.prepareCall(SQL.INVOCION_SP_INFO_ALUMNO);

                cs.setInt(1, expediente);

                cs.execute();
                LOG.debug("invocacion al sp satisfactoria ["+ SQL.INVOCION_SP_INFO_ALUMNO +"] con el parametro :" + expediente);
                status = true;
            } catch (SQLException e) {
                LOG.error("erro durante la invocacion del sp: "+ e.getMessage());
            }finally{
                if(cs != null){
                    try {
                        cs.close();
                        LOG.debug("se ha cerrado");
                    } catch (SQLException e) {
                        LOG.error("imposible cerrar el colableStatement");
                    }
                }
            }
            
        }





        return  status;
    }

    // muestra el alumno por expediente
    public int recuperaAlumnosSP(){

        //se necesita para sp y func
        CallableStatement cs = null;

        int numeroAlumnos = -1;

        if (con != null) {

            try {
                cs = con.prepareCall(SQL.INVOCION_SP_GET_NUM_ALUMNOS);

                //registramos el parametro como de SALIDA para luego porder recuperar su valor tras la ejecucion
                cs.registerOutParameter(1, Types.INTEGER);

                cs.execute();
                
                numeroAlumnos = cs.getInt(1);
                LOG.debug("invocacion al sp satisfactoria ["+ SQL.INVOCION_SP_GET_NUM_ALUMNOS +"] con el parametro  de salida : "+ numeroAlumnos );
            } catch (SQLException e) {
                LOG.error("erro durante la invocacion del sp: "+ e.getMessage());
            }finally{
                if(cs != null){
                    try {
                        cs.close();
                        LOG.debug("se ha cerrado");
                    } catch (SQLException e) {
                        LOG.error("imposible cerrar el colableStatement");
                    }
                }
            }
            
        }

        return  numeroAlumnos;
    }

    public int getNotaAlumno(int exp){
        int nota = -1;

        CallableStatement csFun = null;

        if (con != null) {

            try {
                //se añade la consulta
                csFun = con.prepareCall(SQL.INVOCION_FUNC_GET_NOTA_ALUMNO);
                // se registra los parametros de salida
                csFun.registerOutParameter(1, Types.INTEGER);
                // se asigna el parametro de entrada
                csFun.setInt(2, exp);
                // se ejecuta la query
                csFun.execute();

                // se añade la nota tras la ejecucion
                nota = csFun.getInt(1);

                LOG.debug("relaizada correctamente la funcion nota :"+ nota+" para el alumno con expediente: "+ exp);

            } catch (SQLException e) {
                LOG.error("erro durante la unvocacion a funcion de la nota alumno: "+ exp+"]: "+ e.getMessage());
            }finally{
                if(csFun != null){
                    try {
                        csFun.close();
                        LOG.debug("la liberaciond de recursos en la invocacion de la funcion ha sido satisfactoria");
                    } catch (SQLException e) {
                        LOG.error("error durante la liberacion de recursos: " + e.getMessage());
                    }
                }
            }
            
        }


        return nota;
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



    public boolean altaAlumnosTransac(List<Alumno> alumnosNuevos){

        boolean status = true;

        PreparedStatement pstAltaAlumno = null;

        if (con != null) {

            try {
                pstAltaAlumno = con.prepareStatement(SQL.ALTA_NUEVO_ALUMNO);
                con.setAutoCommit(false);//desde aqui soy responsable de hacer commit o rollback

                for(Alumno al : alumnosNuevos){

                    pstAltaAlumno.setInt(1, al.getExpediente());
                    pstAltaAlumno.setString(2, al.getNombre());
                    pstAltaAlumno.setDate(3, Date.valueOf(al.getFecha_nac()));

                    pstAltaAlumno.executeUpdate();//hace inser, update, del
                    pstAltaAlumno.clearParameters();// si se queda algun valor de la iteracion anterior, se empieza de 0
                    //como queremos reutilizar nos aseguramos que se borren parametros de iteracion previa
                }
                con.commit();
                LOG.debug("se han confirmado todos los cambios");



            } catch (SQLException e) {
                LOG.error("erroo duarante el alta de alumnos de forma transacional");
                try {
                    con.rollback();
                    LOG.debug("rollback realizado correctamente");
                } catch (SQLException e1) {
                    LOG.error("error haciendo rollback durante la transaccion: "+ e.getMessage());
                }
            }finally{
                if(pstAltaAlumno != null){
                    try {
                        pstAltaAlumno.close();
                        con.setAutoCommit(true);// se restaura autocomit para que bajo la misma sesion cualquier alta baja o modi haga comit automaticamente
                    } catch (SQLException e) {
                       LOG.error("erro en la liberacion de recursos en el alta transaccional");
                    }
                    LOG.debug("liberacion de recuros de forma transacionlas");
                }
            }
        }


        return status;
    }
}