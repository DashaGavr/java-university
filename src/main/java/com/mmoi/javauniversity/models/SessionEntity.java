package com.mmoi.javauniversity.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String refImgName;
    private String distImgName1;
    private String distImgName2;


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

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}