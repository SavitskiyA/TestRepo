package com.ryj.utils;


import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.URLSpan;

/**
 * Created by andrey on 7/11/17.
 */

public class URLSpanNoUnderline extends URLSpan {
  public URLSpanNoUnderline(String url) {
    super(url);
  }

  public static void removeUnderlines(Spannable p_Text) {
    URLSpan[] mSpans = p_Text.getSpans(0, p_Text.length(), URLSpan.class);

    for (URLSpan span : mSpans) {
      int start = p_Text.getSpanStart(span);
      int end = p_Text.getSpanEnd(span);
      p_Text.removeSpan(span);
      span = new URLSpanNoUnderline(span.getURL());
      p_Text.setSpan(span, start, end, 0);
    }
  }

  public void updateDrawState(TextPaint p_DrawState) {
    super.updateDrawState(p_DrawState);
    p_DrawState.setUnderlineText(false);
  }
}
