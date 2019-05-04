package com.teleinfgroup.ErrorDetectionAlgorithms;

public class CRCType {
    private String nazwa;
    private int polynomial;
    private int keyLength;

    public CRCType(String nazwa, int polynomial, int keyLength) {
        this.nazwa = nazwa;
        this.polynomial = polynomial;
        this.keyLength = keyLength;
    }

    String getNazwa() {
        return nazwa;
    }

    void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    int getPolynomial() {
        return polynomial;
    }

    void setPolynomial(int polynomial) {
        this.polynomial = polynomial;
    }

    int getKeyLength() {
        return keyLength;
    }

    void setKeyLength(int keyLength) {
        this.keyLength = keyLength;
    }
}