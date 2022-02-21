/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matheus.test_crja.model.dto;

/**
 *
 * @author Matheus
 */
public class ConsumesPersonDTO {
    private long id;
    private String name;
    private double avgHour;

    public ConsumesPersonDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAvgHour() {
        return avgHour;
    }

    public void setAvgHour(double avgHour) {
        this.avgHour = avgHour;
    }
    
    
}
