package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "type_doc")
public class TypeDoc implements Serializable {

    @Column(name = "id")
    private Integer id;

    @Id
    @Column(name = "name")
    @NotNull(message = "Наименование типа документа не должно быть пустым")
    @Size(max = 255, message = "Наименование типа документа должно быть не более 255 символов")
    private String name;

    @OneToMany(mappedBy = "typeDoc")
    private List<Document> documents;

}
