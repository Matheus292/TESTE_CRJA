/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matheus.test_crja.controller;

import com.matheus.test_crja.model.dto.ConsumesPersonDTO;
import com.matheus.test_crja.model.dto.ListPersonDTO;
import com.matheus.test_crja.model.entity.Departament;
import com.matheus.test_crja.model.entity.Person;
import com.matheus.test_crja.model.entity.Task;
import com.matheus.test_crja.model.input.Message;
import com.matheus.test_crja.model.input.PersonInput;
import com.matheus.test_crja.model.repository.DepartamentRepository;
import com.matheus.test_crja.model.repository.PersonRepository;
import com.matheus.test_crja.model.repository.TaskRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Matheus
 */
@RestController
public class PersonController {
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private DepartamentRepository departamentRepository;
    
    @Autowired
    private TaskRepository taskRepository;
    
    public PersonController(){
    
    }
    
    /***
     * Adicionar uma pessoa 
     * @param input
     * @return 
     */
    @PostMapping (value = "/post/pessoas")
    public ResponseEntity<Message> createPerson(@RequestBody(required = true) PersonInput input)
    {        
        try
        {
            Optional<Departament> optional = departamentRepository.findById(input.getIdDepartament());
            
            if(!optional.isPresent())
            {
                return new ResponseEntity<>(new Message("O departamento não existe"), HttpStatus.BAD_REQUEST);
            }    
               
            if(Objects.isNull(input.getName()) || input.getName().trim().equals(""))
            {
                return new ResponseEntity<>(new Message("O nome da pessoa não foi informado"), HttpStatus.BAD_REQUEST);
            }
            
            Person person = new Person();
            person.setDepartament(optional.get());
            person.setName(input.getName());
            
            personRepository.save(person);
            return new ResponseEntity<>(new Message("Pessoa cadastrada com sucesso!"), HttpStatus.OK);
        }
        catch(Exception e)
        {
           return new ResponseEntity<>(new Message("Erro ao tentar cadastrar uma nova pessoa"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /***
     * Alterar uma pessoa 
     * @param personId
     * @param input
     * @return 
     */
    @PutMapping(value = "/put/pessoas/{id}")
    public ResponseEntity<Message> alterPerson(@PathVariable(value = "id") Long personId, @RequestBody PersonInput input)
    {
        try
        {
            Optional<Person> person = personRepository.findById(personId);
            
            if(!person.isPresent())
            {
                return new ResponseEntity<>(new Message("Pessoa não encontrada"), HttpStatus.NOT_FOUND);
            }
            
            Optional<Departament> optional = departamentRepository.findById(input.getIdDepartament());
            
            if(!optional.isPresent())
            {
                return new ResponseEntity<>(new Message("O departamento não existe"), HttpStatus.BAD_REQUEST);
            }    
                        
            Person personUpdate = person.get();
            personUpdate.setDepartament(optional.get());
            
            
            if(Objects.isNull(input.getName()) || input.getName().trim().equals(""))
            {
                return new ResponseEntity<>(new Message("O nome da pessoa não foi informado"), HttpStatus.BAD_REQUEST);
            }
            personUpdate.setName(input.getName());            
            
            personRepository.save(personUpdate);
            return new ResponseEntity<>(new Message("Dados da pessoa atualzado(s) com sucesso!"), HttpStatus.OK);
        }
        catch(Exception e)
        {
           return new ResponseEntity<>(new Message("Erro ao tentar atualizar os dados pessoa"), HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }
    
    /***
     * Remover pessoa 
     * @param personId
     * @return 
     */
    @DeleteMapping(value = "/delete/pessoas/{id}")
    public ResponseEntity<Message> deletePerson(@PathVariable(name = "id") Long personId)
    {
        try
        {
            Optional<Person> person = personRepository.findById(personId);

            if (!person.isPresent())
            {
                return new ResponseEntity<>(new Message("Pessoa não encontrada"), HttpStatus.NOT_FOUND);
            }            
            Person personDelete = person.get();
            
            if(Objects.nonNull(personDelete) && !personDelete.getTasks().isEmpty())
            {
                personDelete.getTasks().forEach(object ->{
                    object.setPersonId(null);                    
                });
                taskRepository.saveAll(personDelete.getTasks());
            }           
            
            personRepository.delete(personDelete);           
            
            return new ResponseEntity<>(new Message("Pessoa excluída com sucesso"), HttpStatus.OK);
            
        } 
        catch (Exception e) 
        {
            return new ResponseEntity<>(new Message("Erro ao tentar excluir dados da pessoa"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /***
     * Listar pessoas trazendo nome, departamento, total horas gastas nas tarefas 
     * @return 
     */
    @GetMapping(value = "/get/pessoas")
    public ResponseEntity getPersons()
    {
        try
        {
            List<Person> persons = personRepository.findAll();
                        
            if(Objects.isNull(persons) || persons.isEmpty())
            {
                return new ResponseEntity<>(new Message("Nenhuma pessoa encontrada"), HttpStatus.OK);
            }
            
            List<ListPersonDTO> list = new ArrayList<>();
            
            for(Person person : persons)
            {
                ListPersonDTO dto = new ListPersonDTO();
                dto.setId(person.getId());
                dto.setName(person.getName());
                dto.setDepartament(person.getDepartament().getTitle());
                
                Integer sum = person.getTasks()
                                .stream()
                                .mapToInt(Task::getDuration)
                                .sum();
                
                dto.setAmountHour(sum);
                list.add(dto);
            }
            
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(new Message("Erro ao listar as pessoas"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /***
     * Buscar pessoas por nome e período, retorna média de horas gastas por tarefa.Ficou uma dúvida sobre o período o que seria esse período?
     *
     * @param name
     * @return 
     */
    @GetMapping(value = "/get/pessoas/gastos")
    public ResponseEntity getConsumes(@RequestParam(name = "name", required = false) String name)
    {
        try
        {
             List<Person> persons ;
            if(Objects.isNull(name) || name.trim().isEmpty())
            {
                persons = personRepository.findAll();
            }
            else
            {
                persons = personRepository.listPersonByName(name);
            }
                    
            if(Objects.isNull(persons) || persons.isEmpty())
            {
                return new ResponseEntity<>(new Message("Nenhuma pessoa encontrada"), HttpStatus.OK);
            }
            
            List<ConsumesPersonDTO> list = new ArrayList<>();
            
            for(Person person : persons)
            {
                ConsumesPersonDTO dto = new ConsumesPersonDTO();
                dto.setId(person.getId());
                dto.setName(person.getName());
                
                
                OptionalDouble avg = person.getTasks().stream().mapToDouble(Task::getDuration).average();
                dto.setAvgHour(avg.isPresent() ? avg.getAsDouble() : 0.0);
                
                list.add(dto);
            }
            
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch(Exception e)
        {
           return new ResponseEntity<>(new Message("Erro ao listar os gastos"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
