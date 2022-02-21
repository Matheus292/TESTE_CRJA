/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matheus.test_crja.model.repository;

import com.matheus.test_crja.model.entity.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Matheus
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
    
    @Query("SELECT p FROM Person p WHERE p.name = :name")
    List<Person> listPersonByName(@Param(value = "name") String name);
    
}
