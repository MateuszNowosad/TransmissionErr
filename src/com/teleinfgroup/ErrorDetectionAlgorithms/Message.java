package com.teleinfgroup.ErrorDetectionAlgorithms;

import java.util.ArrayList;
import java.util.Map;

public class Message {

    private String message;//
    private String messageInBinary;//
    private String encodedMessage;//
    private String decodedMessage;
    private String sentMessage;
    private Map<Integer, Byte> redundantData;//
    private ArrayList<Integer> errorsPosition;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageInBinary(int correctBlockSize) {
        return correctBinaryLength(correctBlockSize,messageInBinary);
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

    public ArrayList<Integer> getErrorsPosition() {
        return errorsPosition;
    }

    public void setErrorsPosition(ArrayList<Integer> errorsPosition) {
        this.errorsPosition = errorsPosition;
    }

    public Message(String message, boolean isBinary){
        this.message=message;
        if(!isBinary){
            this.messageInBinary = stringToBinary(message);
        }else this.messageInBinary=message;
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


}