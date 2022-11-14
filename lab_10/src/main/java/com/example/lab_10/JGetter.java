package com.example.lab_10;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JGetter {
    public static Wows loadByURL(String url) throws IOException {
        StringBuilder jsonIn = new StringBuilder();
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            int cp;
            while ((cp = rd.read()) != -1) {
                jsonIn.append((char) cp);
            }
        } finally {
            is.close();
        }
        ObjectMapper om = new ObjectMapper();
        ArrayList wows = om.readValue(jsonIn.toString().strip(), om.getTypeFactory().constructCollectionType(ArrayList.class, Wow.class));
        Wows w = new Wows();
        w.setResults(wows);

        return w;

    }
}