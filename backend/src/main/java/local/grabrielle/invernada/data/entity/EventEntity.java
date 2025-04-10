package local.grabrielle.invernada.data.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    @Column(name = "event_date")
    private LocalDateTime date;
    @Column(name = "event_place")
    private String place;
    @Column(name = "notes")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    private InvernadaEntity invernada;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "event_participant",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<PersonEntity> participants;

    public EventEntity() {
    }

    public EventEntity(Integer id, String name, LocalDateTime date, String place, String notes, InvernadaEntity invernada, Set<PersonEntity> participants) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.place = place;
        this.notes = notes;
        this.invernada = invernada;
        this.participants = participants;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public InvernadaEntity getInvernada() {
        return invernada;
    }

    public void setInvernada(InvernadaEntity invernada) {
        this.invernada = invernada;
    }

    public Set<PersonEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<PersonEntity> participants) {
        this.participants = participants;
    }
}
