package br.com.mobile10.avaliasim.util;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class Alerts {

    public static boolean apagarItem;

    public static void toast(Activity activity, String message){
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public static void  snackbar(String mensagem, CoordinatorLayout coordinatorLayout) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, mensagem, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

}
