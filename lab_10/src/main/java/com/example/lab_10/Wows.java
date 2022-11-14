package com.example.lab_10;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Wows implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Wow> wows;
    private static String url = "https://owen-wilson-wow-api.onrender.com/wows/random?results=10";

    public Wows() {
        this.wows = new ArrayList<>();
    }

    public List<Wow> getWows() {
        return wows;
    }

    public void setResults(ArrayList<Wow> wows) {
        this.wows = wows;
    }


    public void add(Wow currency) {
        wows.add(currency);
    }

    /**
     * Сортировка по наличию слова в строке.
     */
    public Wows filterByWord(String ccy) {
        Wows temp = new Wows();
        for (Wow word : this.wows) {
            if (word.getFullLine().toLowerCase().contains(ccy.toLowerCase()))
                temp.add(word);
        }
        return temp;
    }

    public static ArrayList<Wow> download() throws IOException {
        Wows wows = JGetter.loadByURL(url);
        List<Wow> w = wows.getWows();
        ArrayList<Wow> ww = new ArrayList<Wow>(w);
        return ww;
    }

}
