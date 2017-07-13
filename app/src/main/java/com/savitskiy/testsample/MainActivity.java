package com.savitskiy.testsample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {
  @BindView(R.id.email)
  EditText mEmail;
  @BindView(R.id.password)
  EditText mPassword;
  @BindView(R.id.signin)
  Button mSignIn;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    formValidation();
  }

  private void formValidation() {
    Observable<String> emailObservable = RxEditText.getTextWatcherObservable(mEmail);
    Observable<String> passwordObservable = RxEditText.getTextWatcherObservable(mPassword);
    Observable.combineLatest(emailObservable, passwordObservable, new BiFunction<String, String, Boolean>() {
      @Override
      public Boolean apply(@io.reactivex.annotations.NonNull String s, @io.reactivex.annotations.NonNull String s2) throws Exception {
        if (!emailValidator(s) || s2.toCharArray().length < 6) return false;
        else return true;
      }
    }).subscribe(new Consumer<Boolean>() {
      @Override
      public void accept(@io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {
        mSignIn.setEnabled(aBoolean);
      }
    });
  }

  private boolean emailValidator(String email) {
    Pattern pattern;
    Matcher matcher;
    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    pattern = Pattern.compile(EMAIL_PATTERN);
    matcher = pattern.matcher(email);
    return matcher.matches();
  }

  public static class RxEditText {
    public static io.reactivex.Observable<String> getTextWatcherObservable(@NonNull final EditText editText) {
      final PublishSubject<String> subject = PublishSubject.create();
      editText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
          subject.onNext(s.toString());
        }
      });
      return subject;
    }
  }
}
