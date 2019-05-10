package com.teleinfgroup;

import com.teleinfgroup.ErrorDetectionAlgorithms.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {

    private static Map<String, CRCType> CRCTypes = null;

    private static void init() {
        CRCTypes = new HashMap<>();
        CRCTypes.put("CRC-12", new CRCType("CRC-12", 0x80f, 12));
        CRCTypes.put("CRC-16", new CRCType("CRC-16", 0x8005, 16));
        CRCTypes.put("CRC-16-REVERSE", new CRCType("CRC-16-REVERSE", 0x4003, 16));
        CRCTypes.put("CRC-32", new CRCType("CRC-32", 0x4C11DB7, 32));
        CRCTypes.put("SDLC", new CRCType("SDLC", 0x1021, 16));
        CRCTypes.put("SDLC-REVERSE", new CRCType("SDLC-REVERSE", 0x811, 16));
        CRCTypes.put("CRC-ITU", new CRCType("CRC-ITU", 0x1021, 16));
        CRCTypes.put("ATM", new CRCType("ATM", 0x7, 8));
    }

    public static void testAlgorithm(String name, ErrorDetectionAlgorithm algorithm, Message message, Message messageBinary) {
        algorithm.encodeMsg(message);
        algorithm.encodeMsg(messageBinary);

        Set<Integer> set = new HashSet<>();
        set.add(9);

        message.sendMessage(set);
        messageBinary.sendMessage(set);

        algorithm.decodeMsg(message);
        algorithm.decodeMsg(messageBinary);


        System.out.println(name + " for " + message.getMessage());
        System.out.println("----------------------------------");
        System.out.println("Encoded: " + message.getEncodedMessage());
        System.out.println("Sent: " + message.getSentMessage());
        System.out.println("Decoded: " + message.getDecodedMessage());
        System.out.println("Errors: " + message.getErrorsPosition());
        System.out.println();
        System.out.println(name + " for " + messageBinary.getMessage());
        System.out.println("----------------------------------");
        System.out.println("Encoded: " + messageBinary.getEncodedMessage());
        System.out.println("Sent: " + messageBinary.getSentMessage());
        System.out.println("Decoded: " + messageBinary.getDecodedMessage());
        System.out.println("Error: " + messageBinary.getErrorsPosition());
        System.out.println("----------------------------------");
        System.out.println();
    }


    public static void main(String[] args) {
        init();
        Message message = new Message("abcd", false);
        Message messageBinary = new Message("101010101011010101010101010101", true);
        /**
         * Example usage
         * Message message = new Message("ab", false);
         * ErrorDetectionAlgorithm hamming = new Hamming74();
         * System.out.println("Hamming74 for ab: "+message.getEncodedMessage());
         */

        ErrorDetectionAlgorithm hamming74 = new Hamming74();
        testAlgorithm("Hamming(7,4)", hamming74, message, messageBinary);

        ErrorDetectionAlgorithm CRC16 = new CRC(CRCTypes.get("CRC-16"));
        testAlgorithm("CRC16", CRC16, message, messageBinary);

        ErrorDetectionAlgorithm CRC32 = new CRC(CRCTypes.get("CRC-32"));
        testAlgorithm("CRC32", CRC32, message, messageBinary);

        ErrorDetectionAlgorithm Parity = new ParityControl();
        testAlgorithm("Parity", Parity, message, messageBinary);
    }
}
