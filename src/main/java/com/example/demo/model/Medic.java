package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Medici")
public class Medic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medic")  // Numele coloanei din tabelul Medici
    private Long id;

    @Column(name = "nume", nullable = false)  // Numele coloanei din tabelul Medici
    private String nume;

    @Column(name = "prenume", nullable = false)  // Numele coloanei din tabelul Medici
    private String prenume;

    @Column(name = "sex", nullable = false)  // Numele coloanei din tabelul Medici
    public String sex;

    @Column(name = "specializare", nullable = false)  // Numele coloanei din tabelul Medici
    private String specializare;

    @Column(name = "e-mail", nullable = false, unique = true)  // Numele coloanei din tabelul Medici
    private String email;

    @Column(name = "telefon", nullable = false)  // Numele coloanei din tabelul Medici
    private String telefon;

    @Column(name = "Spital", nullable = false)  // Numele coloanei din tabelul Medici
    private String spital;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getSpital() {
        return spital;
    }

    public void setSpital(String spital) {
        this.spital = spital;
    }
}
