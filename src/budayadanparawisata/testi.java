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
 * @author AsusVivobook
 */
public class testi {
    
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="pbo2_2310010295";
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_NAMA, CEK_SARAN=null;
    public boolean duplikasi=false;
    
    public testi(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    public void simpantesti01(String id, String nama, String saran){
        try {
            String sqlsimpan="insert into testi(id, nama, saran) value"
                    + " ('"+id+"', '"+nama+"', '"+saran+"')";
            String sqlcari="select*from testi where id='"+id+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID testi sudah terdaftar :D");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
     public void simpantesti02(String id, String nama, String saran){
        try {
            String sqlsimpan="insert into testi(id, nama, saran)"
                    + " value (?, ?, ?)";
            String sqlcari= "select*from testi where id=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Testi sudah terdaftar");
                this.duplikasi=true;
                this.CEK_NAMA=data.getString("jenis_nama");
                this.CEK_SARAN=data.getString("nama_saran");
            } else {
                this.duplikasi=false;
                this.CEK_NAMA=null;
                this.CEK_SARAN=null;
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id );
                perintah.setString(2, nama);
                perintah.setString(3, saran);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
     }
        
        public void ubahTesti(String id, String nama, String saran){
        try {
            String sqlubah="update testi set nama=?, keterangan=?, saran=? where id=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
                perintah.setString(1, nama);
                perintah.setString(2, saran);
                perintah.setString(3, id);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
        
         public void hapusTesti(String id){
        try {
            String sqlhapus="delete from testi where id=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
                perintah.setString(1, id );
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
         
    public void tampilDataTesti(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("ID Testi");
            modeltabel.addColumn("Nama");
            modeltabel.addColumn("Saran");
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (SQLException e) {
        }
    }
     
     
    
}
