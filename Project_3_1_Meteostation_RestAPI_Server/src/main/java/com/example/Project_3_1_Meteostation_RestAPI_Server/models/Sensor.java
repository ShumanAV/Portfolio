package com.example.Project_3_1_Meteostation_RestAPI_Server.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Name of sensor should not be empty")
    @Size(min = 3, max = 30, message = "Name of sensor should be between 3 and 30 characters")
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurements;

    public Sensor() {
    }

    public Sensor(String name) {
        this.name = name;
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

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }
}
