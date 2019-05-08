package com.teleinfgroup;

import com.teleinfgroup.ErrorDetectionAlgorithms.CRCType;
import com.teleinfgroup.ErrorDetectionAlgorithms.ErrorDetectionAlgorithm;
import com.teleinfgroup.ErrorDetectionAlgorithms.Hamming74;

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

    public static void main(String[] args) {
        init();
    }
}
