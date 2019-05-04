package com.teleinfgroup.ErrorDetectionAlgorithms;

import java.util.ArrayList;

public abstract class ErrorDetectionAlgorithm {
    abstract public int encodeMsg(String blok);
    abstract public ArrayList<String> decodeMsg();
}
