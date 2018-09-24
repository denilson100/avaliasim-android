package br.com.mobile10.avaliasim.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.interfaces.OnCompleteOperationListener;

public class InterfaceUtils {

    public static ProgressDialog showProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.CustomDialogStyle);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        return progressDialog;
    }

    public static void hideProgressDialog(ProgressDialog pd) {
        pd.hide();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null)
            view = new View(activity);

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void loadImageFromUrl(URL url, OnCompleteOperationListener onCompleteOperationListener) {
        new Thread(() -> {
            try {
                Bitmap bmp = BitmapFactory.decodeStream((InputStream) url.getContent());
                onCompleteOperationListener.onCompletion(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void showSnackbar(CoordinatorLayout coordinatorLayout, String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void replaceFragment(FragmentManager fragmentManager, int frameLayoutId, Fragment newFragment) {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(frameLayoutId, newFragment)
                .commit();
    }
}
