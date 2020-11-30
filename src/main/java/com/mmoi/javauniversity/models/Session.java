package com.mmoi.javauniversity.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY,
        mappedBy = "session",
        cascade = CascadeType.ALL)
    private List<SessionEntity> sessions;

    public Session() {
    }

    public List<SessionEntity> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionEntity> sessions) {
        this.sessions = sessions;
    }

    public void addSessionEntity(SessionEntity sessionEntity)
    {
        sessions.add(sessionEntity);
    }

    public int getSessionSize() {
        return sessions.size();
    }

    public Long getId() {
        return id;
    }
}
