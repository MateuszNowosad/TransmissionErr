package com.teleinfgroup.ErrorDetectionAlgorithms;

import java.util.ArrayList;

public class Hamming extends ErrorDetectionAlgorithm {

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

    private int calculateControlBits(String text) {
        int controlBits = 0, textLength = text.length();
        while (!(textLength + controlBits + 1 <= Math.pow(2, controlBits))) {
            controlBits++;
        }
        return controlBits;
    }

    @Override
    public String encodeMsg(String text) {
        String binaryText = stringToBinary(text).toString();

        int controlBits = calculateControlBits(binaryText);
        StringBuilder encodedMsg = new StringBuilder();
        int encodedMsgLength = binaryText.length() + controlBits;

        int temp = 0, temp2 = 0, j = 0;
        //fill text bits on suitable positions
        for (int i = 0; i < encodedMsgLength; i++) {
            temp2 = (int) Math.pow(2, temp);
            if (temp2 % 2 != 0) {
                encodedMsg.insert(i, binaryText.charAt(j));
            } else temp++;
        }
    }

    @Override
    public ArrayList<String> decodeMsg() {
        return null;
    }
}
