/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dao.LaptopDAO;
import Model.Laptop;
import View.QuanLyKhoPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class LaptopController {
    private QuanLyKhoPanel view;
    private LaptopDAO dao;
    private List<Laptop> listCurrent;

    public LaptopController(QuanLyKhoPanel view) {
        this.view = view;
        this.dao = new LaptopDAO();
        
        // 1. Tải danh mục vào ComboBox trước
        view.loadCategories(dao.getCategories());
        
        // 2. Tải danh sách laptop
        loadData();

        // Sự kiện Click bảng (Giữ nguyên MouseAdapter vì nó là Class sẵn)
        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTable().getSelectedRow();
                if(row != -1) view.fillForm(row);
            }
        });

        // Nút Thêm (Thay thế e-> bằng new ActionListener)
        view.getBtnThem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Laptop l = view.getFormData();
                    if(dao.insert(l)) {
                        JOptionPane.showMessageDialog(view, "Nhập kho thành công!");
                        loadData();
                        view.clearForm();
                    } else {
                        JOptionPane.showMessageDialog(view, "Thất bại!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Lỗi nhập liệu: " + ex.getMessage());
                }
            }
        });
        
        // Nút Sửa
        view.getBtnSua().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = view.getTable().getSelectedRow();
                if(row == -1) {
                     JOptionPane.showMessageDialog(view, "Chọn dòng cần sửa!");
                     return;
                }
                try {
                    Laptop l = view.getFormData();
                    l.setLaptopId(listCurrent.get(row).getLaptopId()); // Lấy ID cũ
                    
                    if(dao.update(l)) {
                        JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                        loadData();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage());
                }
            }
        });
        
        // Nút Xóa
        view.getBtnXoa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 int row = view.getTable().getSelectedRow();
                if(row != -1) {
                    int id = listCurrent.get(row).getLaptopId();
                    if(JOptionPane.showConfirmDialog(view, "Bạn muốn xóa sản phẩm này?") == 0) {
                        if(dao.delete(id)) loadData();
                    }
                }
            }
        });
        
        // --- SỰ KIỆN TÌM KIẾM ---
        view.getBtnSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = view.getSearchKeyword().trim();
                if (keyword.isEmpty()) {
                    // Nếu ô tìm kiếm rỗng thì tải lại toàn bộ
                    loadData(); 
                } else {
                    // Nếu có chữ thì tìm trong DB
                    listCurrent = dao.search(keyword);
                    view.loadTable(listCurrent);
                    
                    if (listCurrent.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "Không tìm thấy sản phẩm nào!");
                    }
                }
            }
        });
        
        // Nút Làm mới
        view.getBtnLamMoi().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.clearForm();
                loadData();
            }
        });
    }

    private void loadData() {
        listCurrent = dao.getAll();
        view.loadTable(listCurrent);
    }
}