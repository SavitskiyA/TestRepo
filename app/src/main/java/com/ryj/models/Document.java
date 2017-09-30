package com.ryj.models;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 8/30/17.
 */

public class Document implements Reflectable {
  private final static String MAP_PREFIX = "person_doc_photo_attributes][";
  @SerializedName("file")
  @Expose
  private String mFile;

  private Uri mDocUri;

  public Document() {
  }

  public Document(CharSequence file) {
    this.mFile = file.toString().trim();
  }

  public String getFile() {
    return mFile;
  }

  public void setFile(CharSequence file) {
    this.mFile = file.toString().trim();
  }

  public Uri getFileUri() {
    return mDocUri;
  }

  public void setFileUri(Uri docUri) {
    this.mDocUri = docUri;
  }

  @NonNull
  @Override
  public String getFieldMapPrefix() {
    return MAP_PREFIX;
  }
}
