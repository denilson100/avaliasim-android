package br.com.mobile10.avaliasim.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by denmont on 18/04/2018.
 */
public class MyDate implements Serializable {
    public int positive;
    public int negative;
    public LocalDateTime date;

    public MyDate(int positive, int negative) {
        this.positive = positive;
        this.negative = negative;
    }

    public MyDate(int positive, int negative, LocalDateTime date) {
        this.positive = positive;
        this.negative = negative;
        this.date = date;
    }
}
