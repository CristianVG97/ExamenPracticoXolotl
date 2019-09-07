package com.example.examenpracticoxolotl.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonResult {

    public ResultProducts getPlpResults() {
        return plpResults;
    }

    public void setPlpResults(ResultProducts plpResults) {
        this.plpResults = plpResults;
    }

    @SerializedName("plpResults")
    @Expose
    private ResultProducts  plpResults;
}
