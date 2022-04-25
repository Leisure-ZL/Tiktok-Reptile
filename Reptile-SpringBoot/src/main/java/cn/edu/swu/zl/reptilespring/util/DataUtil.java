package cn.edu.swu.zl.reptilespring.util;

import cn.edu.swu.zl.reptilespring.entity.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataUtil {

    //数据库中数字字符串转long
    public static long stringToNum(String str){
        if(str == null){
            return 0;
        }
        char c = str.charAt(str.length()-1);
        String sNum = str.substring(0,str.length()-1);
        long res;
        if(c == 'w'){
            res = (long) (Double.parseDouble(sNum) * 10000);
        }else if(c == '亿'){
            res = (long) (Double.parseDouble(sNum)) * 100000000;
        }else{
            res = Long.parseLong(str);
        }
        return res;
    }

    public static String numToString(long num){
        long t = 0;
        if(num / 100000000 > 0){
            t = num % 100000000 / 1000000;
            num /= 100000000;
            return num + "." + t + "亿";
        }else if (num / 10000 > 0){
            t = num % 10000 / 100;
            num /= 10000;
            return num + "." + t + "万";
        }else{
            return String.valueOf(num);
        }


    }



}
