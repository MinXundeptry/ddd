/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author TrungBui
 */
public class Category {
    private int categoryId;
    private String categoryName;
    private String description;

    public Category(int id, String name, String description) {
        this.categoryId = id;
        this.categoryName = name;
        this.description = description;
    }

    public int getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public String getDescription() { return description; }

    @Override
    public String toString() { return categoryName; }
}