package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryj.models.enums.UserType;

import java.util.List;

public class UserResponse {

  @SerializedName("id")
  @Expose
  private Integer mId;

  @SerializedName("first_name")
  @Expose
  private String mFirstName;

  @SerializedName("last_name")
  @Expose
  private String mLastName;

  @SerializedName("phone")
  @Expose
  private String mPhone;

  @SerializedName("affairs")
  @Expose
  private List<String> mAffairs;

  @SerializedName("specializations")
  @Expose
  private List<String> mSpecializations;

  @SerializedName("type")
  @Expose
  private String mType;

  @SerializedName("certification_number")
  @Expose
  private String mCertificationNumber;

  @SerializedName("company")
  @Expose
  private String mCompany;

  @SerializedName("avatar_urls")
  @Expose
  private Avatar mAvatar;

  public Integer getId() {
    return mId;
  }

  public void setId(Integer id) {
    mId = id;
  }

  public String getFirstName() {
    return mFirstName;
  }

  public void setFirstName(String firstName) {
    mFirstName = firstName;
  }

  public String getLastName() {
    return mLastName;
  }

  public void setLastName(String lastName) {
    mLastName = lastName;
  }

  public String getPhone() {
    return mPhone;
  }

  public void setPhone(String phone) {
    mPhone = phone;
  }

  public List<String> getAffairs() {
    return mAffairs;
  }

  public void setAffairs(List<String> affairs) {
    mAffairs = affairs;
  }

  public List<String> getSpecializations() {
    return mSpecializations;
  }

  public void setSpecializations(List<String> specializations) {
    this.mSpecializations = specializations;
  }

  public UserType getType() {
    return UserType.valueOf(mType.toUpperCase());
  }

  public void setType(String type) {
    mType = type;
  }

  public String getCertificationNumber() {
    return mCertificationNumber;
  }

  public void setCertificationNumber(String certificationNumber) {
    mCertificationNumber = certificationNumber;
  }

  public String getCompany() {
    return mCompany;
  }

  public void setCompany(String company) {
    mCompany = company;
  }

  public Avatar getAvatar() {
    return mAvatar;
  }

  public void setAvatar(Avatar avatar) {
    mAvatar = avatar;
  }
}
