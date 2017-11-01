package com.ryj.activities.settings;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ryj.Constants;
import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.storage.prefs.Prefs;
import com.ryj.utils.PermissionHelper;
import com.ryj.utils.ToastUtil;
import com.ryj.utils.UriUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by andrey on 11/1/17.
 */

public class EditProfileJudgeActivity extends BaseActivity {
  private static final int REQUEST_PERMISSION_STORAGE = 1;
  @Inject
  Prefs mPrefs;

  @BindView(R.id.toolbar)
  Toolbar mToolBar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.photo)
  SimpleDraweeView mPhoto;

  @BindView(R.id.email)
  TextView mEmail;

  @BindView(R.id.phone)
  EditText mPhone;

  @BindView(R.id.categories)
  EditText mCategories;

  @BindString(R.string.text_camera_permissions)
  String mPermissions;

  public static void start(Context context) {
    Intent i = new Intent(context, EditProfileJudgeActivity.class);
    context.startActivity(i);
  }

  @Nullable
  @Override
  protected Toolbar getToolbar() {
    return mToolBar;
  }

  @Nullable
  @Override
  protected TextView getToolbarTitle() {
    return mTitle;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_profile_judge);
    getComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(mToolBar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    setSoftInputMode();
  }

  @OnClick(R.id.photo)
  void onClick() {
    PermissionHelper.requestPermissions(
            this,
            REQUEST_PERMISSION_STORAGE,
            () -> {
              EasyImage.openCamera(EditProfileJudgeActivity.this, Constants.IMAGE_TYPE);
            },
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE);
  }

  @Override
  public void onRequestPermissionsResult(
          int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    boolean allPermissionsGranted = PermissionHelper.isAllPermissionsGranted(grantResults);
    if (requestCode == REQUEST_PERMISSION_STORAGE && allPermissionsGranted) {
      EasyImage.openCamera(EditProfileJudgeActivity.this, Constants.IMAGE_TYPE);
    } else {
      ToastUtil.show(mPermissions, false);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    EasyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            this,
            new DefaultCallback() {
              @Override
              public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                if ((source == EasyImage.ImageSource.GALLERY
                        || source == EasyImage.ImageSource.DOCUMENTS)
                        && !imageFile.getName().toLowerCase().contains(UriUtils.EXTENSION_JPG)) {
                  File renamedFile = new File(imageFile.getAbsolutePath() + UriUtils.EXTENSION_JPG);
                  imageFile.renameTo(renamedFile);
                  imageFile = renamedFile;
                }
                Uri uri = Uri.fromFile(imageFile);
                if (uri == null) {
                  return;
                }
//                mPhoto.setUr uri);
                CropImage.activity(uri)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setAllowFlipping(false)
                        .setAllowRotation(false)
                        .setFixAspectRatio(true)
                        .start(EditProfileJudgeActivity.this);
              }

              @Override
              public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                ToastUtil.show(e.getMessage(), false);
              }

              @Override
              public void onCanceled(EasyImage.ImageSource source, int type) {
                if (source == EasyImage.ImageSource.CAMERA) {
                  File photoFile =
                          EasyImage.lastlyTakenButCanceledPhoto(EditProfileJudgeActivity.this);
                  if (photoFile != null) {
                    photoFile.delete();
                  }
                }
              }
            });
    if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
      CropImage.ActivityResult result = CropImage.getActivityResult(data);
      if (resultCode == RESULT_OK) {
        Uri uri = result.getUri();
        mPhoto.setImageURI(uri);
      } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
        Exception error = result.getError();
        ToastUtil.show(error.getMessage(), false);
      }
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      case R.id.action_check:
//
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_check, menu);
    return true;
  }
}
