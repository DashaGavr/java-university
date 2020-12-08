package com.mmoi.javauniversity.models;

import org.springframework.boot.context.properties.bind.DefaultValue;

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
    private List<SessionEntity> sessions = new ArrayList<>();

    private long sessionCount = 0;

    public Session() {
    }

    public List<SessionEntity> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionEntity> sessions) {
        this.sessions = sessions;
    }

    public void addSessionEntity(SessionEntity sE)
    {
        sessions = new ArrayList<>();
        sessions.add(sE);
        sessionCount += 1;
    }

    public int getSessionSize() {
        return sessions.size();
    }

    public Long getId() {
        return id;
    }


    public long getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(long sessionCount) {
        this.sessionCount = sessionCount;
    }
}
