package cn.edu.swu.zl.reptilespring.util;

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

}
