package com.ryj.models.filters;

import android.support.annotation.Nullable;

import com.ryj.models.enums.Direction;
import com.ryj.models.enums.Sort;

import java.util.List;

/** Created by andrey on 9/30/17. */
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
  private EditorState mState;

  @Nullable
  public EditorState getState() {
    return mState;
  }

  public String getCourtType() {
    return mCourtType;
  }

  public void setCourtType(String courtType) {
    mCourtType = courtType;
  }

  public Integer getCityId() {
    return mCityId;
  }

  public void setCityId(Integer cityId) {
    mCityId = cityId;
  }

  public Integer getRegionId() {
    return mRegionId;
  }

  public void setRegionId(Integer regionId) {
    mRegionId = regionId;
  }

  public List<String> getAffairs() {
    return mAffairs;
  }

  public void setAffairs(List<String> affairs) {
    mAffairs = affairs;
  }

  public String getRegion() {
    return mRegion;
  }

  public void setRegion(String region) {
    mRegion = region;
  }

  public String getCity() {
    return mCity;
  }

  public void setCity(String city) {
    mCity = city;
  }

  public boolean[] getAffairsBooleans() {
    return mAffairsBooleans;
  }

  public void setAffairsBooleans(boolean[] affairsBooleans) {
    mAffairsBooleans = affairsBooleans;
  }

  public boolean[] getCourtsBooleans() {
    return mCourtsBooleans;
  }

  public void setCourtsBooleans(boolean[] courtsBooleans) {
    mCourtsBooleans = courtsBooleans;
  }

  public String getCourtTypeClient() {
    return mCourtTypeClient;
  }

  public void setCourtTypeClient(String courtTypeClient) {
    mCourtTypeClient = courtTypeClient;
  }

  public String getJudgeFullName() {
    return mJudgeFullName;
  }

  public void setJudgeFullName(String judgeFullName) {
    mJudgeFullName = judgeFullName;
  }

  public Integer getCourtId() {
    return mCourtId;
  }

  public void setCourtId(Integer courtId) {
    mCourtId = courtId;
  }

  public Direction getDirection() {
    return mDirection;
  }

  public void setDirection(Direction direction) {
    mDirection = direction;
  }

  public Sort getSorting() {
    return mSorting;
  }

  public void setSorting(Sort sorting) {
    mSorting = sorting;
  }

  public String getCourtName() {
    return mCourtName;
  }

  public void setCourtName(String courtName) {
    mCourtName = courtName;
  }

  public void clear() {
    setCityId(null);
    setRegionId(null);
    setAffairs(null);
    setRegion(null);
    setCity(null);
    setAffairs(null);
    setAffairsBooleans(null);
    setCourtTypeClient(null);
    setCourtType(null);
    setCourtsBooleans(null);
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

  public void saveState() {
    mState =
        new EditorState(
            this,
            mJudgeFullName,
            mCourtId,
            mCourtType,
            mCourtTypeClient,
            mCourtName,
            mCityId,
            mRegionId,
            mAffairs,
            mRegion,
            mCity,
            mAffairsBooleans,
            mCourtsBooleans,
            mDirection,
            mSorting);
  }

  public void restore() {
    mState.restore();
  }

  private final class EditorState {
    private Filters mFilters;
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

    private EditorState(
        Filters filters,
        String judgeFullName,
        Integer courtId,
        String courtType,
        String courtTypeClient,
        String courtName,
        Integer cityId,
        Integer regionId,
        List<String> affairs,
        String region,
        String city,
        boolean[] affairsBooleans,
        boolean[] courtsBooleans,
        Direction direction,
        Sort sorting) {
      mFilters = filters;
      mJudgeFullName = judgeFullName;
      mCourtId = courtId;
      mCourtType = courtType;
      mCourtTypeClient = courtTypeClient;
      mCourtName = courtName;
      mCityId = cityId;
      mRegionId = regionId;
      mAffairs = affairs;
      mRegion = region;
      mCity = city;
      mAffairsBooleans = affairsBooleans;
      mCourtsBooleans = courtsBooleans;
      mDirection = direction;
      mSorting = sorting;
    }

    private void restore() {
      mFilters.setJudgeFullName(mJudgeFullName);
      mFilters.setCourtId(mCourtId);
      mFilters.setCourtType(mCourtType);
      mFilters.setCourtTypeClient(mCourtTypeClient);
      mFilters.setCourtName(mCourtName);
      mFilters.setCityId(mCityId);
      mFilters.setRegionId(mRegionId);
      mFilters.setAffairs(mAffairs);
      mFilters.setRegion(mRegion);
      mFilters.setCity(mCity);
      mFilters.setAffairsBooleans(mAffairsBooleans);
      mFilters.setCourtsBooleans(mCourtsBooleans);
      mFilters.setDirection(mDirection);
      mFilters.setSorting(mSorting);
    }
  }
}
