package com.hwan.commuteProgram.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
  public static Connection getConnection() {
    try {
      String dbURL = "jdbc:mysql://3.34.1.252:3306/commuteprogram?verifyServerCertificate=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";
      String dbID = "root";
      String dbPassword = "star0326";
      Class.forName("com.mysql.jdbc.Driver");
	/*
	  설정없이 그냥 실행시 
	  java.lang.ClassNotFoundException: com.mysql.jdbc.Driver
	   라는 드라이버를 찾을 수 없다는 오류가 발생합니다. 
	   드라이버를 다운받은후 web-inf/lib 에 넣으면 됩니다.
	*/ 
      return DriverManager.getConnection(dbURL, dbID, dbPassword);
    } catch (Exception e) {
      e.printStackTrace();
    }
		
    return null;
    // 오류 발생시 null 값 리턴
  }
}