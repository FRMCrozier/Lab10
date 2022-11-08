package com.example.lab_10;

import java.io.Serializable;
import java.util.ArrayList;

public class Wows implements Serializable {
    private ArrayList<Wow> wows;

    public Wows() {
        this.wows = new ArrayList<>();
    }

    public ArrayList<Wow> getWows() {
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

}
