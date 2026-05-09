/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import Controller.LaptopController;
import Model.Laptop;
import java.awt.*;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Admin
 */
public class QuanLyKhoPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    
    // Các ô nhập liệu
    private JTextField txtName, txtCpu, txtRam, txtPrice, txtQuantity, txtSearch;
    private JComboBox<String> cboCategory; // ComboBox chọn Danh mục
    
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnSearch;
    private Map<String, Integer> categoryMap; // Lưu map danh mục để tra ID

    public QuanLyKhoPanel() {
        initComponents();
        // Gọi Controller
        new LaptopController(this);
    }

    private void initComponents() {
        // 1. CẬP NHẬT LAYOUT VÀ MÀU NỀN GIỐNG NCC
        setLayout(new BorderLayout(15, 15)); // Tăng gap từ 10 lên 15
        setBackground(new Color(245, 245, 245)); // Màu nền xám nhẹ giống NCC
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Viền dày 20

        // --- PHẦN NORTH (TIÊU ĐỀ + TÌM KIẾM) ---
        JPanel pnlNorth = new JPanel(new BorderLayout(10, 10));
        pnlNorth.setOpaque(false);

        // 1. Tiêu đề: Đổi Font và Màu cho giống NCC
        JLabel lblTitle = new JLabel("QUẢN LÝ KHO LAPTOP", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Font Segoe UI, Cỡ 28
        lblTitle.setForeground(new Color(41, 128, 185)); // Màu xanh đặc trưng
        pnlNorth.add(lblTitle, BorderLayout.NORTH);

        // 2. Thanh tìm kiếm: Cập nhật background và layout
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15)); // Thêm gap cho thoáng
        pnlSearch.setBackground(Color.WHITE); // Nền trắng giống NCC
        pnlSearch.add(new JLabel("Tìm kiếm (theo tên):"));
        txtSearch = new JTextField(20); // Tăng kích thước ô tìm kiếm lên 20
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setPreferredSize(new Dimension(100, 30)); 
        
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnSearch);
        
        pnlNorth.add(pnlSearch, BorderLayout.SOUTH);
        add(pnlNorth, BorderLayout.NORTH); 

        // --- CENTER: TABLE ---
        String[] cols = {"ID", "Tên Laptop", "Danh Mục", "CPU", "RAM", "Giá Bán", "Tồn Kho"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(30); // Tăng chiều cao dòng lên 30 giống NCC
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- SOUTH: INPUT FORM & BUTTONS ---
        JPanel pnlSouth = new JPanel(new BorderLayout(10, 10));
        pnlSouth.setOpaque(false);
        
        // Form nhập liệu
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(new TitledBorder("Thông tin sản phẩm"));
        pnlInput.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Tăng khoảng cách các ô input (giống NCC)
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Cột 1
        gbc.gridx = 0; gbc.gridy = 0; pnlInput.add(new JLabel("Tên Laptop:"), gbc);
        gbc.gridx = 1; txtName = new JTextField(20); pnlInput.add(txtName, gbc); // Tăng size lên 20
        
        gbc.gridx = 0; gbc.gridy = 1; pnlInput.add(new JLabel("Danh Mục:"), gbc);
        gbc.gridx = 1; cboCategory = new JComboBox<>(); pnlInput.add(cboCategory, gbc);

        // Cột 2
        gbc.gridx = 2; gbc.gridy = 0; pnlInput.add(new JLabel("CPU:"), gbc);
        gbc.gridx = 3; txtCpu = new JTextField(20); pnlInput.add(txtCpu, gbc);

        gbc.gridx = 2; gbc.gridy = 1; pnlInput.add(new JLabel("RAM:"), gbc);
        gbc.gridx = 3; txtRam = new JTextField(20); pnlInput.add(txtRam, gbc);

        // Cột 3
        gbc.gridx = 4; gbc.gridy = 0; pnlInput.add(new JLabel("Giá Bán (VNĐ):"), gbc);
        gbc.gridx = 5; txtPrice = new JTextField(20); pnlInput.add(txtPrice, gbc);
        
        gbc.gridx = 4; gbc.gridy = 1; pnlInput.add(new JLabel("Số Lượng:"), gbc);
        gbc.gridx = 5; txtQuantity = new JTextField(20); pnlInput.add(txtQuantity, gbc);

        // Panel Nút bấm: Sử dụng hàm createBtn để giống màu NCC
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        btnThem = createBtn("Thêm", new Color(224, 255, 255));   // Màu xanh nhạt
        btnSua = createBtn("Sửa", new Color(255, 250, 205));    // Màu vàng nhạt
        btnXoa = createBtn("Xóa", new Color(255, 228, 225));         // Màu hồng nhạt
        btnLamMoi = createBtn("Làm Mới", new Color(220, 220, 220));  // Màu xám nhạt
        
        pnlBtns.add(btnThem); 
        pnlBtns.add(btnSua); 
        pnlBtns.add(btnXoa); 
        pnlBtns.add(btnLamMoi);

        pnlSouth.add(pnlInput, BorderLayout.CENTER);
        pnlSouth.add(pnlBtns, BorderLayout.SOUTH);
        add(pnlSouth, BorderLayout.SOUTH);
    }
    
    // Hàm hỗ trợ tạo nút giống hệt bên QuanLyNhaCungCapPanel
    private JButton createBtn(String t, Color c) {
        JButton b = new JButton(t); 
        b.setBackground(c); 
        b.setPreferredSize(new Dimension(100, 35)); // Kích thước chuẩn
        return b;
    }

    // --- Các hàm logic giữ nguyên không đổi ---
    public void loadTable(List<Laptop> list) {
        model.setRowCount(0);
        for (Laptop l : list) {
            model.addRow(new Object[]{
                l.getLaptopId(), l.getLaptopName(), l.getCategoryName(),
                l.getCpu(), l.getRam(), String.format("%,.0f", l.getPrice()), l.getQuantity()
            });
        }
    }
    
    public void loadCategories(Map<String, Integer> map) {
        this.categoryMap = map;
        cboCategory.removeAllItems();
        for (String name : map.keySet()) {
            cboCategory.addItem(name);
        }
    }

    public void fillForm(int row) {
        txtName.setText(table.getValueAt(row, 1).toString());
        String catName = table.getValueAt(row, 2).toString();
        cboCategory.setSelectedItem(catName);
        
        txtCpu.setText(table.getValueAt(row, 3).toString());
        txtRam.setText(table.getValueAt(row, 4).toString());
        
        String priceStr = table.getValueAt(row, 5).toString().replace(".", "").replace(",", "");
        txtPrice.setText(priceStr);
        
        txtQuantity.setText(table.getValueAt(row, 6).toString());
    }
    
    public void clearForm() {
        txtName.setText(""); txtCpu.setText(""); txtRam.setText("");
        txtPrice.setText(""); txtQuantity.setText("");
        table.clearSelection();
    }

    // Getters
    public JTable getTable() { return table; }
    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnLamMoi() { return btnLamMoi; }
    public JButton getBtnSearch() { return btnSearch; }
    public String getSearchKeyword() { return txtSearch.getText(); }
    
    public Laptop getFormData() {
        Laptop l = new Laptop();
        l.setLaptopName(txtName.getText());
        
        String catName = (String) cboCategory.getSelectedItem();
        if(categoryMap != null && catName != null) {
            l.setCategoryId(categoryMap.get(catName));
        }
        
        l.setCpu(txtCpu.getText());
        l.setRam(txtRam.getText());
        l.setPrice(Double.parseDouble(txtPrice.getText()));
        l.setQuantity(Integer.parseInt(txtQuantity.getText()));
        return l;
    }
}