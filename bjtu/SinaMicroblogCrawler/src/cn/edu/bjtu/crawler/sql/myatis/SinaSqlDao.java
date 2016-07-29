/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sql.myatis;

import org.apache.ibatis.session.SqlSession;

/**
 * @author QiaoJian
 *
 */
public class SinaSqlDao {
	
	SqlManager sqlManager = null;
	ThreadLocal<SqlSession> sessionContainer = null;
	public SinaSqlDao() {
		super();
		// TODO Auto-generated constructor stub
		this.sqlManager = SqlManager.getInstance();
	}
	/**
	 * 从本地线程中获取sqlSession对象
	 * @return
	 */
	private SqlSession getSqlSession(){
		SqlSession sqlSession = null;
		if(sessionContainer == null){
			sessionContainer = new ThreadLocal<SqlSession>();
			sqlSession = sqlManager.createSqlSession();
			sessionContainer.set(sqlSession);
		}else{
			sqlSession = sessionContainer.get();
			if(sqlSession == null){
				sqlSession = sqlManager.createSqlSession();
				sessionContainer.set(sqlSession);
			}
		}
		
		return sessionContainer.get();
	}
	/**
	 * 关闭sqlSeesion对象
	 */
	public void closeSqlSession(){
		SqlSession sqlSession = this.sessionContainer.get();
		if(sqlSession==null){
			return;
		}else{
			sqlSession.commit();
			sqlSession.close();
			sessionContainer.remove();
		}
	}
	/**
	 * 获取mybatis的代理mapper
	 * @param mapperClass
	 * 
	 */
	public Object getMyBatisProxyMapper(Class mapperClass){
		SqlSession session = this.getSqlSession();
		return session.getMapper(mapperClass);
	}
	/**
	 * sqlsession提交
	 */
	public void commit(){
		SqlSession sqlSession = this.sessionContainer.get();
		if(sqlSession==null){
			return;
		}else{
			sqlSession.commit();
		}
	}
	
}
