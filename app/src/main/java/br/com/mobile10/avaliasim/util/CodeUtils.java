package br.com.mobile10.avaliasim.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class CodeUtils {

    public static URL buildURL(String textUrl) {
        try {
            return new URL(textUrl);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return null;
    }

    //Retorna a activity de um contexto informado
    public static Activity getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity)
                return (Activity) context;
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
