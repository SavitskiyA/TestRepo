package com.ryj.interfaces;

import com.ryj.models.enums.VoteType;

/** Created by andrey on 11/15/17. */
public interface OnVoteClickListener {
  void vote(VoteType type, int position);

  void deleteVote(int position);
}
