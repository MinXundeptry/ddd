/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * 
 * @author TrungBui
 */

package view;

import dao.Laptopdao1;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.List;
import model.Category;
import model.Laptop3;


public class LaptopGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<Category> cbCategory, cbFilter;
    private JTextField txtId, txtName, txtPrice, txtStock, txtCpu, txtRam, txtSsd;
    private JTextArea txtCatDesc; 
    private JLabel lblImage; 
    private String selectedImageName = ""; 
    private Laptopdao1 laptopdao1 = new Laptopdao1();
    private DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");

    public LaptopGUI() {
        Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);
        UIManager.put("Label.font", mainFont);
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 13));

        setTitle("ỨNG DỤNG BÁN LAPTOP");
        setSize(1300, 850);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 242, 245));
        setLayout(new BorderLayout(10, 10));

        // --- NORTH: Thanh Lọc ---
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlTop.setBackground(Color.WHITE);
        pnlTop.add(new JLabel("Lọc theo hãng:"));
        cbFilter = new JComboBox<>();
        JButton btnFilter = new JButton("Lọc");
        btnFilter.setBackground(new Color(187, 222, 251)); btnFilter.setForeground(Color.BLACK);
        JButton btnAll = new JButton("Tải lại tất cả");
        pnlTop.add(cbFilter); pnlTop.add(btnFilter); pnlTop.add(btnAll);
        add(pnlTop, BorderLayout.NORTH);

        // --- WEST: Nhập liệu ---
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setPreferredSize(new Dimension(450, 0));
        pnlInput.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5); g.fill = GridBagConstraints.HORIZONTAL;

        addInput(pnlInput, "Mã SP:", txtId = new JTextField(), 0, g); txtId.setEditable(false);
        addInput(pnlInput, "Tên máy:", txtName = new JTextField(), 1, g);
        addInput(pnlInput, "Hãng sx:", cbCategory = new JComboBox<>(), 2, g);
        
        // Ô mô tả hãng (Description)
        txtCatDesc = new JTextArea(3, 15);
        txtCatDesc.setEditable(false); txtCatDesc.setLineWrap(true); txtCatDesc.setWrapStyleWord(true);
        txtCatDesc.setBackground(new Color(245, 245, 245));
        addInput(pnlInput, "Mô tả hãng:", new JScrollPane(txtCatDesc), 3, g);

        addInput(pnlInput, "CPU:", txtCpu = new JTextField(), 4, g);
        addInput(pnlInput, "RAM:", txtRam = new JTextField(), 5, g);
        addInput(pnlInput, "SSD:", txtSsd = new JTextField(), 6, g);
        addInput(pnlInput, "Giá bán:", txtPrice = new JTextField(), 7, g);
        addInput(pnlInput, "Tồn kho:", txtStock = new JTextField(), 8, g);

        lblImage = new JLabel("Hình ảnh Laptop", SwingConstants.CENTER);
        lblImage.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        lblImage.setPreferredSize(new Dimension(200, 160));
        g.gridx = 0; g.gridy = 9; g.gridwidth = 2; pnlInput.add(lblImage, g);

        JButton btnImg = new JButton("Chọn hình ảnh...");
        btnImg.setBackground(new Color(240, 240, 240)); btnImg.setForeground(Color.BLACK);
        g.gridy = 10; pnlInput.add(btnImg, g);

        JPanel pnlBtns = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlBtns.setBackground(Color.WHITE);
        JButton btnAdd = new JButton("THÊM MỚI"); btnAdd.setBackground(new Color(200, 230, 201)); btnAdd.setForeground(Color.BLACK);
        JButton btnUp = new JButton("CẬP NHẬT"); btnUp.setBackground(new Color(255, 249, 196)); btnUp.setForeground(Color.BLACK);
        JButton btnDel = new JButton("XÓA"); btnDel.setBackground(new Color(255, 205, 210)); btnDel.setForeground(Color.BLACK);
        JButton btnClr = new JButton("LÀM TRỐNG");
        pnlBtns.add(btnAdd); pnlBtns.add(btnUp); pnlBtns.add(btnDel); pnlBtns.add(btnClr);
        g.gridy = 11; pnlInput.add(pnlBtns, g);
        add(pnlInput, BorderLayout.WEST);

        // --- CENTER: Bảng dữ liệu ---
        tableModel = new DefaultTableModel(new String[]{"ID", "Tên", "Hãng", "CPU", "RAM", "SSD", "Giá", "Kho", "Ảnh"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setSelectionBackground(new Color(232, 240, 254));
        table.setSelectionForeground(Color.BLACK);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- SỰ KIỆN ---
        initData();
        btnImg.addActionListener(e -> chooseImage());
        btnAdd.addActionListener(e -> save(true));
        btnUp.addActionListener(e -> save(false));
        btnDel.addActionListener(e -> delete());
        btnClr.addActionListener(e -> clear());
        btnFilter.addActionListener(e -> loadTable(laptopdao1.getByFilter(((Category)cbFilter.getSelectedItem()).getCategoryId())));
        btnAll.addActionListener(e -> loadTable(laptopdao1.getAllForTable()));
        
        cbCategory.addActionListener(e -> {
            Category c = (Category) cbCategory.getSelectedItem();
            if(c != null) txtCatDesc.setText(c.getDescription());
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if(r != -1) fillForm(r);
        });
    }

    private void addInput(JPanel p, String text, JComponent c, int row, GridBagConstraints g) {
        g.gridx = 0; g.gridy = row; g.gridwidth = 1; p.add(new JLabel(text), g);
        g.gridx = 1; p.add(c, g);
    }

    private void initData() {
        List<Category> cats =  laptopdao1.getCategories();
        for (Category c : cats) { cbCategory.addItem(c); cbFilter.addItem(c); }
        loadTable(laptopdao1.getAllForTable());
    }

    private void loadTable(List<Object[]> data) {
        tableModel.setRowCount(0);
        for (Object[] row : data) {
            row[6] = formatter.format((double)row[6]); // Định dạng tiền VNĐ
            tableModel.addRow(row);
        }
    }

    private void fillForm(int r) {
        txtId.setText(tableModel.getValueAt(r, 0).toString());
        txtName.setText(tableModel.getValueAt(r, 1).toString());
        txtCpu.setText(tableModel.getValueAt(r, 3).toString());
        txtRam.setText(tableModel.getValueAt(r, 4).toString());
        txtSsd.setText(tableModel.getValueAt(r, 5).toString());
        txtPrice.setText(tableModel.getValueAt(r, 6).toString().replaceAll("[^0-9]", ""));
        txtStock.setText(tableModel.getValueAt(r, 7).toString());
        displayImage(tableModel.getValueAt(r, 8).toString());
    }

    private void chooseImage() {
        JFileChooser fc = new JFileChooser();
        if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File f = fc.getSelectedFile();
                selectedImageName = System.currentTimeMillis() + "_" + f.getName();
                Files.copy(f.toPath(), Paths.get("images", selectedImageName), StandardCopyOption.REPLACE_EXISTING);
                displayImage(selectedImageName);
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    private void displayImage(String name) {
        selectedImageName = name;
        File f = new File("images/" + name);
        if(f.exists()) {
            lblImage.setIcon(new ImageIcon(new ImageIcon(f.getAbsolutePath()).getImage().getScaledInstance(200, 160, Image.SCALE_SMOOTH)));
            lblImage.setText("");
        } else { lblImage.setIcon(null); lblImage.setText("Không có ảnh"); }
    }

 private void save(boolean isNew) {
    try {
        Laptop3 l = new Laptop3();   // ✅ KHAI BÁO ĐÚNG

        if (!isNew) {
            l.setLaptopId(Integer.parseInt(txtId.getText()));
        }

        l.setLaptopName(txtName.getText());
        l.setCategoryId(((Category) cbCategory.getSelectedItem()).getCategoryId());
        l.setCpuInfo(txtCpu.getText());
        l.setRamInfo(txtRam.getText());
        l.setSsdInfo(txtSsd.getText());
        l.setPrice(Double.parseDouble(txtPrice.getText()));
        l.setStockQuantity(Integer.parseInt(txtStock.getText()));
        l.setImageUrl(selectedImageName);
        l.setStatus(1);

        boolean ok = isNew ? laptopdao1.insert(l) : laptopdao1.update(l);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Lưu thành công!");
            loadTable(laptopdao1.getAllForTable());
            clear();
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Giá hoặc tồn kho phải là số!");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Có lỗi xảy ra!");
    }
}

   private void delete() {
        if(!txtId.getText().isEmpty() && laptopdao1.delete(Integer.parseInt(txtId.getText()))) {
            loadTable(laptopdao1.getAllForTable());
            clear();
        }
    }


    private void clear() {
        txtId.setText(""); txtName.setText(""); txtCpu.setText(""); txtRam.setText("");
        txtSsd.setText(""); txtPrice.setText(""); txtStock.setText("");
        lblImage.setIcon(null); lblImage.setText("Hình ảnh Laptop"); selectedImageName = "";
    }

  
}