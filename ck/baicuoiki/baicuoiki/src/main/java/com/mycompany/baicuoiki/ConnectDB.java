/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.baicuoiki;

/**
 *
 * @author Admin
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */

public class ConnectDB {
    // Sửa tên DB thành 'cuoikijava' chuẩn theo ảnh bạn gửi
    // Thêm characterEncoding=UTF-8 để hỗ trợ tiếng Việt
    private static final String URL = "jdbc:mysql://localhost:3306/cuoikijava?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Kết nối CSDL thành công!"); // Dòng này sẽ hiện ở Output nếu kết nối OK
        } catch (ClassNotFoundException ex) {
            System.out.println("Lỗi: Không tìm thấy thư viện MySQL JDBC!");
        } catch (SQLException ex) {
            System.out.println("Lỗi kết nối: " + ex.getMessage());
        }
        return conn;
    }
    

}
