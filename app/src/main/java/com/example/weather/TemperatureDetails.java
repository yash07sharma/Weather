package com.example.weather;

import com.google.gson.annotations.SerializedName;

public class TemperatureDetails {
    public class Metric {
        @SerializedName("Value")
        private Double temp;
        @SerializedName("Unit")
        private String unit;

        public Metric(Double temp, String unit) {
            this.temp = temp;
            this.unit = unit;
        }

        public Double getTemp() {
            return temp;
        }

        public String getUnit() {
            return unit;
        }
    }
    @SerializedName("Metric")
    private Metric metric;

    public TemperatureDetails(Metric metric) {
        this.metric = metric;
    }

    public Metric getMetric() {
        return metric;
    }
}
