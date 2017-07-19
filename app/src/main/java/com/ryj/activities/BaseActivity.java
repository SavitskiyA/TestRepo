package com.ryj.activities;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ryj.App;
import com.ryj.di.ApplicationComponent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseActivity extends RxAppCompatActivity {

  public ApplicationComponent getComponent() {
    return App.get().getComponent();
  }

  @Nullable
  protected abstract Toolbar getToolbar();

  @Nullable
  protected abstract TextView getToolbarTitle();

  public void setToolbarBackArrowEnabled(boolean enabled) {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
      getSupportActionBar().setDisplayShowHomeEnabled(enabled);
    }
  }

  public void setDefaultDisplayShowTitleEnabled(boolean enabled) {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayShowTitleEnabled(enabled);
    }
  }

  public void showToast(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public PaintDrawable getBackgroundGradient(int[] colors, float[] points) {
    ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
      @Override
      public Shader resize(int width, int height) {
        LinearGradient linearGradient = new LinearGradient(0, 0, width, height,
                colors, points,
                Shader.TileMode.REPEAT);
        return linearGradient;
      }
    };

    PaintDrawable paint = new PaintDrawable();
    paint.setShape(new RectShape());
    paint.setShaderFactory(shaderFactory);
    return paint;
  }
}
