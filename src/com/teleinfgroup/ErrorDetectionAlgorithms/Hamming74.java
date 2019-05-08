package com.teleinfgroup.ErrorDetectionAlgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hamming74 extends ErrorDetectionAlgorithm {

    private int calculateControlBits(String text) {
        int controlBitsAmount = 0, textLength = text.length();
        while (!(textLength + controlBitsAmount + 1 <= Math.pow(2, controlBitsAmount))) {
            controlBitsAmount++;
        }
        return controlBitsAmount;
    }

    private String computeHamming74(String text){
        StringBuilder sb = new StringBuilder(text);
        String binaryText = sb.reverse().toString();

        int controlBitsAmount = calculateControlBits(binaryText);
        StringBuilder encodedMsg = new StringBuilder();
        int encodedMsgLength = binaryText.length() + controlBitsAmount;

        int temp = 0, temp2, j = 0, controlBit=0;
        //fill text bits on suitable positions
        for (int i = 0; i < encodedMsgLength; i++) {
            temp2 = (int) Math.pow(2, temp);
            if ((i+1) % temp2 != 0) {
                encodedMsg.append( binaryText.charAt(j));
                if(binaryText.charAt(j)=='1') controlBit^=(i+1);
                j++;
            } else temp++;
        }
        String controlBitsBinary = Integer.toBinaryString(controlBit);
        for(int i = 0; i < controlBitsAmount; i++ ){
            encodedMsg.insert((int)Math.pow(2,i)-1, i>=controlBitsBinary.length() ? '0': controlBitsBinary.charAt(i));
        }
        return encodedMsg.reverse().toString();
    }

    @Override
    public void encodeMsg(Message message) {
        String text = message.getMessageInBinary(4);
        Map<Integer, Byte> redundantData =new HashMap<>();
        StringBuilder result = new StringBuilder();
        for(int i =0;i<text.length();i+=4){
            String hammingCodeForBlock= computeHamming74(text.substring(i,i+4));
            result.append(hammingCodeForBlock);
        }
        for(int i =0;i<result.length();i+=7){
            redundantData.put(i+3,(byte) Integer.parseInt(String.valueOf(result.charAt(i+3))));
            redundantData.put(i+5,(byte) Integer.parseInt(String.valueOf(result.charAt(i+5))));
            redundantData.put(i+6,(byte) Integer.parseInt(String.valueOf(result.charAt(i+6))));
        }

        message.setEncodedMessage(result.toString());
    }

    @Override
    public ArrayList<String> decodeMsg() {
        return null;
    }
}
