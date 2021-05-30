package Tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DruidUtils {
	private static DataSource dataSource = null;
	static {

		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream("src//Tools//druid.properties"));
			dataSource = DruidDataSourceFactory.createDataSource(properties);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getConn() throws Exception {
		return dataSource.getConnection();
	}

	// 数据库连接池中得close不是断掉连接，而是把connection放回连接池
	public static void close(ResultSet set, Statement statement, Connection connection) throws SQLException {
		if (set != null)
			set.close();
		if (statement != null)
			statement.close();
		if (connection != null)
			connection.close();
	}
}
