package com.teleinfgroup.ErrorDetectionAlgorithms;

import java.util.ArrayList;

public class ParityControl extends ErrorDetectionAlgorithm {

    @Override
    public String encodeMsg(String text) {
        boolean[] parityBits = new boolean[text.length()];
        int character;

        StringBuilder encodedMsg = new StringBuilder(text);

        for (int i = 0; i < text.length(); i+=8) {
            character = Integer.parseInt(text.substring(i, i+8),2);
            //negatywna kontrola parzystości
            parityBits[i]= Integer.bitCount(character) % 2 == 1;
            encodedMsg.insert(i+(i/8), parityBits[i] ? '0' : '1');
        }
        return encodedMsg.toString();
    }

    @Override
    public ArrayList<String> decodeMsg() {
        return null;
    }
}
