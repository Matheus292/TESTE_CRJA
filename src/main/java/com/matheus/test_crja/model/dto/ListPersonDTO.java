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
public class ListPersonDTO {
    private Long id;
    private String name;
    private String departament;
    private Integer amountHour;

    public ListPersonDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public Integer getAmountHour() {
        return amountHour;
    }

    public void setAmountHour(Integer amountHour) {
        this.amountHour = amountHour;
    }
    
    
    
}
