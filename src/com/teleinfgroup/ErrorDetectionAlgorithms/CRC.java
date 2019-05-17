package com.teleinfgroup.ErrorDetectionAlgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class CRC extends ErrorDetectionAlgorithm {

    private int polynomial;
    private int keyLength;

    public CRC(CRCType type) {
        this.keyLength = type.getKeyLength();
        this.polynomial = type.getPolynomial() << (32 - keyLength);
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
    public void encodeMsg(Message message) {
        Map<Integer, Byte> redundantData = new HashMap<>();
        String text = message.getMessageInBinary(8);
        if (polynomial == -1 || keyLength <= 0 || keyLength > 32) {
        } else {
            String result = Integer.toBinaryString(computeCRC(text));
            result = "0".repeat(keyLength - result.length()).concat(result);
            int i = text.length();
            for (char ch : result.toCharArray()) {
                redundantData.put(i++, (byte) Integer.parseInt(String.valueOf(ch)));
            }
            message.setRedundantData(redundantData);
            message.setEncodedMessage(text + result);
        }
    }

    @Override
    public void decodeMsg(Message message) {
        TreeSet<Integer> errorPosition = new TreeSet<>();

        StringBuilder sentMsg = new StringBuilder(message.getSentMessage());
        message.setDecodedMessage(sentMsg.substring(0, sentMsg.length() - keyLength));

        String sentMsgCRC = sentMsg.substring(sentMsg.length() - keyLength);
        String decodedMessageCRC = Integer.toBinaryString(computeCRC(message.getDecodedMessage()));
        decodedMessageCRC = "0".repeat(keyLength - decodedMessageCRC.length()).concat(decodedMessageCRC);

        if (!sentMsgCRC.equals(decodedMessageCRC)) {
            for (int i = 0; i < sentMsg.length(); i++) {
                errorPosition.add(i);
            }
        }

        message.setErrorsPosition(errorPosition);
    }

    private int computeCRC(String text) {
        int a = 0, b;
        for (int i = 0; i < text.length(); i += 8) {
            b = Integer.parseInt(text.substring(i, i + 8), 2) << 24;
            for (int j = 8; j > 0; j--) {

                if (((a ^ b) & (1 << 31)) >>> 31 == 1) {
                    a <<= 1;
                    b <<= 1;
                    a ^= polynomial;
                } else {
                    a <<= 1;
                    b <<= 1;
                }
            }
        }
        return a >>> (32 - keyLength);
    }


}
