package com.teleinfgroup.ErrorDetectionAlgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParityControl extends ErrorDetectionAlgorithm {

    @Override
    public void encodeMsg(Message message) {
        String text=message.getMessageInBinary(8);
        boolean[] parityBits = new boolean[text.length()];
        int character;

        Map<Integer, Byte> redundantData =new HashMap<>();

        StringBuilder encodedMsg = new StringBuilder(text);

        for (int i = 0; i < text.length(); i+=8) {
            int offset = i+(i/8);
            character = Integer.parseInt(text.substring(i, i+8),2);
            //negatywna kontrola parzystości
            parityBits[i]= Integer.bitCount(character) % 2 == 1;
            encodedMsg.insert(offset, parityBits[i] ? '0' : '1'); //position bits
            redundantData.put(offset, parityBits[i] ? (byte)0 : (byte)1);
        }
        message.setEncodedMessage(encodedMsg.toString()); //encoded
    }

    @Override
    public ArrayList<String> decodeMsg() {


        return null;
    }
}
