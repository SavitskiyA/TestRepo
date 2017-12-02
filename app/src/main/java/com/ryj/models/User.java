package com.ryj.models;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryj.models.enums.Affairs;
import com.ryj.models.enums.Specializations;
import com.ryj.models.enums.UserType;

import java.util.List;

public class User implements Reflectable {
  private static final String MAP_PREFIX = "user[";

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

  @SerializedName("type")
  @Expose
  private UserType mType;

  @SerializedName("affairs")
  @Expose
  private List<Affairs> mAffairs;

  @SerializedName("specializations")
  @Expose
  private List<Specializations> mSpecializations;

  @SerializedName("certification_number")
  @Expose
  private String mCertNumber;

  @SerializedName("company")
  @Expose
  private String mCompany;

  @SerializedName("avatar")
  @Expose
  private String mAvatar;

  @SerializedName("person_doc_photo_attributes")
  @Expose
  private Document mDoc;

  private Uri mAvatarUri;

  public User() {}

  public User(CharSequence firstName, CharSequence lastName) {
    mFirstName = firstName.toString().trim();
    mLastName = lastName.toString().trim();
  }

  public Integer getId() {
    return mId;
  }

  public void setId(Integer id) {
    mId = id;
  }

  public String getFirstName() {
    return mFirstName;
  }

  public void setFirstName(CharSequence firstName) {
    mFirstName = firstName.toString().trim();
  }

  public String getLastName() {
    return mLastName;
  }

  public void setLastName(CharSequence lastName) {
    mLastName = lastName.toString().trim();
  }

  public String getPhone() {
    return mPhone;
  }

  public void setPhone(CharSequence phone) {
    mPhone = phone.toString().trim();
  }

  public UserType getType() {
    return mType;
  }

  public void setType(UserType type) {
    mType = type;
  }

  public List<Affairs> getAffairs() {
    return mAffairs;
  }

  public void setAffairs(List<Affairs> affairs) {
    mAffairs = affairs;
  }

  public List<Specializations> getSpecializations() {
    return mSpecializations;
  }

  public void setSpecializations(List<Specializations> specializations) {
    mSpecializations = specializations;
  }

  public String getCertNumber() {
    return mCertNumber;
  }

  public void setCertNumber(CharSequence certNumber) {
    mCertNumber = certNumber.toString().trim();
  }

  public String getCompany() {
    return mCompany;
  }

  public void setCompany(CharSequence company) {
    mCompany = company.toString().trim();
  }

  public String getAvatar() {
    return mAvatar;
  }

  public void setAvatar(String avatar) {
    mAvatar = avatar;
  }

  public Document getDoc() {
    return mDoc;
  }

  public void setDoc(Document docPhoto) {
    mDoc = docPhoto;
  }

  public Uri getAvatarUri() {
    return mAvatarUri;
  }

  public void setAvatarUri(Uri avatarUri) {
    mAvatarUri = avatarUri;
  }

  @NonNull
  @Override
  public String getFieldMapPrefix() {
    return MAP_PREFIX;
  }
}
