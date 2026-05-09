/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import Controller.ProviderController;
import Model.ProviderModel;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Admin
 */
public class QuanLyNhaCungCapPanel extends JPanel{
    private JTable table;
    private DefaultTableModel model;
    // Các trường nhập liệu khớp với bảng Providers
    private JTextField txtName, txtContact, txtPhone, txtEmail, txtAddress, txtLoc;
    private JButton btnLoc, btnThem, btnSua, btnXoa, btnLamMoi;

    public QuanLyNhaCungCapPanel() {
        initComponents();
        // Gọi Controller để kích hoạt sự kiện
        new ProviderController(this);
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- NORTH: Tiêu đề & Tìm kiếm ---
        JPanel pnlNorth = new JPanel(new BorderLayout(10, 10));
        pnlNorth.setOpaque(false);
        
        JLabel lblTitle = new JLabel("QUẢN LÝ NHÀ CUNG CẤP", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(41, 128, 185));
        
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        pnlFilter.setBackground(Color.WHITE);
        pnlFilter.add(new JLabel("Tìm kiếm (Tên/SĐT):"));
        txtLoc = new JTextField(20);
        btnLoc = new JButton("Tìm Kiếm");
        pnlFilter.add(txtLoc); 
        pnlFilter.add(btnLoc);
        
        pnlNorth.add(lblTitle, BorderLayout.NORTH);
        pnlNorth.add(pnlFilter, BorderLayout.SOUTH);
        add(pnlNorth, BorderLayout.NORTH);

        // --- CENTER: Bảng dữ liệu ---
        String[] cols = {"ID", "Tên NCC", "Người LH", "SĐT", "Email", "Địa chỉ"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(30);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- SOUTH: Form nhập liệu & Nút bấm ---
        JPanel pnlSouth = new JPanel(new BorderLayout(10, 10));
        pnlSouth.setOpaque(false);
        
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(new TitledBorder("Thông tin nhà cung cấp"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dòng 1
        gbc.gridx = 0; gbc.gridy = 0; pnlInput.add(new JLabel("Tên NCC:"), gbc);
        gbc.gridx = 1; txtName = new JTextField(20); pnlInput.add(txtName, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0; pnlInput.add(new JLabel("Người LH:"), gbc);
        gbc.gridx = 3; txtContact = new JTextField(20); pnlInput.add(txtContact, gbc);

        // Dòng 2
        gbc.gridx = 0; gbc.gridy = 1; pnlInput.add(new JLabel("SĐT:"), gbc);
        gbc.gridx = 1; txtPhone = new JTextField(20); pnlInput.add(txtPhone, gbc);
        
        gbc.gridx = 2; gbc.gridy = 1; pnlInput.add(new JLabel("Email:"), gbc);
        gbc.gridx = 3; txtEmail = new JTextField(20); pnlInput.add(txtEmail, gbc);

        // Dòng 3 (Địa chỉ dài hơn)
        gbc.gridx = 0; gbc.gridy = 2; pnlInput.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1; txtAddress = new JTextField(20); pnlInput.add(txtAddress, gbc);

        // Panel Nút bấm
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnThem = createBtn("Thêm", new Color(224, 255, 255));
        btnSua = createBtn("Sửa", new Color(255, 250, 205));
        btnXoa = createBtn("Xóa", new Color(255, 228, 225));
        btnLamMoi = createBtn("Làm Mới", new Color(220, 220, 220));
        
        pnlBtns.add(btnThem); pnlBtns.add(btnSua); 
        pnlBtns.add(btnXoa); pnlBtns.add(btnLamMoi);

        pnlSouth.add(pnlInput, BorderLayout.CENTER);
        pnlSouth.add(pnlBtns, BorderLayout.SOUTH);
        add(pnlSouth, BorderLayout.SOUTH);
    }

    private JButton createBtn(String t, Color c) {
        JButton b = new JButton(t); 
        b.setBackground(c); 
        b.setPreferredSize(new Dimension(100, 35)); 
        return b;
    }

    public void loadTable(List<ProviderModel> list) {
        model.setRowCount(0);
        for (ProviderModel pv : list) {
            model.addRow(new Object[]{
                pv.getProviderId(),
                pv.getProviderName(),
                pv.getContactName(),
                pv.getPhone(),
                pv.getEmail(),
                pv.getAddress()
            });
        }
    }

    public void fillForm(int row) {
        txtName.setText(table.getValueAt(row, 1).toString());
        txtContact.setText(table.getValueAt(row, 2) != null ? table.getValueAt(row, 2).toString() : "");
        txtPhone.setText(table.getValueAt(row, 3) != null ? table.getValueAt(row, 3).toString() : "");
        txtEmail.setText(table.getValueAt(row, 4) != null ? table.getValueAt(row, 4).toString() : "");
        txtAddress.setText(table.getValueAt(row, 5) != null ? table.getValueAt(row, 5).toString() : "");
    }

    public void clearForm() {
        txtName.setText(""); txtContact.setText(""); 
        txtPhone.setText(""); txtEmail.setText(""); 
        txtAddress.setText(""); txtLoc.setText("");
        table.clearSelection();
    }

    // Getters
    public JTable getTable() { return table; }
    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnLoc() { return btnLoc; }
    public JButton getBtnLamMoi() { return btnLamMoi; }
    
    public String getProviderName() { return txtName.getText(); }
    public String getContactName() { return txtContact.getText(); }
    public String getPhone() { return txtPhone.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getAddress() { return txtAddress.getText(); }
    public String getLocKeyword() { return txtLoc.getText(); }
}
