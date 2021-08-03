package com.sportswork.sportswork.xss;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL过滤
 */
public class SQLFilter {

    /**
     * SQL注入过滤
     * @param str  待验证的字符串
     * @throws
     *
     */
    public static String sqlInject(String str)  {
        if(StringUtils.isBlank(str)){
            return null;
        }
        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        //转换成小写
        str = str.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop"};

        //判断是否包含非法字符
        for(String keyword : keywords){
            try{
                if(str.indexOf(keyword) != -1){
                    throw new Exception("包含非法字符");
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }

        return str;
    }
}
