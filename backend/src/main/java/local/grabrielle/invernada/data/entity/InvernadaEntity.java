package local.grabrielle.invernada.data.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "invernada")
public class InvernadaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "invernada_member",
            joinColumns = @JoinColumn(name = "invernada_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<PersonEntity> members;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "invernada_administrator",
            joinColumns = @JoinColumn(name = "invernada_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<PersonEntity> administrators;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<EventEntity> events;

    public InvernadaEntity() {
    }

    public InvernadaEntity(Integer id, String name, Set<PersonEntity> members, Set<PersonEntity> administrators, Set<EventEntity> events) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.administrators = administrators;
        this.events = events;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PersonEntity> getMembers() {
        return members;
    }

    public void setMembers(Set<PersonEntity> members) {
        this.members = members;
    }

    public Set<PersonEntity> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(Set<PersonEntity> administrators) {
        this.administrators = administrators;
    }

    public Set<EventEntity> getEvents() {
        return events;
    }

    public void setEvents(Set<EventEntity> events) {
        this.events = events;
    }
}
