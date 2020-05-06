package com.github.cynaith.util;

import java.util.List;

/**
 * @USER: lynn
 * @DATE: 2020/5/3
 **/
public class ObjectUtil {
    public static boolean isEmpty(Object object){
        if (object == null){
            return true;
        }
        if (object instanceof List){
            return ((List)object).size() ==0;
        }
        if (object instanceof String){
            return ((String)object).trim().equals("");
        }
        return false;
    }
}
