//package com.teleinfgroup;
//
//import com.teleinfgroup.ErrorDetectionAlgorithms.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class Encoder {
//
////    private static Map<String, CRCType> CRCTypes=null;
////
////    private static void init(){
////        CRCTypes=new HashMap<>();
////        CRCTypes.put("CRC-12", new CRCType("CRC-12", 0x80f, 12));
////        CRCTypes.put("CRC-16", new CRCType("CRC-16", 0x8005, 16));
////        CRCTypes.put("CRC-16-REVERSE", new CRCType("CRC-16-REVERSE", 0x4003, 16));
////        CRCTypes.put("CRC-32", new CRCType("CRC-32", 0x4C11DB7, 32));
////        CRCTypes.put("SDLC", new CRCType("SDLC", 0x1021, 16));
////        CRCTypes.put("SDLC-REVERSE", new CRCType("SDLC-REVERSE", 0x811, 16));
////        CRCTypes.put("CRC-ITU", new CRCType("CRC-ITU", 0x1021, 16));
////        CRCTypes.put("ATM", new CRCType("ATM", 0x7, 8));
////    }
//
//    public static void encodeMsg(Message message) {
//        //init();
//
//        //Message message = new Message("ab", false);
//        //Message messageBinary = new Message("1011", true);
//        /**
//         * Wywołanie dla stringa ascii
//         * ErrorDetectionAlgorithm test = new Hamming74();
//         * System.out.println(hamming74.encodeMsg(correctBinaryLength(4,stringToBinary("ab"))));
//         *
//         * Wywołanie dla stringa binarnego
//         * ErrorDetectionAlgorithm test = new Hamming74();
//         * System.out.println(hamming74.encodeMsg(correctBinaryLength(4,"101")));
//         */
//        ErrorDetectionAlgorithm hamming74 = new Hamming74();
//        hamming74.encodeMsg(message);
//        //hamming74.encodeMsg(messageBinary);
//        System.out.println("Hamming74 for ab: "+message.getEncodedMessage());
//        //System.out.println("Hamming74 for 101: "+messageBinary.getEncodedMessage());
//
//
//        ErrorDetectionAlgorithm CRC16 = new CRC(CRCTypes.get("CRC-16"));
//        CRC16.encodeMsg(message);
////        CRC16.encodeMsg(messageBinary);
//        System.out.println("CRC16 for ab: "+message.getEncodedMessage());
////        System.out.println("CRC16 for 101: "+messageBinary.getEncodedMessage());
//
//        ErrorDetectionAlgorithm CRC32 = new CRC(CRCTypes.get("CRC-32"));
//        CRC32.encodeMsg(message);
////        CRC32.encodeMsg(messageBinary);
//        System.out.println("CRC32 for ab: "+message.getEncodedMessage());
////        System.out.println("CRC32 for 101: "+messageBinary.getEncodedMessage());
//
//        ErrorDetectionAlgorithm Parity = new ParityControl();
//        Parity.encodeMsg(message);
////        Parity.encodeMsg(messageBinary);
////
//        System.out.println("Parity for ab: "+message.getEncodedMessage());
////        System.out.println("Parity for 101: "+messageBinary.getEncodedMessage());
//
//    }
//}
