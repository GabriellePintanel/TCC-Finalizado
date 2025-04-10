package local.grabrielle.invernada.data.entity;

import jakarta.persistence.*;
import local.grabrielle.invernada.api.pilcha.model.PilchaEnum;

@Entity
@Table(name = "pilcha")
public class PilchaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "description")
    private String description;
    @Column(name = "pilcha_type")
    @Enumerated(EnumType.ORDINAL)
    private PilchaEnum pilchaType;
    @Column(name = "tag")
    private String tag;
    @Column(name = "notes")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pilcha_owner",
            joinColumns = @JoinColumn(name = "pilcha_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private PersonEntity owner;

    public PilchaEntity() {
    }

    public PilchaEntity(Integer id, String description, PilchaEnum pilchaType, String notes, PersonEntity owner) {
        this.id = id;
        this.description = description;
        this.pilchaType = pilchaType;
        this.notes = notes;
        this.setOwner(owner);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PilchaEnum getPilchaType() {
        return pilchaType;
    }

    public void setPilchaType(PilchaEnum pilchaType) {
        this.pilchaType = pilchaType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public PersonEntity getOwner() {
        return owner;
    }

    public void setOwner(PersonEntity owner) {
        this.owner = owner;
    }
}

