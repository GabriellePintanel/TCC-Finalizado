package local.grabrielle.invernada.data.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "person")
public class PersonEntity {
    @Id
    private String id;
    private String notes;

    @ManyToMany(mappedBy = "administrators", fetch = FetchType.LAZY)
    private Set<InvernadaEntity> administratorOf;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private Set<InvernadaEntity> memberOf;

    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
    private Set<EventEntity> participantOf;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<PilchaEntity> ownerOf;

    public PersonEntity() {
    }

    public PersonEntity(String id, String notes, Set<InvernadaEntity> administratorOf, Set<InvernadaEntity> memberOf, Set<EventEntity> participantOf) {
        this.id = id;
        this.notes = notes;
        this.administratorOf = administratorOf;
        this.memberOf = memberOf;
        this.participantOf = participantOf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<InvernadaEntity> getAdministratorOf() {
        return administratorOf;
    }

    public void setAdministratorOf(Set<InvernadaEntity> administratorOf) {
        this.administratorOf = administratorOf;
    }

    public Set<InvernadaEntity> getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(Set<InvernadaEntity> memberOf) {
        this.memberOf = memberOf;
    }

    public Set<EventEntity> getParticipantOf() {
        return participantOf;
    }

    public void setParticipantOf(Set<EventEntity> participantOf) {
        this.participantOf = participantOf;
    }

    public Set<PilchaEntity> getOwnerOf() {
        return ownerOf;
    }

    public void setOwnerOf(Set<PilchaEntity> ownerOf) {
        this.ownerOf = ownerOf;
    }
}
