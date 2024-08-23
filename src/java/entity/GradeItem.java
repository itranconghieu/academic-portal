// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author End User
 */
public class GradeItem {
    private String id;
    private GradeCategory category;
    private String name;
    private int weight;
    private float criteria;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GradeCategory getCategory() {
        return category;
    }

    public void setCategory(GradeCategory category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public float getCriteria() {
        return criteria;
    }

    public void setCriteria(float criteria) {
        this.criteria = criteria;
    }
    
    
}
