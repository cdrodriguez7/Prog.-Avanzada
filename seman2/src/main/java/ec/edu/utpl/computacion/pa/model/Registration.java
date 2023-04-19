package ec.edu.utpl.computacion.pa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Registration {
    @Id
    private Integer id;
    @Column(name = "FIRST_NAME")
    private String firtsName;
    @Column(name = "LAST_NAME")
    private String lastName;
    private Integer age;

    public Registration() {
    }

    public Registration(Integer id, String firtsName, String lastName, Integer age) {
        this.id = id;
        this.firtsName = firtsName;
        this.lastName = lastName;
        this.age = age;
    }
}
