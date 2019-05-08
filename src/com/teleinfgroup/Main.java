package com.teleinfgroup;

import com.teleinfgroup.ErrorDetectionAlgorithms.CRCType;
import com.teleinfgroup.ErrorDetectionAlgorithms.ErrorDetectionAlgorithm;
import com.teleinfgroup.ErrorDetectionAlgorithms.Hamming74;
import com.teleinfgroup.ErrorDetectionAlgorithms.CRC;
import com.teleinfgroup.ErrorDetectionAlgorithms.ParityControl;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private static Map<String, CRCType> CRCTypes=null;

    private static void init(){
        CRCTypes=new HashMap<>();
        CRCTypes.put("CRC-12", new CRCType("CRC-12", 0x80f, 12));
        CRCTypes.put("CRC-16", new CRCType("CRC-16", 0x8005, 16));
        CRCTypes.put("CRC-16-REVERSE", new CRCType("CRC-16-REVERSE", 0x4003, 16));
        CRCTypes.put("CRC-32", new CRCType("CRC-32", 0x4C11DB7, 32));
        CRCTypes.put("SDLC", new CRCType("SDLC", 0x1021, 16));
        CRCTypes.put("SDLC-REVERSE", new CRCType("SDLC-REVERSE", 0x811, 16));
        CRCTypes.put("CRC-ITU", new CRCType("CRC-ITU", 0x1021, 16));
        CRCTypes.put("ATM", new CRCType("ATM", 0x7, 8));
    }

    private static String stringToBinary(String text) {
        byte[] bytes = text.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }

    private static String correctBinaryLength(int correctBlockSize, String text ){
        if(text.length()<correctBlockSize){
            text= "0".repeat(correctBlockSize-text.length()).concat(text);
        }else {
            int validBlockLength = text.length() % correctBlockSize;
            if (validBlockLength != 0) {
                text = "0".repeat(validBlockLength).concat(text);
            }
        }
        return text;
    }

    public static void main(String[] args) {
        init();

        /**
         * Wywołanie dla stringa ascii
         * ErrorDetectionAlgorithm test = new Hamming74();
         * System.out.println(hamming74.encodeMsg(correctBinaryLength(4,stringToBinary("ab"))));
         *
         * Wywołanie dla stringa binarnego
         * ErrorDetectionAlgorithm test = new Hamming74();
         * System.out.println(hamming74.encodeMsg(correctBinaryLength(4,"101")));
         */
        ErrorDetectionAlgorithm hamming74 = new Hamming74();
        System.out.println("Hamming74 for ab: "+hamming74.encodeMsg(correctBinaryLength(4,stringToBinary("ab"))));
        System.out.println("Hamming74 for 101: "+hamming74.encodeMsg(correctBinaryLength(4,"101")));

        ErrorDetectionAlgorithm CRC16 = new CRC(CRCTypes.get("CRC-16"));
        System.out.println("CRC16 for ab: "+CRC16.encodeMsg(correctBinaryLength(8,stringToBinary("ab"))));
        System.out.println("CRC16 for 101: "+CRC16.encodeMsg(correctBinaryLength(8,"101")));

        ErrorDetectionAlgorithm CRC32 = new CRC(CRCTypes.get("CRC-32"));
        System.out.println("CRC32 for ab: "+CRC32.encodeMsg(correctBinaryLength(8,stringToBinary("ab"))));
        System.out.println("CRC32 for 101: "+CRC32.encodeMsg(correctBinaryLength(8,"101")));

        ErrorDetectionAlgorithm Parity = new ParityControl();
        System.out.println("Parity for ab: "+Parity.encodeMsg(correctBinaryLength(8,stringToBinary("ab"))));
        System.out.println("Parity for 101: "+Parity.encodeMsg(correctBinaryLength(8,"101")));
    }
}
