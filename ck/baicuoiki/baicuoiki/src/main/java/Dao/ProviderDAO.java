/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import com.mycompany.baicuoiki.ConnectDB;
import Model.ProviderModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Admin
 */
public class ProviderDAO {
    public List<ProviderModel> getAll() {
        List<ProviderModel> list = new ArrayList<>();
        String sql = "SELECT * FROM providers ORDER BY provider_id DESC";
        try (Connection c = ConnectDB.getConnection(); 
             PreparedStatement p = c.prepareStatement(sql)) {
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                list.add(new ProviderModel(
                    rs.getInt("provider_id"),
                    rs.getString("provider_name"),
                    rs.getString("contact_name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(ProviderModel pv) {
        String sql = "INSERT INTO providers(provider_name, contact_name, phone, email, address) VALUES(?,?,?,?,?)";
        try (Connection c = ConnectDB.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, pv.getProviderName());
            p.setString(2, pv.getContactName());
            p.setString(3, pv.getPhone());
            p.setString(4, pv.getEmail());
            p.setString(5, pv.getAddress());
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean update(ProviderModel pv) {
        String sql = "UPDATE providers SET provider_name=?, contact_name=?, phone=?, email=?, address=? WHERE provider_id=?";
        try (Connection c = ConnectDB.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, pv.getProviderName());
            p.setString(2, pv.getContactName());
            p.setString(3, pv.getPhone());
            p.setString(4, pv.getEmail());
            p.setString(5, pv.getAddress());
            p.setInt(6, pv.getProviderId());
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        // Lưu ý: Nếu nhà cung cấp đã có phiếu nhập (inventoryreceipts), việc xóa có thể bị lỗi do ràng buộc khóa ngoại.
        // Bạn nên xử lý try-catch để thông báo.
        String sql = "DELETE FROM providers WHERE provider_id=?";
        try (Connection c = ConnectDB.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public List<ProviderModel> search(String keyword) {
        List<ProviderModel> list = new ArrayList<>();
        String sql = "SELECT * FROM providers WHERE provider_name LIKE ? OR phone LIKE ?";
        try (Connection c = ConnectDB.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            String key = "%" + keyword + "%";
            p.setString(1, key);
            p.setString(2, key);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                list.add(new ProviderModel(
                    rs.getInt("provider_id"),
                    rs.getString("provider_name"),
                    rs.getString("contact_name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
