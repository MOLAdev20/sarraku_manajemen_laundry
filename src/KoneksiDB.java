/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.*;
/**
 *
 * @author Sabil
 */
public class KoneksiDB {
    public static final String URL = "jdbc:mariadb://localhost:3306/sarraku";
    public static final String USER = "root";
    public static final String PASS = "admin@24434";
    
    
    public static Connection getConnection(){
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Koneksi ke Database Berhasil!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Koneksi Gagal: " + e.getMessage());
            return null;
        }
    }
    
    public static List<Map<String, Object>> runSelectQuery(String sql) {
        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = meta.getColumnLabel(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                result.add(row);
            }

        } catch (SQLException e) {
            System.err.println("Query error: " + e.getMessage());
        }
        return result;
    }
    
    public static boolean runModifiedDataQuery(String sql) {
        try {

            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            // Buat statement dan eksekusi
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(sql);

            return result > 0;

        } catch (SQLException e) {
            return false;
        }
    }
}
