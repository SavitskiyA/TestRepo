package com.ryj.models.filters;

import com.ryj.models.enums.Direction;
import com.ryj.models.enums.Sort;

import java.util.List;

/**
 * Created by andrey on 9/30/17.
 */
public class Filters {
  private String mJudgeFullName;
  private Integer mCourtId;
  private String mCourtType;
  private String mCourtTypeClient;
  private String mCourtName;
  private Integer mCityId;
  private Integer mRegionId;
  private List<String> mAffairs;
  private String mRegion;
  private String mCity;
  private boolean[] mAffairsBooleans;
  private boolean[] mCourtsBooleans;
  private Direction mDirection = Direction.ASC;
  private Sort mSorting = Sort.LAST_NAME;

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

  public boolean[] getCourtsBooleans() {
    return mCourtsBooleans;
  }

  public void setCourtsBooleans(boolean[] courtsBooleans) {
    this.mCourtsBooleans = courtsBooleans;
  }

  public String getCourtTypeClient() {
    return mCourtTypeClient;
  }

  public void setCourtTypeClient(String courtTypeClient) {
    this.mCourtTypeClient = courtTypeClient;
  }

  public String getJudgeFullName() {
    return mJudgeFullName;
  }

  public void setJudgeFullName(String judgeFullName) {
    this.mJudgeFullName = judgeFullName;
  }

  public Integer getCourtId() {
    return mCourtId;
  }

  public void setCourtId(Integer courtId) {
    this.mCourtId = courtId;
  }

  public Direction getDirection() {
    return mDirection;
  }

  public void setDirection(Direction direction) {
    this.mDirection = direction;
  }

  public Sort getSorting() {
    return mSorting;
  }

  public void setSorting(Sort sorting) {
    this.mSorting = sorting;
  }

  public String getCourtName() {
    return mCourtName;
  }

  public void setCourtName(String courtName) {
    this.mCourtName = courtName;
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
    this.setCourtsBooleans(null);
  }

  public void clearCourtType() {
    setCourtTypeClient(null);
    setCourtType(null);
    setCourtsBooleans(null);
  }

  public void clearRegion() {
    setRegion(null);
    setRegionId(null);
  }

  public void clearCity() {
    setCity(null);
    setCityId(null);
  }

  public void clearAffairs() {
    setAffairsBooleans(null);
    setAffairs(null);
  }
}
