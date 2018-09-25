package br.com.mobile10.avaliasim.util;

import java.util.ArrayList;
import java.util.List;

public class Support {

    /**
     * Conversor de list em array
     * @param text separado por ;
     * @return
     */
    public static List<String> getFeaturesList(String text) {
        List<String> list = new ArrayList<String>();
        String[] textoSeparado = text.split(";\\s");
        for (int i=0; i < textoSeparado.length; i++) {
            list.add(textoSeparado[i]);
        }
        String[] stringArray = list.toArray(new String[0]);

        return list;
    }

}
