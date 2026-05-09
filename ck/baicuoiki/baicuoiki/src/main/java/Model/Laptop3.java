/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author TrungBui
 */
public class Laptop3 {
    private int laptopId;
    private int categoryId;
    private String laptopName;
    private String cpuInfo;
    private String ramInfo;
    private String ssdInfo;
    private double price;
    private int stockQuantity;
    private String imageUrl;
    private int status;

    public Laptop3 () {}

    // Getters và Setters
    public int getLaptopId() { return laptopId; }
    public void setLaptopId(int id) { this.laptopId = id; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int id) { this.categoryId = id; }
    public String getLaptopName() { return laptopName; }
    public void setLaptopName(String name) { this.laptopName = name; }
    public String getCpuInfo() { return cpuInfo; }
    public void setCpuInfo(String cpu) { this.cpuInfo = cpu; }
    public String getRamInfo() { return ramInfo; }
    public void setRamInfo(String ram) { this.ramInfo = ram; }
    public String getSsdInfo() { return ssdInfo; }
    public void setSsdInfo(String ssd) { this.ssdInfo = ssd; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int qty) { this.stockQuantity = qty; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String url) { this.imageUrl = url; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}
