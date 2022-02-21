/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matheus.test_crja.controller;

import com.matheus.test_crja.model.dto.TaskDTO;
import com.matheus.test_crja.model.entity.Departament;
import com.matheus.test_crja.model.entity.Person;
import com.matheus.test_crja.model.entity.Task;
import com.matheus.test_crja.model.input.Message;
import com.matheus.test_crja.model.input.TaskInput;
import com.matheus.test_crja.model.input.TaskPersonInput;
import com.matheus.test_crja.model.repository.DepartamentRepository;
import com.matheus.test_crja.model.repository.PersonRepository;
import com.matheus.test_crja.model.repository.TaskRepository;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Matheus
 */
@RestController
public class TaskController {
       
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private DepartamentRepository departamentRepository;
    
    @Autowired
    private PersonRepository personRepository;
    
    
    public TaskController() {
    }
    
    /***
     * Adicionar uma tarefa 
     * @param input
     * @return 
     */
    @PostMapping(value = "/post/tarefas")
    public ResponseEntity<Message> addTask(@RequestBody TaskInput input)
    {
        try
        {
            Task task = new Task();
            task.setFinish(false);
            
            if(Objects.isNull(input.getTitle()) || input.getTitle().trim().equals("")){
                return new ResponseEntity<>(new Message("Nome não informado"), HttpStatus.BAD_REQUEST);
            }
            task.setTitle(input.getTitle());
            
            if(Objects.isNull(input.getDescription()) || input.getDescription().trim().equals("")){
                return new ResponseEntity<>(new Message("Descrição não informada"), HttpStatus.BAD_REQUEST);
            }
            
            task.setDescription(input.getDescription());
            
            if(Objects.isNull(input.getDuration()) || input.getDuration() < 1){
                return new ResponseEntity<>(new Message("Duração inválida"), HttpStatus.BAD_REQUEST);
            }
            
            task.setDuration(input.getDuration());
           
            try
            {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                task.setDeadLine(format.parse(input.getDeadLine()));
                
                if(new Date().after(task.getDeadLine()))
                {
                    return new ResponseEntity<>(new Message("O prazo não pode ser anterior do que a data atual"), HttpStatus.BAD_REQUEST);
                }                
            }
            catch(Exception e)
            {
                return new ResponseEntity<>(new Message("Data inválida"), HttpStatus.BAD_REQUEST);
            }            
            Optional<Departament> optional = departamentRepository.findById(input.getDepartamentId());
            
            if(!optional.isPresent())
            {
                return new ResponseEntity<>(new Message("O departamento não existe"), HttpStatus.BAD_REQUEST);
            }            
            task.setDepartamentId(optional.get());       
            taskRepository.save(task);            
            return new ResponseEntity<>(new Message("Tarefa criada com sucesso"), HttpStatus.OK);
            
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(new Message("Erro ao tentar cadastrar a tarefa"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /***
     * Alocar uma pessoa na tarefa que tenha o mesmo departamento 
     * @param taskId
     * @param input
     * @return 
     */
    @PutMapping(value = "/put/tarefas/alocar/{id}")
    public ResponseEntity<Message> addPersonInTask(@PathVariable(value = "id") Long taskId, @RequestBody TaskPersonInput input)
    {
        try
        {
            Optional<Task> taskOptional = taskRepository.findById(taskId);
            
            if(!taskOptional.isPresent())
            {
                return new ResponseEntity<>(new Message("Tarefa não encontrada"), HttpStatus.NOT_FOUND);
            }
            
            Optional<Person> personOptional = personRepository.findById(input.getPersonId());
            
            if(!personOptional.isPresent())
            {
                return new ResponseEntity<>(new Message("Pessoa não encontrada"), HttpStatus.BAD_REQUEST);
            }           
            
            Task task = taskOptional.get();
            Person person = personOptional.get();
            
            if(person.getDepartament() != task.getDepartamentId())
            {
                return new ResponseEntity<>(new Message("A pessoa não é do mesmo departamento da tarefa"), HttpStatus.BAD_REQUEST);
            }            
            
            task.setPersonId(person);
            taskRepository.save(task);
            return new ResponseEntity<>(new Message("Vinculo criado com sucesso!"), HttpStatus.OK);
        }
        catch(Exception e)
        {
           return new ResponseEntity<>(new Message("Erro ao tentar atualizar os dados pessoa"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /***
     * Finalizar tarefa 
     * @param taskId
     * @return 
     */
    @PutMapping(value = "/put/tarefas/finalizar/{id}")
    public ResponseEntity<Message> finishTask(@PathVariable(value = "id") Long taskId)
    {
        try
        {
            Optional<Task> taskOptional = taskRepository.findById(taskId);
            
            if(!taskOptional.isPresent())
            {
                return new ResponseEntity<>(new Message("Tarefa não encontrada"), HttpStatus.NOT_FOUND);
            }
        
            Task task = taskOptional.get();
        
            if(task.isFinish())
            {
                return new ResponseEntity<>(new Message("Essa tarefa já foi finalizada"), HttpStatus.BAD_REQUEST);
            }
        
            task.setFinish(true);        
            taskRepository.save(task);        
            return new ResponseEntity<>(new Message("Tarefa finalizada com sucesso"), HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(new Message("Erro ao tentar finalizar a terafa"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    /***
     * Listar 3 tarefas que estejam sem pessoa alocada com os prazos mais antigos 
     * @return 
     */
    @GetMapping(value = "/get/tarefas/pendentes")
    public ResponseEntity getPendings()
    {
        try
        {
            List<Task> tasks = taskRepository.listPending(PageRequest.of(0, 3));
            
            if(Objects.isNull(tasks) || tasks.isEmpty())
            {
                return new ResponseEntity<>(new Message("Nenhuma tarefa encontrada"), HttpStatus.OK);
            }
            
            List<TaskDTO> list = new ArrayList<>();
            
           tasks.forEach(object ->{
           
               TaskDTO dto = new TaskDTO();
               dto.setDeadLine(object.getDeadLine());
               dto.setId(object.getId());
               dto.setTitle(object.getTitle());
               
               list.add(dto);               
           });

           return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(new Message("Erro ao tentar listar as tarefas pendentes"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
