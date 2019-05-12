package com.teleinfgroup.ErrorDetectionAlgorithms;

import java.util.*;

public class Message {

    private String message;//
    private String messageInBinary;//
    private String encodedMessage;//
    private String decodedMessage;
    private String sentMessage;
    private TreeSet<Integer> disturbedBitsPositions;
    private Map<Integer, Byte> redundantData;//
    private TreeSet<Integer> errorsPosition;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageInBinary(int correctBlockSize) {
        return correctBinaryLength(correctBlockSize, messageInBinary);
    }

    public void setMessageInBinary(String messageInBinary) {
        this.messageInBinary = messageInBinary;
    }

    public String getEncodedMessage() {
        return encodedMessage;
    }

    public void setEncodedMessage(String encodedMessage) {
        this.encodedMessage = encodedMessage;
    }

    public String getDecodedMessage() {
        return decodedMessage;
    }

    public void setDecodedMessage(String decodedMessage) {
        this.decodedMessage = decodedMessage;
    }

    public String getSentMessage() {
        return sentMessage;
    }

    public void setSentMessage(String sentMessage) {
        this.sentMessage = sentMessage;
    }

    public Map<Integer, Byte> getRedundantData() {
        return redundantData;
    }

    public void setRedundantData(Map<Integer, Byte> redundantData) {
        this.redundantData = redundantData;
    }

    public TreeSet<Integer> getRedundantDataPositions() {
        TreeSet<Integer> set = new TreeSet<>();
        redundantData.forEach((key, value) -> set.add(key));
        return set;
    }

    public TreeSet<Integer> getErrorsPosition() {
        return errorsPosition;
    }

    public void setErrorsPosition(TreeSet<Integer> errorsPosition) {
        this.errorsPosition = errorsPosition;
    }

    public Message(String message, boolean isBinary) {
        this.message = message;
        if (!isBinary) {
            this.messageInBinary = stringToBinary(message);
        } else this.messageInBinary = message;
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

    private static String correctBinaryLength(int correctBlockSize, String text) {
        if (text.length() < correctBlockSize) {
            text = "0".repeat(correctBlockSize - text.length()).concat(text);
        } else {
            int validBlockLength = correctBlockSize - (text.length() % correctBlockSize);
            if (validBlockLength != 8) {
                text = "0".repeat(validBlockLength).concat(text);
            }
        }
        return text;
    }

    public void sendMessage() {
        sentMessage = encodedMessage;
    }

    public void sendMessage(int wrongBitAmount) {
        Random generator = new Random();
        Set<Integer> wrongBitsPositions = new LinkedHashSet<>();

        while (wrongBitAmount > wrongBitsPositions.size()) {
            Integer losowyNumer = generator.nextInt(messageInBinary.length());
            wrongBitsPositions.add(losowyNumer);
        }

        sendMessage(wrongBitsPositions);
    }

    public void sendMessage(Set<Integer> wrongBitsPositions) {
        StringBuilder sentMessageSB = new StringBuilder(encodedMessage);
        disturbedBitsPositions = new TreeSet<>(wrongBitsPositions);

        for (Integer wrongBitPosition : wrongBitsPositions) {
            sentMessageSB.replace(wrongBitPosition, wrongBitPosition + 1, sentMessageSB.charAt(wrongBitPosition) == '0' ? "1" : "0");
        }

        sentMessage = sentMessageSB.toString();
    }

    public Set<Integer> getDisturbedBitsPositions() {
        return disturbedBitsPositions;
    }

    public void setDisturbedBitsPositions(TreeSet<Integer> disturbedBitsPositions) {
        this.disturbedBitsPositions = disturbedBitsPositions;
    }
}
