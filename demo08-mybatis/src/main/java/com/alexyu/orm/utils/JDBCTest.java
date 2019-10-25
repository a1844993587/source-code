package com.alexyu.orm.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Yu
 * @date 2019/8/4 12:23
 */
public class JDBCTest {

    public static void main(String[] args) throws SQLException {
        /*String insertSql = "INSERT INTO `test`.`user` (`id`, `name`, `age`) VALUES (NULL, ?, ?);";
        List<Object> arrayList = new ArrayList<>();
        arrayList.add("AlexYu");
        arrayList.add(20);
        int insert = JDBCUtils.insert(insertSql, false, arrayList);
        System.out.println(insert);*/

        List<Object> arrayList = new ArrayList<>();
        arrayList.add("AlexYu");
        arrayList.add(20);
        ResultSet rs = JDBCUtils.query("SELECT * FROM user WHERE name = ? AND age = ?", arrayList);
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            System.out.println("name:" + name + ", id:" + id);
        }
    }
}
