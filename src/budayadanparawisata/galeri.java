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
public class galeri {
    
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="pbo2_2310010295";
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_JENIS, CEK_TEMPAT, CEK_FOTO=null;
    public boolean duplikasi=false;
    
    public galeri(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    public void simpangaleri01(String id, String jenis, String tempat, String foto){
        try {
            String sqlsimpan="insert into galeri(id, jenis_wisata, nama_tempat, foto) value"
                    + " ('"+id+"', '"+jenis+"', '"+tempat+"', '"+foto+"')";
            String sqlcari="select*from galeri where id='"+id+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Galeri sudah terdaftar :D");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
     public void simpangaleri02(String id, String jenis, String tempat, String foto){
        try {
            String sqlsimpan="insert into galeri(id, jenis_wisata, nama_tempat, foto)"
                    + " value (?, ?, ?, ?)";
            String sqlcari= "select*from galeri where id=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Galeri sudah terdaftar");
                this.duplikasi=true;
                this.CEK_JENIS=data.getString("jenis_wisata");
                this.CEK_TEMPAT=data.getString("nama_tempat");
                this.CEK_FOTO=data.getString("foto");
            } else {
                this.duplikasi=false;
                this.CEK_JENIS=null;
                this.CEK_TEMPAT=null;
                this.CEK_FOTO=null;
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id );
                perintah.setString(2, jenis);
                perintah.setString(3, tempat);
                perintah.setString(4, foto);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
     }
        
        public void ubahGaleri(String id, String jenis, String tempat, String foto){
        try {
            String sqlubah="update galeri set judul=?, keterangan=?, foto=?, peta=?, jenis_wisata=? where id=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
                perintah.setString(1, jenis);
                perintah.setString(2, tempat);
                perintah.setString(3, foto);
                perintah.setString(4, id);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
        
         public void hapusGaleri(String id){
        try {
            String sqlhapus="delete from galeri where id=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
                perintah.setString(1, id );
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
         
    public void tampilDataGaleri(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("ID Galeri");
            modeltabel.addColumn("Jenis Wisata");
            modeltabel.addColumn("Nama Tempat");
            modeltabel.addColumn("Foto");
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
