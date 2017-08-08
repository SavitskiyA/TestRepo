package com.ryj.models.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 7/27/17.
 */

public enum Affairs {
  @SerializedName("admin_violation_affairs")ADMIN {
    @Override
    public String toString() {
      return "admin_violation_affairs";
    }
  },
  @SerializedName("economic_affairs")ECONOMIC {
    @Override
    public String toString() {
      return "economic_affairs";
    }
  },
  @SerializedName("criminal_affairs")CRIMINAL {
    @Override
    public String toString() {
      return "criminal_affairs";
    }
  },
  @SerializedName("urgent_lawsuits")URGENT {
    @Override
    public String toString() {
      return "urgent_lawsuits";
    }
  },
  @SerializedName("administrative_affairs")ADMINISTRATIVE {
    @Override
    public String toString() {
      return "administrative_affairs";
    }
  },
  @SerializedName("civil_affairs")CIVIL {
    @Override
    public String toString() {
      return "civil_affairs";
    }
  }

}
