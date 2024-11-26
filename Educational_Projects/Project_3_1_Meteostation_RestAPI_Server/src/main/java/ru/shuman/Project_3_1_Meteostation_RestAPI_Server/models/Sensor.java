package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

/*
Модель сенсора
 */
@Entity
@Table(name = "sensor")
public class Sensor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurements;

    public Sensor() {
    }

    public Sensor(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "name='" + name + '}';
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
