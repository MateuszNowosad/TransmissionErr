package com.teleinfgroup.ErrorDetectionAlgorithms;

import java.util.ArrayList;

public class CRC extends ErrorDetectionAlgorithm {

    private int polynomial;
    private int keyLength;

    public CRC(int polynomial, int keyLength) {
        this.polynomial = polynomial;
        this.keyLength = keyLength;
    }

    public Integer getPolynomial() {
        return polynomial;
    }

    public void setPolynomial(Integer polynomial) {
        this.polynomial = polynomial;
    }

    public int getKeyLength() {
        return keyLength;
    }

    public void setKeyLength(int keyLength) {
        this.keyLength = keyLength;
    }


    @Override
    public int encodeMsg( String blok) {
        if(polynomial>0 && keyLength>0 && polynomial<32 && keyLength<32){
            return -1;
        }else
        return computeCRC( blok );
    }

    private int computeCRC(String blok){
        int a = 0, b;
        for (int i=0; i < blok.length(); i++) {
            b = blok.charAt(i) << 24;
            for (int j = 8;j>0; j-- ) {

                if (((a^b) & (1<<31))>>>31==1) {
                    a <<= 1;
                    b <<= 1;
                    a ^= polynomial;
                }else {
                    a <<= 1;
                    b <<= 1;
                }
            }
        }
        return a>>>(32-keyLength);
    }

    @Override
    public ArrayList<String> decodeMsg() {
        return null;
    }
}
