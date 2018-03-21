/*
 * Quando botão é apertado, a exceção android.view.ViewRootImpl$CalledFromWrongThreadException:
 * é lançada. Apenas a thread de UI, que criou a view, pode mexer nas suas views.
 */

package br.ufpe.cin.if1001.threads;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ThreadSimples extends Activity {
    private static final String TAG = "SimpleThreading";

    private Bitmap mBitmap;
    private ImageView mIView;
    private TextView contadorToasts;
    private int mDelay = 5000;
    int toasts = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threads);
        mIView = findViewById(R.id.imageView);
        contadorToasts = findViewById(R.id.contadorToasts);

        final Button loadButton = findViewById(R.id.loadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadIcon();
            }
        });

        final Button otherButton = findViewById(R.id.otherButton);
        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toasts++;
                contadorToasts.setText(getString(R.string.contador_de_toasts) + toasts);
                Toast.makeText(getApplicationContext(), "Estou trabalhando... (" + toasts + ")", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadIcon() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(mDelay);
                } catch (InterruptedException e) {
                    Log.e(TAG, e.toString());
                }
                mBitmap = BitmapFactory.decodeResource(getResources(),
                        R.drawable.painter);

                // vai dar pau...
                mIView.setImageBitmap(mBitmap);
            }
        }).start();
    }
/*
                                                                          android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
                                                                              at android.view.ViewRootImpl.checkThread(ViewRootImpl.java:6094)
                                                                              at android.view.ViewRootImpl.requestLayout(ViewRootImpl.java:824)
                                                                              at android.view.View.requestLayout(View.java:16431)
                                                                              at android.view.View.requestLayout(View.java:16431)
                                                                              at android.view.View.requestLayout(View.java:16431)
                                                                              at android.view.View.requestLayout(View.java:16431)
                                                                              at android.view.View.requestLayout(View.java:16431)
                                                                              at android.widget.ImageView.setImageDrawable(ImageView.java:424)
                                                                              at android.widget.ImageView.setImageBitmap(ImageView.java:439)
                                                                              at br.ufpe.cin.if1001.threads.ThreadSimples$3.run(ThreadSimples.java:67)
                                                                              at java.lang.Thread.run(Thread.java:841)

 */



}
