/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mycompany.baicuoiki.ConnectDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Laptop3;

public class Laptopdao1 {

    // ================= CATEGORY =================
    public List<Category> getCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("description")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================= LAPTOP TABLE =================
    public List<Object[]> getAllForTable() {
        String sql = """
                SELECT l.*, c.category_name
                FROM laptops l
                LEFT JOIN categories c ON l.category_id = c.category_id
                WHERE l.status = 1
                """;
        return getBySql(sql);
    }

    public List<Object[]> getByFilter(int categoryId) {
        String sql = """
                SELECT l.*, c.category_name
                FROM laptops l
                LEFT JOIN categories c ON l.category_id = c.category_id
                WHERE l.status = 1 AND l.category_id = ?
                """;

        List<Object[]> list = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<Object[]> getBySql(String sql) {
        List<Object[]> list = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Object[] mapRow(ResultSet rs) throws SQLException {
        return new Object[]{
                rs.getInt("laptop_id"),
                rs.getString("laptop_name"),
                rs.getString("category_name"),
                rs.getString("cpu_info"),
                rs.getString("ram_info"),
                rs.getString("ssd_info"),
                rs.getDouble("price"),
                rs.getInt("stock_quantity"),
                rs.getString("image_url")
        };
    }

    // ================= CRUD =================
    public boolean insert(Laptop3 l) {
        String sql = """
                INSERT INTO laptops
                (category_id, laptop_name, cpu_info, ram_info, ssd_info, price, stock_quantity, image_url, status)
                VALUES (?,?,?,?,?,?,?,?,1)
                """;
        return executeUpdate(sql, l, false);
    }

    public boolean update(Laptop3 l) {
        String sql = """
                UPDATE laptops
                SET category_id=?, laptop_name=?, cpu_info=?, ram_info=?, ssd_info=?, price=?, stock_quantity=?, image_url=?
                WHERE laptop_id=?
                """;
        return executeUpdate(sql, l, true);
    }

    public boolean delete(int id) {
        String sql = "UPDATE laptops SET status = 0 WHERE laptop_id = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean executeUpdate(String sql, Laptop3 l, boolean isUpdate) {
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, l.getCategoryId());
            ps.setString(2, l.getLaptopName());
            ps.setString(3, l.getCpuInfo());
            ps.setString(4, l.getRamInfo());
            ps.setString(5, l.getSsdInfo());
            ps.setDouble(6, l.getPrice());
            ps.setInt(7, l.getStockQuantity());
            ps.setString(8, l.getImageUrl());

            if (isUpdate) {
                ps.setInt(9, l.getLaptopId());
            }
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
