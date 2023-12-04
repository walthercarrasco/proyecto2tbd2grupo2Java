/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 *
 * @author walth
 */
public class Conexion{
    public String basededato;
    public String usuario;
    public String password;
    public String instancia;
    public String puerto;
    Connection conn;

    public Conexion(String basededato, String usuario, String password, String instancia, String puerto) {
        this.basededato = basededato;
        this.usuario = usuario;
        this.password = password;
        this.instancia = instancia;
        this.puerto = puerto;
    }

    public void Conectar() {
        if(puerto.equals("3306")){
            //conexion mariadb
            try {
                String url = "jdbc:mariadb://" + instancia + ":" + puerto + "/" + basededato;
                conn = DriverManager.getConnection(url, usuario, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(puerto.equals("1433")){
            //conexion sqlserver
            try {
                String url = "jdbc:sqlserver://" + instancia + ":" + puerto +
                        ";databaseName=" + basededato
                        + ";user="+usuario
                        + ";password="+password
                        + ";encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=30;"; 
            
                conn = DriverManager.getConnection(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }	

    }
}
