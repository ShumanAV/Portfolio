package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "document")
public class Document {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(max = 255, message = "Наименование документа должно быть не более 255 символов")
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_doc_name", referencedColumnName = "name")
    private TypeDoc typeDoc;

    @OneToOne(mappedBy = "document")
    private Parent parent;

    @OneToOne(mappedBy = "document")
    private Patient patient;


}
