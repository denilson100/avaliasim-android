package br.com.mobile10.avaliasim.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denmont on 17/04/2018.
 */

public class ListEstados {

    public static List<String> getList() {
        List<String> list = new ArrayList<String>();
        list.add("RJ");
        list.add("SP");
        list.add("MG");
        list.add("ES");
        return list;
    }
}
