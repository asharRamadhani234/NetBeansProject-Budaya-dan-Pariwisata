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
public class artikel {
    
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="pbo2_2310010295";
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_JUDUL, CEK_KETERANGAN, CEK_FOTO, CEK_PETA, CEK_WISATA=null;
    public boolean duplikasi=false;
    
    public artikel(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    public void simpanartikel01(String id, String judul, String keterangan, String foto, String peta, String wisata){
        try {
            String sqlsimpan="insert into artikel(id, judul, keterangan, foto, peta, jenis_wisata) value"
                    + " ('"+id+"', '"+judul+"', '"+keterangan+"', '"+foto+"', '"+peta+"', '"+wisata+"')";
            String sqlcari="select*from artikel where id='"+id+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Artikel sudah terdaftar :D");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
     public void simpanartikel02(String id, String judul, String keterangan, String foto, String peta, String wisata){
        try {
            String sqlsimpan="insert into artikel (id, judul, keterangan, foto, peta, jenis_wisata)"
                    + " value (?, ?, ?, ?, ?, ?)";
            String sqlcari= "select*from artikel where id=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Artikel sudah terdaftar");
                this.duplikasi=true;
                this.CEK_JUDUL=data.getString("judul");
                this.CEK_KETERANGAN=data.getString("keterangan");
                this.CEK_FOTO=data.getString("foto");
                this.CEK_PETA=data.getString("peta");
                this.CEK_WISATA=data.getString("jenis_wisata"); 
            } else {
                this.duplikasi=false;
                this.CEK_JUDUL=null;
                this.CEK_KETERANGAN=null;
                this.CEK_FOTO=null;
                this.CEK_PETA=null;
                this.CEK_WISATA=null;
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id );
                perintah.setString(2, judul);
                perintah.setString(3, keterangan);
                perintah.setString(4, foto);
                perintah.setString(5, peta);
                perintah.setString(6, wisata);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
     }
        
        public void ubahArtikel(String id, String judul, String keterangan, String foto, String peta, String wisata){
        try {
            String sqlubah="update artikel set judul=?, keterangan=?, foto=?, peta=?, jenis_wisata=? where id=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
                perintah.setString(1, judul);
                perintah.setString(2, keterangan);
                perintah.setString(3, foto);
                perintah.setString(4, peta);
                perintah.setString(5, wisata);
                perintah.setString(6, id);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
        
         public void hapusArtikel(String id){
        try {
            String sqlhapus="delete from artikel where id=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
                perintah.setString(1, id );
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
         
    public void tampilDataArtikel(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("ID Artikel");
            modeltabel.addColumn("Judul");
            modeltabel.addColumn("Keterangan");
            modeltabel.addColumn("Foto");
            modeltabel.addColumn("Peta");
            modeltabel.addColumn("Jenis Wisata");
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
