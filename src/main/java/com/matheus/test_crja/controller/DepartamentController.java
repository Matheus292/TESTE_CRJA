/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matheus.test_crja.controller;

import com.matheus.test_crja.model.dto.DepartamentDTO;
import com.matheus.test_crja.model.entity.Departament;
import com.matheus.test_crja.model.input.Message;
import com.matheus.test_crja.model.repository.DepartamentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Matheus
 */
@RestController
public class DepartamentController {
    
    @Autowired
    private DepartamentRepository departamentRepository;
       
    public DepartamentController(){
    }
        
    /***
     * Listar departamento e quantidade de pessoas e tarefas 
     * @return 
     */
    @GetMapping("/get/departamentos")
    public ResponseEntity getDeorataments()
    {
        try
        {
            List<Departament> departaments = departamentRepository.findAll();
            
            if(Objects.isNull(departaments) || departaments.isEmpty())
            {
                return new ResponseEntity<>(new Message("Nenhum departamento encontrado"), HttpStatus.OK);
            }
            
            List<DepartamentDTO> list = new ArrayList<>();
            departaments.forEach(object ->{
                
                DepartamentDTO dto = new DepartamentDTO();
                dto.setId(object.getId());
                dto.setName(object.getTitle());
                dto.setAmountPerson(object.getPersons().size());
                dto.setAmountTask(object.getTasks().size());
                
                list.add(dto);                
            });
            
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(new Message("Erro ao listar os departamentos"), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}
