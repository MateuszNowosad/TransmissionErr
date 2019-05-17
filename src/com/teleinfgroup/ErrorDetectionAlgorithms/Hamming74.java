package com.teleinfgroup.ErrorDetectionAlgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Hamming74 extends ErrorDetectionAlgorithm {

    private int calculateControlBits(String text) {
        int controlBitsAmount = 0, textLength = text.length();
        while (!(textLength + controlBitsAmount + 1 <= Math.pow(2, controlBitsAmount))) {
            controlBitsAmount++;
        }
        return controlBitsAmount;
    }

    private String computeHamming74(String text) {
        StringBuilder sb = new StringBuilder(text);
        String binaryText = sb.reverse().toString();

        int controlBitsAmount = calculateControlBits(binaryText);
        StringBuilder encodedMsg = new StringBuilder();
        int encodedMsgLength = binaryText.length() + controlBitsAmount;

        int temp = 0, temp2, j = 0, controlBit = 0;
        //fill text bits on suitable positions
        for (int i = 0; i < encodedMsgLength; i++) {
            temp2 = (int) Math.pow(2, temp);
            if ((i + 1) % temp2 != 0) {
                encodedMsg.append(binaryText.charAt(j));
                if (binaryText.charAt(j) == '1') controlBit ^= (i + 1);
                j++;
            } else temp++;
        }
        StringBuilder controlBitsBinary = new StringBuilder(Integer.toBinaryString(controlBit));
        controlBitsBinary.reverse();

        for (int i = 0; i < controlBitsAmount; i++) {
            encodedMsg.insert((int) Math.pow(2, i) - 1, i >= controlBitsBinary.length() ? '0' : controlBitsBinary.charAt(i));
        }
        return encodedMsg.reverse().toString();
    }

    @Override
    public void encodeMsg(Message message) {
        String text = message.getMessageInBinary(4);
        Map<Integer, Byte> redundantData = new HashMap<>();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i += 4) {
            String hammingCodeForBlock = computeHamming74(text.substring(i, i + 4));
            result.append(hammingCodeForBlock);
        }
        for (int i = 0; i < result.length(); i += 7) {
            redundantData.put(i + 3, (byte) Integer.parseInt(String.valueOf(result.charAt(i + 3))));
            redundantData.put(i + 5, (byte) Integer.parseInt(String.valueOf(result.charAt(i + 5))));
            redundantData.put(i + 6, (byte) Integer.parseInt(String.valueOf(result.charAt(i + 6))));
        }
        message.setRedundantData(redundantData);
        message.setEncodedMessage(result.toString());
    }

    @Override
    public void decodeMsg(Message message) {
        TreeSet<Integer> errorPosition = new TreeSet<>();

        StringBuilder sentMsg = new StringBuilder(message.getSentMessage());

        for (int i = 0; i < sentMsg.length(); i += 7) {
            int xorResult = 0;
            String targetString = sentMsg.substring(i, i + 7);
            for (int j = 0; j < 7; j++) {
                if (targetString.charAt(j) == '1') {
                    xorResult ^= 7 - j;
                }
            }
            if (xorResult != 0) {
                int errorPos = 7 - xorResult + i;
                errorPosition.add(errorPos);
                sentMsg.replace(errorPos,errorPos+1,sentMsg.charAt(errorPos) == '1' ? "0" :"1");
            }
        }

        for (int i = sentMsg.length() - 7; i >= 0; i -= 7) {
            sentMsg.deleteCharAt(i + 6);
            sentMsg.deleteCharAt(i + 5);
            sentMsg.deleteCharAt(i + 3);
        }

        message.setDecodedMessage(sentMsg.toString());

        message.setErrorsPosition(errorPosition);

    }

}
