package com.ryj.activities.auth.signup.lawyer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryj.Constants;
import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.models.request.SignUpQuery;
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
 * Created by andrey on 7/11/17.
 */

public class SignUpLawyerAvatarActivity extends BaseActivity {
  private final static String TAG = "SignUpLawyerAvatarActivity";
  private static final int REQUEST_PERMISSION_STORAGE = 1;
  @Inject
  SignUpQuery mQuery;
  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.title)
  TextView mTitle;
  @BindView(R.id.photo)
  ImageView mPhoto;
  @BindView(R.id.make_photo)
  ImageButton mTakePhoto;
  @BindView(R.id.next)
  Button mNext;
  @BindView(R.id.doc_photo)
  TextView mYourPhoto;
  @BindString(R.string.text_avatar_not_found)
  String mAvatarNotFound;
  @BindString(R.string.text_camera_permissions)
  String mPermissions;

  public static void start(Context context) {
    Intent i = new Intent(context, SignUpLawyerAvatarActivity.class);
    context.startActivity(i);
  }

  private static void setAvatar(SignUpQuery query, Uri uri) {
    query.getUser().setAvatarUri(uri);
    query.getUser().setAvatar(UriUtils.getFileNameFromUri(uri));
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup_lawyer_avatar);
    getComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    setSupportActionBar(mToolbar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
  }

  @Nullable
  @Override
  protected Toolbar getToolbar() {
    return mToolbar;
  }

  @Nullable
  @Override
  protected TextView getToolbarTitle() {
    return mTitle;
  }

  @OnClick(R.id.make_photo)
  public void onClickMakePhoto() {
    PermissionHelper.requestPermissions(this,
            REQUEST_PERMISSION_STORAGE, () -> {
              EasyImage.openCamera(SignUpLawyerAvatarActivity.this, Constants.IMAGE_TYPE);
            }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    boolean allPermissionsGranted = PermissionHelper.isAllPermissionsGranted(grantResults);
    if (requestCode == REQUEST_PERMISSION_STORAGE && allPermissionsGranted) {
      EasyImage.openCamera(SignUpLawyerAvatarActivity.this, Constants.IMAGE_TYPE);
    } else {
      ToastUtil.show(mPermissions, false);
    }
  }

  @OnClick(R.id.next)
  public void onClickNext() {
    SignUpLawyerDocActivity.start(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
      @Override
      public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
        if ((source == EasyImage.ImageSource.GALLERY
                || source == EasyImage.ImageSource.DOCUMENTS)
                && !imageFile.getName()
                .toLowerCase()
                .contains(UriUtils.EXTENSION_JPG)) {
          File renamedFile = new File(
                  imageFile.getAbsolutePath() + UriUtils.EXTENSION_JPG);
          imageFile.renameTo(renamedFile);
          imageFile = renamedFile;
        }
        Uri uri = Uri.fromFile(imageFile);
        if (uri == null) {
          return;
        }
        setAvatar(mQuery, uri);
        CropImage.activity(uri).setCropShape(CropImageView.CropShape.OVAL)
                .setAllowFlipping(false)
                .setAllowRotation(false)
                .setFixAspectRatio(true)
                .start(SignUpLawyerAvatarActivity.this);
      }

      @Override
      public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
        super.onImagePickerError(e, source, type);
        ToastUtil.show(e.getMessage(), false);
      }

      @Override
      public void onCanceled(EasyImage.ImageSource source, int type) {
        if (source == EasyImage.ImageSource.CAMERA) {
          File photoFile = EasyImage.lastlyTakenButCanceledPhoto(SignUpLawyerAvatarActivity.this);
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
        mNext.setEnabled(true);
        mYourPhoto.setVisibility(View.INVISIBLE);
      } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
        Exception error = result.getError();
        ToastUtil.show(error.getMessage(), false);
      }
    }
  }
}

