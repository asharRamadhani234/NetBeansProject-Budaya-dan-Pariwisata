/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package budayadanparawisata;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.sql.ResultSetMetaData;

/**
 *
 * @author Ashar
 */
public class diskusi {
    
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="pbo2_2310010295";
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_NAMA, CEK_PESAN, CEK_TIME=null;
    public boolean duplikasi=false;
    
    public diskusi(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    public void simpandiskusi01(String id, String nama, String pesan, String time){
        try {
            String sqlsimpan="insert into artikel(id, nama, pesan, time) value"
                    + " ('"+id+"', '"+nama+"', '"+pesan+"', '"+time+"')";
            String sqlcari="select*from diskusi where id='"+id+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Diskusi sudah terdaftar :D");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
     public void simpandiskusi02(String id, String nama, String pesan, String time){
        try {
            String sqlsimpan="insert into diskusi (id, nama, pesan, time)"
                    + " value (?, ?, ?, ?)";
            String sqlcari= "select*from diskusi where id=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Diskusi sudah terdaftar");
                this.duplikasi=true;
                this.CEK_NAMA=data.getString("nama");
                this.CEK_PESAN=data.getString("PESAN");
                this.CEK_TIME=data.getString("TIME");
            
            } else {
                this.duplikasi=false;
                this.CEK_NAMA=null;
                this.CEK_PESAN=null;
                this.CEK_TIME=null;
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id );
                perintah.setString(2, nama);
                perintah.setString(3, pesan);
                perintah.setString(4, time);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
     }
        
        public void ubahDiskusi(String id, String nama, String pesan, String time){
        try {
            String sqlubah="update diskusi set nama=?, pesan=?, time=?  where id=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
                perintah.setString(1, nama);
                perintah.setString(2, pesan);
                perintah.setString(3, time);
                perintah.setString(4, id);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
        
         public void hapusDiskusi(String id){
        try {
            String sqlhapus="delete from diskusi where id=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
                perintah.setString(1, id );
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
         
    public void tampilDataDiskusi(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("ID Diskusi");
            modeltabel.addColumn("Nama");
            modeltabel.addColumn("Pesan");
            modeltabel.addColumn("Time");
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }     
}
