package com.github.Pewbe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBManager {
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //드라이버. mysql8이후 드라이버가 변경됨
    private final String DB_URL = "jdbc:mysql://localhost/planttree_data?serverTimezone=UTC"; //접속할 DB 서버 ?serverTimezone=UTC는 mysql8이상일때 넣어줘야함

    private final String USER_NAME = "Abot"; //DB에 접속할 사용자 이름을 상수로 정의
    private final String PASSWORD = "attpo4378"; //사용자의 비밀번호를 상수로 정의

    Connection conn = null;
    Statement state = null;
    ResultSet res = null;//SQL 명령문을 전달하여 실행. 실행 결과를 가져올 때 사용함
}