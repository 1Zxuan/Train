package tools;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBTools {
	public static SqlSessionFactory sessionFactory;
	static{
		try {
			Reader reader=Resources.getResourceAsReader("conf.xml");
			sessionFactory =new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			System.out.println("DBTOOL");
			e.printStackTrace();
		}
	}
	public static SqlSession getSession() {
		return sessionFactory.openSession();
	}
}
