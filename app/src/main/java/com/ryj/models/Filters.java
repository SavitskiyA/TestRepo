package com.ryj.models;

import com.ryj.models.enums.Direction;
import com.ryj.models.enums.Sort;

import java.util.List;

/**
 * Created by andrey on 9/30/17.
 */

public class Filters {
  private String mCourtType;
  private String mCourtTypeClient;
  private Integer mCityId;
  private Integer mRegionId;
  private List<String> mAffairs;
  private String mRegion;
  private String mCity;
  private boolean[] mAffairsBooleans;

  public String getCourtType() {
    return mCourtType;
  }

  public void setCourtType(String courtType) {
    this.mCourtType = courtType;
  }

  public Integer getCityId() {
    return mCityId;
  }

  public void setCityId(Integer cityId) {
    this.mCityId = cityId;
  }

  public Integer getRegionId() {
    return mRegionId;
  }

  public void setRegionId(Integer regionId) {
    this.mRegionId = regionId;
  }

  public List<String> getAffairs() {
    return mAffairs;
  }

  public void setAffairs(List<String> affairs) {
    this.mAffairs = affairs;
  }

  public String getRegion() {
    return mRegion;
  }

  public void setRegion(String region) {
    this.mRegion = region;
  }

  public String getCity() {
    return mCity;
  }

  public void setCity(String city) {
    this.mCity = city;
  }

  public boolean[] getAffairsBooleans() {
    return mAffairsBooleans;
  }

  public void setAffairsBooleans(boolean[] affairsBooleans) {
    this.mAffairsBooleans = affairsBooleans;
  }

  public String getCourtTypeClient() {
    return mCourtTypeClient;
  }

  public void setCourtTypeClient(String courtTypeClient) {
    this.mCourtTypeClient = courtTypeClient;
  }

  public void clear() {
    this.setCityId(null);
    this.setRegionId(null);
    this.setAffairs(null);
    this.setRegion(null);
    this.setCity(null);
    this.setAffairs(null);
    this.setAffairsBooleans(null);
    this.setCourtTypeClient(null);
    this.setCourtType(null);
  }
}
