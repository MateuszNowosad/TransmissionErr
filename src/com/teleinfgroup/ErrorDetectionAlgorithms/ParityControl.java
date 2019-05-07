package com.teleinfgroup.ErrorDetectionAlgorithms;

import java.util.ArrayList;

public class ParityControl extends ErrorDetectionAlgorithm {

    private StringBuilder stringToBinary(String text) {
        byte[] bytes = text.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary;
    }

    @Override
    public String encodeMsg(String text) {
        boolean[] parityBits = new boolean[text.length()];
        int character;

        StringBuilder encodedMsg = stringToBinary(text);

        for (int i = 0; i < text.length(); i++) {
            character = text.charAt(i);
            //negatywna kontrola parzystości
            parityBits[i]= Integer.bitCount(character) % 2 == 1;
            encodedMsg.insert(9*i, parityBits[i] ? '0' : '1');
        }
        return encodedMsg.toString();
    }

    @Override
    public ArrayList<String> decodeMsg() {
        return null;
    }
}
