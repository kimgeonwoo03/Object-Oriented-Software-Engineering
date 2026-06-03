package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {

    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            Properties props = new Properties();

            InputStream inputStream = DBUtil.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            if (inputStream == null) {
                throw new RuntimeException("db.properties 파일을 찾을 수 없습니다.");
            }

            props.load(inputStream);

            driver = props.getProperty("db.driver");
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");

            Class.forName(driver);

        } catch (Exception e) {
            throw new RuntimeException("DB 설정 초기화 실패", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, password);
    }
}