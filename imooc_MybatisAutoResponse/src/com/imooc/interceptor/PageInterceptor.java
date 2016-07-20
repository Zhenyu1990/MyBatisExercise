package com.imooc.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import com.imooc.entity.Page;

/**
 * ��ҳ������
 * @author Zhenyu
 *
 */
// mybatis-3.4.0 ֮��İ汾, Ҫ����StatementHandler��prepare����, ��Ҫ��ע���argsдΪargs = { Connection.class, Integer.class }
@Intercepts({@Signature(type = StatementHandler.class, args = { Connection.class, Integer.class }, method = "prepare")})
public class PageInterceptor implements Interceptor{

	// private String value;
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler sh = (StatementHandler) invocation.getTarget();
		// ע��, ����ʹ�õ�MyBatis 3.4.1�汾, forObject�����ĸ�����, ���һ��������дnew DefaultReflectorFactory()����
		MetaObject mo = MetaObject.forObject(sh, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
		MappedStatement ms = (MappedStatement) mo.getValue("delegate.mappedStatement");
		// ��ȡ�����ļ���SQL��ID
		String id = ms.getId();
		// �ж�id�Ƿ���ByPage��β
		if(id.matches(".+ByPage$")) {
			BoundSql bs = sh.getBoundSql();
			// ��ȡԭʼ��SQL���
			String sql = bs.getSql();
			// ��ѯ��������SQL���
			String countSql = "SELECT count(*) FROM (" + sql + ")a";
			Connection conn = (Connection) invocation.getArgs()[0]; // java.sql��, ��ͬ
			PreparedStatement countStmt = conn.prepareStatement(countSql);
			ParameterHandler ph = (ParameterHandler) mo.getValue("delegate.parameterHandler");
			ph.setParameters(countStmt);
			ResultSet rs = countStmt.executeQuery();
			
			// ��ȡ����
			Map<?, ?> param = (Map<?, ?>) bs.getParameterObject();
			Page page = (Page) param.get("page");
			if(rs.next()) {
				page.setTotalNumber(rs.getInt(1));
			}
			System.out.println(page.getTotalNumber());
			System.out.println(page.getDbIndex());
			System.out.println(page.getDbNumber());
			// ����ҳSQL���
			String pageSql = sql + " limit " + page.getDbIndex() + "," + page.getDbNumber();
			// ��������ֵ, �滻SQL���
			mo.setValue("delegate.boundSql.sql", pageSql);
		}
		// �����ڲ���setProperties����
		// System.out.println(value);
		// ������Ȩ
		return invocation.proceed();
	}

	
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	// �÷��������õ������ļ���ע��ʱ�����õ�����ֵ
	@Override
	public void setProperties(Properties properties) {
		// String value = properties.getProperty("test");
		// this.value = value;
	}
	
}
