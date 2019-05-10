package com.teleinfgroup.ErrorDetectionAlgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParityControl extends ErrorDetectionAlgorithm {

    @Override
    public void encodeMsg(Message message) {
        String text = message.getMessageInBinary(8);
        boolean[] parityBits = new boolean[text.length()];
        int character;

        Map<Integer, Byte> redundantData = new HashMap<>();

        StringBuilder encodedMsg = new StringBuilder(text);

        for (int i = 0; i < text.length(); i += 8) {
            int offset = i + (i / 8);
            character = Integer.parseInt(text.substring(i, i + 8), 2);
            //negatywna kontrola parzystości
            parityBits[i] = Integer.bitCount(character) % 2 == 1;
            encodedMsg.insert(offset, parityBits[i] ? '0' : '1'); //position bits
            redundantData.put(offset, parityBits[i] ? (byte) 0 : (byte) 1);
        }
        message.setRedundantData(redundantData);
        message.setEncodedMessage(encodedMsg.toString()); //encoded
    }

    @Override
    public void decodeMsg(Message message) {
        ArrayList<Integer> errorPosition = new ArrayList<>();

        StringBuilder sentMsg = new StringBuilder(message.getSentMessage());

        for (int i = 0; i < sentMsg.length(); i += 9) {
            if (!checkParityBlock(sentMsg.substring(i, i + 9))) {
                for (int j = i; j < i + 9; j++)
                    errorPosition.add(j);
            }
        }
        for (int i = 0; i < sentMsg.length(); i += 8) {
            sentMsg.deleteCharAt(i);
        }
        message.setErrorsPosition(errorPosition);
        message.setDecodedMessage(sentMsg.toString());
    }

    public boolean checkParityBlock(String block) {

        boolean parityBit = block.charAt(0) == '1';
        boolean blockParityBit;

        blockParityBit = Integer.bitCount(Integer.parseInt(block.substring(1), 2)) % 2 == 0;

        return parityBit == blockParityBit;
    }


}
