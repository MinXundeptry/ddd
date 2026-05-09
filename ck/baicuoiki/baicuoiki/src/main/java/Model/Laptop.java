/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class Laptop {
    private int laptopId;
    private String laptopName;
    private int categoryId;     // Mã danh mục để lưu xuống DB
    private String categoryName; // Tên danh mục để hiện lên bảng (Nhờ lệnh JOIN)
    private String cpu;
    private String ram;
    private double price;       // Giá bán
    private int quantity;       // Số lượng tồn kho

    public Laptop() {
    }

    public Laptop(int laptopId, String laptopName, int categoryId, String categoryName, String cpu, String ram, double price, int quantity) {
        this.laptopId = laptopId;
        this.laptopName = laptopName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.cpu = cpu;
        this.ram = ram;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter & Setter
    public int getLaptopId() { return laptopId; }
    public void setLaptopId(int laptopId) { this.laptopId = laptopId; }
    public String getLaptopName() { return laptopName; }
    public void setLaptopName(String laptopName) { this.laptopName = laptopName; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getCpu() { return cpu; }
    public void setCpu(String cpu) { this.cpu = cpu; }
    public String getRam() { return ram; }
    public void setRam(String ram) { this.ram = ram; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
