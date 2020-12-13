package com.mmoi.javauniversity.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String refImgName;
    private String distImgName1;
    private String distImgName2;
    private String chosen;
    private Date start;
    private Date stop;
    private Long InSessionId;


    @ManyToOne (fetch=FetchType.LAZY,
            cascade=CascadeType.ALL)
    private Session session;

    private String userCookie;

    /*private Long session_id;*/

    public SessionEntity() {
    }

    public String getRefImgName() {
        return refImgName;
    }

    public void setRefImgName(String refImgName) {
        this.refImgName = refImgName;
    }

    public String getDistImgName1() {
        return distImgName1;
    }

    public void setDistImgName1(String distImgName1) {
        this.distImgName1 = distImgName1;
    }

    public String getDistImgName2() {
        return distImgName2;
    }

    public void setDistImgName2(String distImgName2) {
        this.distImgName2 = distImgName2;
    }

    public Long getId() {
        return id;
    }

    public String getChosen() {
        return chosen;
    }

    public void setChosen(String chosen) {
        this.chosen = chosen;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Long getInSessionId() {
        return InSessionId;
    }

    public void setInSessionId(Long inSessionId) {
        InSessionId = inSessionId;
    }

    public String getUserCookie() {
        return userCookie;
    }

    public void setUserCookie(String userCookie) {
        this.userCookie = userCookie;
    }

    /*public Long getSession_id() {
        return session_id;
    }

    public void setSession_id(Long session_id) {
        this.session_id = session_id;
    }*/
}