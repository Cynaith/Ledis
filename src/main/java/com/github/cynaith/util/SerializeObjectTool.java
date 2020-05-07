package com.github.cynaith.util;

import com.github.cynaith.exceptions.UnserizlizeException;

import java.io.*;


public class SerializeObjectTool {
    //序列化
    public static byte[] serialize(Object obj) {
        ObjectOutputStream obi = null;
        ByteArrayOutputStream bai = null;
        try {
            bai = new ByteArrayOutputStream();
            try {
                obi = new ObjectOutputStream(bai);
            } catch (IOException e) {
                e.printStackTrace();
            }
            obi.writeObject(obj);
            byte[] byt = bai.toByteArray();
            return byt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 反序列化
    public static Object unserizlize(byte[] byt) {
        ObjectInputStream oii = null;
        ByteArrayInputStream bis = null;
        bis = new ByteArrayInputStream(byt);
        try {
            oii = new ObjectInputStream(bis);
            Object obj = oii.readObject();
            return obj;
        } catch (Exception e) {
            throw new UnserizlizeException("UnSerizlize error");
        }
    }

}
