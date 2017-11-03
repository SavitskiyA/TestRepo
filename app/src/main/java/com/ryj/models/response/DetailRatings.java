package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 11/2/17.
 */

public class DetailRatings {
  @SerializedName("di")
  @Expose
  private Double mDi;
  @SerializedName("rd")
  @Expose
  private Double mRd;
  @SerializedName("bais")
  @Expose
  private Double mBais;
  @SerializedName("ftit")
  @Expose
  private Double mFtit;
  @SerializedName("pnel")
  @Expose
  private Double mPnel;
  @SerializedName("croot")
  @Expose
  private Double mCroot;
  @SerializedName("material_law")
  @Expose
  private Double mMaterialLaw;
  @SerializedName("legal_justification")
  @Expose
  private Double mLegalJustification;

  public Double getDi() {
    return mDi;
  }

  public void setDi(Double di) {
    this.mDi = di;
  }

  public Double getRd() {
    return mRd;
  }

  public void setRd(Double rd) {
    this.mRd = rd;
  }

  public Double getBais() {
    return mBais;
  }

  public void setBais(Double bais) {
    this.mBais = bais;
  }

  public Double getFtit() {
    return mFtit;
  }

  public void setFtit(Double ftit) {
    this.mFtit = ftit;
  }

  public Double getPnel() {
    return mPnel;
  }

  public void setPnel(Double pnel) {
    this.mPnel = pnel;
  }

  public Double getCroot() {
    return mCroot;
  }

  public void setCroot(Double croot) {
    this.mCroot = croot;
  }

  public Double getMaterialLaw() {
    return mMaterialLaw;
  }

  public void setMaterialLaw(Double materialLaw) {
    this.mMaterialLaw = materialLaw;
  }

  public Double getLegalJustification() {
    return mLegalJustification;
  }

  public void setLegalJustification(Double legalJustification) {
    this.mLegalJustification = legalJustification;
  }

}
