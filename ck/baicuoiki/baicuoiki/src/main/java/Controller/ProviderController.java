/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dao.ProviderDAO;
import Model.ProviderModel;
import View.QuanLyNhaCungCapPanel;
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
public class ProviderController {
    private QuanLyNhaCungCapPanel view;
    private ProviderDAO dao;
    private List<ProviderModel> listCurrent;

    public ProviderController(QuanLyNhaCungCapPanel view) {
        this.view = view;
        this.dao = new ProviderDAO();
        loadData();

        // 1. Click bảng -> hiện thông tin lên form
        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTable().getSelectedRow();
                if (row != -1) view.fillForm(row);
            }
        });

        // 2. Thêm mới (Thay thế e-> bằng new ActionListener)
        view.getBtnThem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    ProviderModel pv = getForm();
                    if (dao.insert(pv)) {
                        JOptionPane.showMessageDialog(view, "Thêm nhà cung cấp thành công!");
                        view.clearForm();
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(view, "Thêm thất bại!");
                    }
                }
            }
        });

        // 3. Cập nhật (Sửa)
        view.getBtnSua().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = view.getTable().getSelectedRow();
                if (row != -1 && validateForm()) {
                    ProviderModel pv = getForm();
                    // Lấy ID từ dòng đang chọn trong list
                    pv.setProviderId(listCurrent.get(row).getProviderId());
                    
                    if (dao.update(pv)) {
                        JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn nhà cung cấp cần sửa!");
                }
            }
        });

        // 4. Xóa
        view.getBtnXoa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = view.getTable().getSelectedRow();
                if (row != -1) {
                    int id = listCurrent.get(row).getProviderId();
                    int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (dao.delete(id)) {
                            JOptionPane.showMessageDialog(view, "Đã xóa!");
                            view.clearForm();
                            loadData();
                        } else {
                            JOptionPane.showMessageDialog(view, "Xóa thất bại! (Có thể do NCC này đã có giao dịch)");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng cần xóa!");
                }
            }
        });

        // 5. Tìm kiếm
        view.getBtnLoc().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tuKhoa = view.getLocKeyword().trim();
                listCurrent = dao.search(tuKhoa);
                view.loadTable(listCurrent);
                if (listCurrent.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Không tìm thấy!");
                }
            }
        });

        // 6. Làm mới
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

    private boolean validateForm() {
        if (view.getProviderName().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tên Nhà Cung Cấp không được để trống!");
            return false;
        }
        return true;
    }

    private ProviderModel getForm() {
        ProviderModel pv = new ProviderModel();
        pv.setProviderName(view.getProviderName().trim());
        pv.setContactName(view.getContactName().trim());
        pv.setPhone(view.getPhone().trim());
        pv.setEmail(view.getEmail().trim());
        pv.setAddress(view.getAddress().trim());
        return pv;
    }
}