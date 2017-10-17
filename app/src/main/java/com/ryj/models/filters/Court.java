package com.ryj.models.filters;

import java.util.List;

/** Created by andrey on 10/17/17. */
public class Court {
  private Integer mId;
  private String mKind;
  private String mName;
  private Region mRegion;
  private City mCity;
  private List<Judge> mJudges;

  public Integer getId() {
    return mId;
  }

  public void setId(Integer id) {
    this.mId = id;
  }

  public String getKind() {
    return mKind;
  }

  public void setKind(String kind) {
    this.mKind = kind;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  public List<Judge> getJudges() {
    return mJudges;
  }

  public void setJudges(List<Judge> judges) {
    this.mJudges = judges;
  }

  public Region getRegion() {
    return mRegion;
  }

  public void setRegion(Region region) {
    this.mRegion = region;
  }

  public City getCity() {
    return mCity;
  }

  public void setCity(City city) {
    this.mCity = city;
  }
}
