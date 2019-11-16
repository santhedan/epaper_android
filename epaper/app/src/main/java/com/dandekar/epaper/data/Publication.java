package com.dandekar.epaper.data;

public enum Publication {
    TheTimesOfIndia {
        public String toString() {
            return "The Times of India";
        }
    },
    Mirror {
        public String toString() {
            return "The Mirror";
        }
    },
    TheEconomicTimes {
        public String toString() {
            return "The Economic Times";
        }
    }
}