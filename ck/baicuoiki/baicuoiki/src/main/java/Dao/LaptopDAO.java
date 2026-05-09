/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import com.mycompany.baicuoiki.ConnectDB; // Nhớ import đúng file ConnectDB của bạn
import Model.Laptop;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Admin
 */
public class LaptopDAO {
    // 1. Lấy danh sách Laptop (Kèm tên danh mục)
    public List<Laptop> getAll() {
        List<Laptop> list = new ArrayList<>();
        // Kết nối bảng laptops với categories để lấy tên loại máy
        String sql = "SELECT l.*, c.category_name FROM laptops l LEFT JOIN categories c ON l.category_id = c.category_id ORDER BY l.laptop_id DESC";
        
        try (Connection c = ConnectDB.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Laptop lap = new Laptop();
                lap.setLaptopId(rs.getInt("laptop_id"));
                lap.setLaptopName(rs.getString("laptop_name"));
                lap.setCategoryId(rs.getInt("category_id"));
                lap.setCategoryName(rs.getString("category_name")); // Lấy từ bảng categories
                lap.setCpu(rs.getString("cpu_info"));
                lap.setRam(rs.getString("ram_info"));
                lap.setPrice(rs.getDouble("price"));
                lap.setQuantity(rs.getInt("stock_quantity"));
                list.add(lap);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Thêm mới
    public boolean insert(Laptop lap) {
        String sql = "INSERT INTO laptops(laptop_name, category_id, cpu_info, ram_info, price, stock_quantity) VALUES(?,?,?,?,?,?)";
        try (Connection c = ConnectDB.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, lap.getLaptopName());
            p.setInt(2, lap.getCategoryId());
            p.setString(3, lap.getCpu());
            p.setString(4, lap.getRam());
            p.setDouble(5, lap.getPrice());
            p.setInt(6, lap.getQuantity());
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 3. Sửa
    public boolean update(Laptop lap) {
        String sql = "UPDATE laptops SET laptop_name=?, category_id=?, cpu_info=?, ram_info=?, price=?, stock_quantity=? WHERE laptop_id=?";
        try (Connection c = ConnectDB.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, lap.getLaptopName());
            p.setInt(2, lap.getCategoryId());
            p.setString(3, lap.getCpu());
            p.setString(4, lap.getRam());
            p.setDouble(5, lap.getPrice());
            p.setInt(6, lap.getQuantity());
            p.setInt(7, lap.getLaptopId());
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 4. Xóa
    public boolean delete(int id) {
        String sql = "DELETE FROM laptops WHERE laptop_id=?";
        try (Connection c = ConnectDB.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 5. Lấy danh sách Danh Mục (Để đổ vào ComboBox chọn loại máy)
    public Map<String, Integer> getCategories() {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT * FROM categories";
        try (Connection c = ConnectDB.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                // Lưu Tên làm Key, ID làm Value (Ví dụ: "Dell" -> 1)
                map.put(rs.getString("category_name"), rs.getInt("category_id"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return map;
    }
    
    // 6. Tìm kiếm Laptop theo tên (Gần đúng)
    public List<Laptop> search(String keyword) {
        List<Laptop> list = new ArrayList<>();
        // Vẫn phải JOIN bảng để lấy tên danh mục hiển thị ra bảng
        String sql = "SELECT l.*, c.category_name " +
                     "FROM laptops l " +
                     "LEFT JOIN categories c ON l.category_id = c.category_id " +
                     "WHERE l.laptop_name LIKE ?";
        
        try (Connection c = ConnectDB.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, "%" + keyword + "%"); // Thêm % để tìm kiếm gần đúng
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Laptop lap = new Laptop();
                lap.setLaptopId(rs.getInt("laptop_id"));
                lap.setLaptopName(rs.getString("laptop_name"));
                lap.setCategoryId(rs.getInt("category_id"));
                lap.setCategoryName(rs.getString("category_name"));
                lap.setCpu(rs.getString("cpu_info"));
                lap.setRam(rs.getString("ram_info"));
                lap.setPrice(rs.getDouble("price"));
                lap.setQuantity(rs.getInt("stock_quantity"));
                list.add(lap);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<Object[]> getAllForTable() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
