package com.pb.server.util;

import com.server.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by DW on 2016/8/8.
 */
public class MessageHolder {
    public static Map<String,Message> send_messages = new HashMap<>();
    public static ConcurrentLinkedQueue<String> rec_messages = new ConcurrentLinkedQueue<>();
}
