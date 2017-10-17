package com.ryj.models.filters;

import com.ryj.models.enums.Direction;
import com.ryj.models.enums.Sort;

/** Created by andrey on 10/17/17. */
public class Filter {
  private Judge mJudge;
  private Court mCourt;
  private Direction mDirection = Direction.ASC;
  private Sort mSorting = Sort.LAST_NAME;

  public Court getCourt() {
    return mCourt;
  }

  public void setCourt(Court court) {
    this.mCourt = court;
  }

  public Judge getJudge() {
    return mJudge;
  }

  public void setJudge(Judge judge) {
    this.mJudge = judge;
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
}
