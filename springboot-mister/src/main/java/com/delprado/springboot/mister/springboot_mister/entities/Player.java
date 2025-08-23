package com.delprado.springboot.mister.springboot_mister.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String image;

    private String name;
    private String lastname;
    private Integer numberClau;
    private Date date;

    public Player() {
    }

    public Player(Long id, String image, String name, String lastname, Integer numberClau, Date date) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.lastname = lastname;
        this.numberClau = numberClau;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getNumberClau() {
        return numberClau;
    }

    public void setNumberClau(Integer numberClau) {
        this.numberClau = numberClau;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
