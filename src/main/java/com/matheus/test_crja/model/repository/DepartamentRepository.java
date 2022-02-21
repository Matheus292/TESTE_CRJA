/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matheus.test_crja.model.repository;

import com.matheus.test_crja.model.entity.Departament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Matheus
 */
@Repository
public interface DepartamentRepository extends JpaRepository<Departament, Integer>{
    
}
