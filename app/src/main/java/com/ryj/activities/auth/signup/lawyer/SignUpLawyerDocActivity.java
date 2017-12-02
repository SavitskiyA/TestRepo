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
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ryj.Constants;
import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.activities.auth.signup.ThankYouActivity;
import com.ryj.models.Document;
import com.ryj.models.enums.RequestType;
import com.ryj.models.request.SignUpQuery;
import com.ryj.utils.IOUtils;
import com.ryj.utils.PermissionHelper;
import com.ryj.utils.RxUtils;
import com.ryj.utils.StringUtils;
import com.ryj.utils.ToastUtil;
import com.ryj.utils.UriUtils;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/** Created by andrey on 8/15/17. */
public class SignUpLawyerDocActivity extends BaseActivity {
  private static final String TAG = "SignUpLawyerDocActivity";
  private static final int REQUEST_PERMISSION_STORAGE = 1;
  private static final String EXTRA_BUNDLE = "bundle";
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;
  @Inject SignUpQuery mQuery;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.photo)
  SimpleDraweeView mPhoto;

  @BindView(R.id.make_photo)
  ImageButton mTakePhoto;

  @BindView(R.id.next)
  Button mNext;

  @BindView(R.id.doc_photo)
  TextView mDocPhoto;

  @BindString(R.string.text_doc_not_found)
  String mDocNotFound;

  @BindString(R.string.text_select_source)
  String mSelectSource;

  @BindString(R.string.text_camera_permissions)
  String mPermissions;

  public static void start(Context context) {
    Intent i = new Intent(context, SignUpLawyerDocActivity.class);
    context.startActivity(i);
  }

  private static void setDocumentPhoto(SignUpQuery query, Uri uri) {
    query.getUser().setDoc(new Document());
    query.getUser().getDoc().setFileUri(uri);
    query.getUser().getDoc().setFile(UriUtils.getFileNameFromUri(uri));
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup_lawyer_doc);
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
    PermissionHelper.requestPermissions(
        this,
        REQUEST_PERMISSION_STORAGE,
        () -> {
          EasyImage.openChooserWithGallery(
              SignUpLawyerDocActivity.this, mSelectSource, Constants.IMAGE_TYPE);
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
      EasyImage.openCamera(SignUpLawyerDocActivity.this, Constants.IMAGE_TYPE);
    } else {
      ToastUtil.show(mPermissions, false);
    }
  }

  @OnClick(R.id.next)
  public void onClickNext() {
    executeSignUp();
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
            setDocumentPhoto(mQuery, uri);
            mPhoto.setImageURI(uri);
            mNext.setEnabled(true);
            mDocPhoto.setVisibility(View.INVISIBLE);
          }

          @Override
          public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
            super.onImagePickerError(e, source, type);
            ToastUtil.show(e.getMessage(), false);
          }

          @Override
          public void onCanceled(EasyImage.ImageSource source, int type) {
            if (source == EasyImage.ImageSource.CAMERA) {
              File photoFile = EasyImage.lastlyTakenButCanceledPhoto(SignUpLawyerDocActivity.this);
              if (photoFile != null) {
                photoFile.delete();
              }
            }
          }
        });
  }

  private MultipartBody.Part getAvatarAttachment() {
    return IOUtils.toPart(Constants.PARAM_NAME_AVATAR_PHOTO, new File(mQuery.getUser().getAvatarUri().getPath()));
  }

  private MultipartBody.Part getDocAttachment() {
    return IOUtils.toPart(Constants.PARAM_NAME_DOC_PHOTO, new File(mQuery.getUser().getDoc().getFileUri().getPath()));
  }

  private Map<String, RequestBody> getUserPartMap() {
    return IOUtils.getDataAsMap(mQuery.getUser(), null);
  }

  private Map<String, RequestBody> getAccountPartMap() {
    return IOUtils.getDataAsMap(mQuery.getAccount(), null);
  }

  private Map<String, RequestBody> getSessionPartMap() {
    return IOUtils.getDataAsMap(mQuery.getSession(), null);
  }

  private void executeSignUp() {
    doRequest(
        mApi.signUpLawyer(
                getUserPartMap(),
                getAccountPartMap(),
                getSessionPartMap(),
                getAvatarAttachment(),
                getDocAttachment())
            .compose(bindUntilEvent(ActivityEvent.DESTROY))
            .compose(RxUtils.applySchedulers())
            .compose(
                RxUtils.applyBeforeAndAfter(
                    (disposable) ->
                        getSpinnerDialog()
                            .show(getSupportFragmentManager(), StringUtils.EMPTY_STRING),
                    () -> getSpinnerDialog().dismiss()))
            .subscribe(
                response -> {
                  ThankYouActivity.start(this);
                  finish();
                },
                throwable -> {
                  mErrorHandler.handleErrorByRequestType(throwable, this, RequestType.SIGN_UP_TEMP);
                }));
  }
}
