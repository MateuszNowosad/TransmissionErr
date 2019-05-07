package com.teleinfgroup.ErrorDetectionAlgorithms;

import java.util.ArrayList;

public abstract class ErrorDetectionAlgorithm {
    abstract public String encodeMsg(String blok);
    abstract public ArrayList<String> decodeMsg();
}
