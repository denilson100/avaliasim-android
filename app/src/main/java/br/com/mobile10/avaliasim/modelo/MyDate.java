package br.com.mobile10.avaliasim.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by denmont on 18/04/2018.
 */

public class MyDate implements Serializable {
    public int positive;
    public int negative;
    public Date date;

    public MyDate(int positive, int negative) {
        this.positive = positive;
        this.negative = negative;
    }

    public MyDate(int positive, int negative, Date date) {
        this.positive = positive;
        this.negative = negative;
        this.date = date;
    }
}
