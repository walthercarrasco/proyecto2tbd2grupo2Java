/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import javax.swing.JLabel;

/**
 *
 * @author walth
 */
public class hilo extends Thread {
    
    private Conexion c;
    private JLabel label;
    
    public hilo(Conexion c, JLabel label){
        this.c = c;
        this.label = label;
    }
    
    public void run(){
        try {
            String sql = "SELECT last_executed\n" +
                "FROM information_schema.events\n" +
                "WHERE" +
                " event_name = 'replicador';";
            java.sql.ResultSet rs = c.conn.createStatement().executeQuery(sql);
            if(rs.next())
                label.setText("Ultima Ejecucion del JOB: " + rs.getString(1));
            Thread.sleep(15000); // Pausa de 1 segundo
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
}
