package com.ryj.activities.auth.signup.lawyer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.utils.PermissionHelper;
import com.ryj.utils.ToastUtil;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by andrey on 8/5/17.
 */

public class SignUpLawyerActivityPhoto extends BaseActivity {
  private final static String TAG = "SignUpLawyerActivityPhoto";
  private final static int REQUEST_CAMERA = 1;
  private final static int REQUEST_FILE = 2;
  private static final int REQUEST_PERMISSION_STORAGE = 0;
  @Inject
  Api mApi;
  @Inject
  ErrorHandler mErrorHandler;
  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.title)
  TextView mTitle;
  @BindView(R.id.photo)
  ImageView mPhoto;
  @BindView(R.id.make_photo)
  ImageButton mTakePhoto;
  @BindView(R.id.ok)
  Button mOk;
  private String mURI;
  private int mPhotoWidth;
  private int mPhotoHeight;
  private CropImage.ActivityBuilder mCropActivityBuilder = CropImage.activity();

  public static void start(Context context) {
    Intent i = new Intent(context, SignUpLawyerActivityPhoto.class);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup_lawyer_take_photo);
    getComponent().inject(this);
    ButterKnife.bind(this);
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
    startCropActivityt();
//    PermissionHelper.requestPermissions(this, REQUEST_PERMISSION_STORAGE, new PermissionHelper.PermissionsChecker() {
//      @Override
//      public void allPermissionsGranted() {
//        getImageFromExternals();
//      }
//    }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
  }

  private void getImageFromExternals() {
    EasyImage.openChooserWithGallery(this, "Choose smth", 1);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

//    EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
//      @Override
//      public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
//        if ((source == EasyImage.ImageSource.GALLERY
//                || source == EasyImage.ImageSource.DOCUMENTS)
//                && !imageFile.getName()
//                .toLowerCase()
//                .contains(UriUtils.EXTENSION_JPG)) {
//          File renamedFile = new File(
//                  imageFile.getAbsolutePath() + UriUtils.EXTENSION_JPG);
//          imageFile.renameTo(renamedFile);
//          imageFile = renamedFile;
//        }
//        Uri avatarUri = Uri.fromFile(imageFile);

//      }
//
//      @Override
//      public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
//        super.onImagePickerError(e, source, type);
//        ToastUtil.show(e.getMessage(), false);
//      }
//
//      @Override
//      public void onCanceled(EasyImage.ImageSource source, int type) {
//        if (source == EasyImage.ImageSource.CAMERA) {
//          File photoFile = EasyImage.lastlyTakenButCanceledPhoto(SignUpLawyerActivityPhoto.this);
//          if (photoFile != null) photoFile.delete();
//        }
//      }
//    });


    if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
      CropImage.ActivityResult result = CropImage.getActivityResult(data);
      if (resultCode == RESULT_OK) {
        Uri resultUri = result.getUri();
        mPhoto.setImageURI(resultUri);
      } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
        Exception error = result.getError();
        ToastUtil.show(error.getMessage(), false);
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    boolean allPermissionsGranted = PermissionHelper.allPermissionsGranted(grantResults);
    if (requestCode == REQUEST_PERMISSION_STORAGE) {
      if (allPermissionsGranted) {
        startCropActivityt();
      } else {
        ToastUtil.show("Please provide us a permission to write external storage", false);
      }
    }
  }

  private void startCropActivityt() {
    mCropActivityBuilder.setCropShape(CropImageView.CropShape.OVAL)
            .setAllowFlipping(false)
            .setAllowRotation(false)
            .setFixAspectRatio(true)
            .setBackgroundColor(R.color.colorBlueLight)
            .start(SignUpLawyerActivityPhoto.this);
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    mPhotoWidth = mPhoto.getWidth();
    mPhotoHeight = mPhoto.getHeight();
  }
}
