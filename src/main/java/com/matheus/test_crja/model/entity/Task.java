/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matheus.test_crja.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Matheus
 */
@Entity
@Table(name = "TAREFA")
public class Task implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "TITULO")
    private String title;
    
    @Column(name = "DESCRICAO")
    private String description;
    
    @Column(name = "PRAZO")
    @Temporal(TemporalType.DATE)
    private Date deadLine;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_DEPARTAMENTO", referencedColumnName = "ID")
    private Departament departamentId;
    
    @Column(name = "DURACAO")
    private Integer duration;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PESSOA", referencedColumnName = "ID")
    private Person personId;
    
    @Column(name = "FINALIZADO")
    private boolean finish;

    public Task() {
    }

    public Task(String title, String description, Date deadLine, Departament departamentId, Integer duration, boolean finish) {
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.departamentId = departamentId;
        this.duration = duration;
        this.finish = finish;
    }

    public Task(String title, String description, Date deadLine, Departament departamentId, Integer duration, Person personId, boolean finish) {
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.departamentId = departamentId;
        this.duration = duration;
        this.personId = personId;
        this.finish = finish;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Departament getDepartamentId() {
        return departamentId;
    }

    public void setDepartamentId(Departament departamentId) {
        this.departamentId = departamentId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", title=" + title + ", description=" + description + ", deadLine=" + deadLine + ", departamentId=" + departamentId + ", duration=" + duration + ", personId=" + personId + ", finish=" + finish + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.title);
        hash = 47 * hash + Objects.hashCode(this.description);
        hash = 47 * hash + Objects.hashCode(this.deadLine);
        hash = 47 * hash + Objects.hashCode(this.departamentId);
        hash = 47 * hash + Objects.hashCode(this.duration);
        hash = 47 * hash + Objects.hashCode(this.personId);
        hash = 47 * hash + (this.finish ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Task other = (Task) obj;
        if (this.finish != other.finish) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.deadLine, other.deadLine)) {
            return false;
        }
        if (!Objects.equals(this.departamentId, other.departamentId)) {
            return false;
        }
        if (!Objects.equals(this.duration, other.duration)) {
            return false;
        }
        if (!Objects.equals(this.personId, other.personId)) {
            return false;
        }
        return true;
    }
       
}
