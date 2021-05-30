package server.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import Tools.DruidUtils;

public class BasicDao<T> {
	// 保证添加的是用一个连接，获取的也是同一个连接(事务处理)
	// private static ThreadLocal<Connection> tl = new
	// ThreadLocal<Connection>();
	private QueryRunner queryRunner;

	/**
	 * 返回单个对象
	 * 
	 * @param <T>
	 * 
	 * @param sql
	 * @param clazz
	 * @param params
	 *            如果没有参数就设为 Object[] params={}
	 * @return
	 */

	// 泛型的方法

	//
	public <T> T get(String sql, Class<T> clazz, Object... params) {
		T obj = null;
		Connection conn = null;
		try {
			conn = DruidUtils.getConn();
			queryRunner = new QueryRunner();
			obj = queryRunner.query(conn, sql, new BeanHandler<T>(clazz), params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DruidUtils.close(null, null, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * 返回多个对象
	 * 
	 * @param sql
	 * @param clazz
	 * @param params
	 *            如果没有参数就设为 Object[] params={}
	 * @return
	 */
	public <T> List<T> query(String sql, Class<T> clazz, Object... params) {
		List beans = null;
		Connection conn = null;
		try {
			conn = DruidUtils.getConn();
			queryRunner = new QueryRunner();
			// BeanListHandler多条数据
			beans = queryRunner.query(conn, sql, new BeanListHandler<T>(clazz), params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DruidUtils.close(null, null, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return beans;
	}

	/**
	 * 
	 * 方法描述 :page 分页
	 * 
	 * @param page
	 * @param beanClass
	 * @param sql
	 * @param params
	 * @return
	 */
	// public <T> Page page(Page page, Class<T> beanClass, String sql, Object[]
	// params) {
	// // 当前页面为1
	// if (page.getCurPage() < 1) {
	// page.setCurPage(1);
	//
	// }
	// int totalPages = 0;// 页数
	//
	// // limit sql语句 limit 4，4
	// String sqlLimit = "limit " + (page.getCurPage() - 1) *
	// page.getPageNumber() + "," + page.getPageNumber();
	//
	// List<T> list = query(sql + sqlLimit, beanClass, params);
	//
	// // 计算总数
	// Number number = this.getCount("select count(*) from (" + sql + ")c",
	// params);
	//
	// int rows = (Integer) number.intValue();
	// // 求余数
	// if (rows % page.getPageNumber() == 0) {
	// totalPages = rows / page.getPageNumber();
	// } else {
	// totalPages = rows / page.getPageNumber() + 1;
	//
	// }
	// // 封装page
	// page.setRows(number.intValue());
	// page.setData(list);
	// page.setTotalPage(totalPages);
	// return page;
	//
	// }

	/**
	 * 返回增删改是否成功 【注意，此方法没有关闭connection连接；需要在事务中关闭。 】
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public boolean update(String sql, Object... params) {

		// 此获取connection的方法
		boolean flag = false;
		Connection conn;
		try {
			conn = DruidUtils.getConn();
			queryRunner = new QueryRunner();

			QueryRunner qRunner = new QueryRunner();
			int i = qRunner.update(conn, sql, params);
			if (i > 0) {
				flag = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}

	/***
	 * 批量操作，需要用到事务 【注意，此方法没有关闭connection连接；需要在事务中关闭。 】
	 * 
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	// public boolean batchUpdate(String sql, Object[][] params) throws
	// Exception {
	// QueryRunner qRunner = new QueryRunner();
	// int result = 0;
	// boolean flag = false;
	// result = qRunner.batch(getConnection(), sql, params).length;
	// if (result > 0) {
	// flag = true;
	// }
	// return flag;
	// }

	/**
	 * 返回统计单值,
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public Number getCount(String sql, Object[] params) {
		Number value = 0;
		Connection conn = null;
		try {
			conn = DruidUtils.getConn();
			queryRunner = new QueryRunner();

			// new ScalarHandler() 单个值
			value = (Number) queryRunner.query(conn, sql, new ScalarHandler(), params);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return value;
	}

	/**
	 * 返回主键,通常是执行insert语句时返回当前的主键值 【注意，此方法没有关闭connection连接；需要在事务中关闭。 】
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	// public Long getCurrentKey(String sql, Object[] params) throws Exception {
	// Connection conn = null;
	// Long key = 0l;
	// conn = getConnection();
	// QueryRunner qRunner = new QueryRunner();
	// key = (Long) qRunner.insert(conn, sql, new ScalarHandler(1), params);
	// return key;
	// }

	/*********** 事务处理方法 ************/
	/**
	 * 开启事务
	 */
	public static void beginTranscation() {
		Connection conn = null;

		try {
			conn = DruidUtils.getConn();
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 结束事务
	 * 
	 * @throws SQLException
	 */
	public static void endTranscation() {
		Connection conn = null;
		try {
			conn = DruidUtils.getConn();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 回滚
	 * 
	 * @throws SQLException
	 */
	// public static void rollback() {
	// Connection conn = tl.get();
	// try {
	// conn.rollback();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * 事务处理，关闭资源
	 * 
	 * @throws SQLException
	 */
	// public static void closeConn() {
	// Connection conn = tl.get();
	// try {
	// if (conn != null) {
	// conn.close();
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// tl.remove();
	// }

}
