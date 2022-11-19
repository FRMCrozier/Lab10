package com.example.lab_10;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "1080p",
        "720p",
        "480p",
        "360p"
})


public class Video {
    private String _1080p; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    private String _720p; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    private String _480p; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    private String _360p; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression


    public String get1080p() {
        return _1080p;
    }

    public void set1080p(String _1080p) {
        this._1080p = _1080p;
    }


    public String get720p() {
        return _720p;
    }

    public void set720p(String _720p) {
        this._720p = _720p;
    }


    public String get480p() {
        return _480p;
    }

    public void set480p(String _480p) {
        this._480p = _480p;
    }


    public String get360p() {
        return _360p;
    }

    public void set360p(String _360p) {
        this._360p = _360p;
    }

    @Override
    public String toString() {
        return
                "1080p = '" + _1080p + "\n" +
                        "720p = '" + _720p + '\n' +
                        "480p = '" + _480p + '\n' +
                        "360p = '" + _360p;
    }
}
