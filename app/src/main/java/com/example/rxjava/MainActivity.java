package com.example.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = "myLogs";
    private Observable<String> myObservable;
//    private Observer<String> myObserver;
    private DisposableObserver<String> myObserver;
    private Disposable disposable;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeText();
            }
        });

//        myObserver = new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                disposable = d;
//                Log.i(TAG, "onSubscribe invoked");
//            }
//
//            @Override
//            public void onNext(String s) {
//                textView.setText(s);
//                Log.i(TAG, "onNext invoked");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i(TAG, "onError invoked");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i(TAG, "onComplete invoked");
//            }
//        };


    }

    public void changeText() {
        textView = findViewById(R.id.textViewGreeting);
        myObservable = Observable.just(fakeAPICall());

        myObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        myObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                textView.setText(s);
                Log.i(TAG, "onNext invoked");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError invoked");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete invoked");
            }
        };

        myObservable.subscribe(myObserver);
    }

    public String fakeAPICall() {
//      for (int x = 200000; x > 0; x--) {
//        Log.d("count", String.valueOf(x));
//      }
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {}
      return "Hello from RxJava";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        disposable.dispose();
        myObserver.dispose();
    }
}
