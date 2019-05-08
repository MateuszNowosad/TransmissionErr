package com.teleinfgroup.ErrorDetectionAlgorithms;

import java.util.ArrayList;

public abstract class ErrorDetectionAlgorithm {
    abstract public void encodeMsg(Message message);
    abstract public ArrayList<String> decodeMsg();
}
