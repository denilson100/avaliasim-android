package br.com.mobile10.avaliasim.util;

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
}
