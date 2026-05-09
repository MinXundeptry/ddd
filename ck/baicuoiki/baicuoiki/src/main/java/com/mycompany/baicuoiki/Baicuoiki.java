/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.baicuoiki;
import View.QuanLyKhoPanel;
import View.QuanLyNhaCungCapPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.LaptopGUI;
/**
 *
 * @author Admin
 */
public class Baicuoiki extends JFrame{
    public Baicuoiki() {
        initUI();
    }
private void btnLaptopActionPerformed(java.awt.event.ActionEvent evt) {
    LaptopGUI laptopGUI = new LaptopGUI();
    laptopGUI.setLocationRelativeTo(null); // đúng cú pháp
    laptopGUI.setVisible(true);            // gọi từ object
}

    private void initUI() {
        // 1. Cấu hình form chính
        setTitle("HỆ THỐNG QUẢN LÝ CỬA HÀNG LAPTOP - DASHBOARD");
        setSize(1200, 750);
        setLocationRelativeTo(null); // Căn giữa màn hình
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 2. Tạo Menu Bar (Thanh thực đơn ngang)
        setJMenuBar(createMenuBar());

        // 3. Phần thân (Trang trí ảnh nền hoặc lời chào)
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Tạo một Panel chứa nội dung chào mừng
        JPanel welcomeBox = new JPanel();
        welcomeBox.setLayout(new BoxLayout(welcomeBox, BoxLayout.Y_AXIS));
        welcomeBox.setBackground(Color.WHITE);

        JLabel lblWelcome = new JLabel("CHÀO MỪNG ĐẾN VỚI HỆ THỐNG QUẢN LÝ");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblWelcome.setForeground(new Color(0, 102, 204));
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSub = new JLabel("Vui lòng chọn chức năng trên thanh Menu");
        lblSub.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        lblSub.setForeground(Color.GRAY);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomeBox.add(lblWelcome);
        welcomeBox.add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách
        welcomeBox.add(lblSub);
        
        mainPanel.add(welcomeBox);
        add(mainPanel, BorderLayout.CENTER);
    }

    // --- HÀM TẠO THANH MENU ---
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        // Tăng chiều cao thanh menu một chút cho đẹp
        menuBar.setPreferredSize(new Dimension(1200, 40));

        // GROUP 0: HỆ THỐNG
        JMenu menuHeThong = new JMenu("Hệ Thống");
        menuHeThong.setIcon(UIManager.getIcon("FileView.computerIcon")); // Icon mặc định của Java
        
        JMenuItem itemDangXuat = new JMenuItem("Đăng Xuất");
        JMenuItem itemThoat = new JMenuItem("Thoát");
        
        itemThoat.addActionListener(e -> System.exit(0));
        
        menuHeThong.add(itemDangXuat);
        menuHeThong.addSeparator();
        menuHeThong.add(itemThoat);

        // GROUP 1: QUẢN LÝ kho và NCC (KHO)
        JMenu menuKho = new JMenu("Quản Lý Kho và Nhà cung cấp");
        // Gọi FormCode là "FormKho" để lát xử lý
        addMenuItem(menuKho, "Quản lý Kho", "FormKho"); 
        addMenuItem(menuKho, "Quản lý Nhà cung cấp", "FormNCC"); // Demo placeholder

        // GROUP 2: TRỢ GIÚP (Demo thêm cho giống menu thật)
        JMenu menuTroGiup = new JMenu("Trợ Giúp");
        addMenuItem(menuTroGiup, "Thông tin phần mềm", "FormAbout");
        JMenu menuSanPham = new JMenu("Sản phẩm");
          addMenuItem(menuSanPham, "Quản lý Laptop", "FormLaptop");

          menuBar.add(menuSanPham);


        // --- ADD CÁC MENU VÀO THANH BAR ---
        menuBar.add(menuHeThong);
        menuBar.add(menuKho);
        menuBar.add(menuTroGiup);

        return menuBar;
    }

    // --- HÀM HỖ TRỢ TẠO MENU ITEM VÀ GẮN SỰ KIỆN ---
    private void addMenuItem(JMenu menu, String title, String formCode) {
        JMenuItem item = new JMenuItem(title);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        item.setPreferredSize(new Dimension(200, 30));
        
        // --- SỬA LẠI: Dùng ActionListener truyền thống ---
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPopupForm(title, formCode);
            }
        });
        
        menu.add(item);
    }

    // LOGIC MỞ POPUP FORM (CỬA SỔ RIÊNG)
    private void openPopupForm(String title, String formCode) {
        // Tạo cửa sổ Popup riêng biệt giống code mẫu MainForm
        JFrame popup = new JFrame(title);
        popup.setSize(1100, 650); // Kích thước cửa sổ popup
        popup.setLocationRelativeTo(null); // Ra giữa màn hình
        popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Chỉ tắt popup, ko tắt phần mềm chính

        // --- XỬ LÝ GHÉP CODE TỪ DỰ ÁN CỦA BẠN ---
        if (formCode.equals("FormKho")) {
            // Nhúng Panel Quản Lý Kho vào
            popup.add(new QuanLyKhoPanel());
            popup.setVisible(true);
            return;
        }
        else if (formCode.equals("FormNCC")) {
            // Nhúng Panel Nhà Cung Cấp vào
            popup.add(new QuanLyNhaCungCapPanel());
            popup.setVisible(true);
            return;
        }
        else if (formCode.equals("FormLaptop")) {
    LaptopGUI gui = new LaptopGUI();
    gui.setLocationRelativeTo(null);
    gui.setVisible(true);
    return;
}

        
        // Các trường hợp chưa có code thật (Demo)
        JPanel demoPanel = new JPanel(new GridBagLayout());
        JLabel lbl = new JLabel("Chức năng đang phát triển: " + title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(Color.RED);
        demoPanel.add(lbl);
        
        popup.add(demoPanel);
        popup.setVisible(true);
    }

    // HÀM MAIN ĐỂ CHẠY CHƯƠNG TRÌNH
    public static void main(String[] args) {
        // Set giao diện đẹp theo hệ điều hành (Windows)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new Baicuoiki().setVisible(true);
         
        });
      
    }
}
