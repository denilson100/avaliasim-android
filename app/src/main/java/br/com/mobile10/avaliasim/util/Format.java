package br.com.mobile10.avaliasim.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by denmont on 16/04/2018.
 */

public class Format {

    public static String Date(Long timeStamp) {
        long val = 1346524199000l;
        Date date = new Date(timeStamp);
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        return df2.format(date);
    }

    /**
     * Convert a String para dada.
     * @param dateString
     * @return
     */
    public static Date convertStringInDate(String dateString) {
        Date dateFinal = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            dateFinal = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFinal;
    }
}
