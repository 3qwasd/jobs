/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sql.myatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;



/**
 * 数据库管理类
 * @author QiaoJian
 *
 */
public class SqlManager{

	private SqlSessionFactory sqlSessionFactory;

	private static SqlManager instance;

	private SqlManager(){
		init();
	}
	/**
	 * 初始化
	 */
	private void init(){
		try {
			InputStream inputStream = Resources.getResourceAsStream("cn/edu/bjtu/crawler/sql/myatis/mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取该类实体
	 * @return
	 */
	public static SqlManager getInstance(){
		if(instance == null){
			instance = new SqlManager();
		}

		return instance;
	}
	/**
	 * 创建一个新sqlSession对象
	 * @return
	 */
	public SqlSession createSqlSession(){

		return this.sqlSessionFactory.openSession();

	}

}
