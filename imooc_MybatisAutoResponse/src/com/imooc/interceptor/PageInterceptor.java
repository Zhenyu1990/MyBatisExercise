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
 * 分页拦截器
 * @author Zhenyu
 *
 */
// mybatis-3.4.0 之后的版本, 要调用StatementHandler的prepare方法, 需要将注解的args写为args = { Connection.class, Integer.class }
@Intercepts({@Signature(type = StatementHandler.class, args = { Connection.class, Integer.class }, method = "prepare")})
public class PageInterceptor implements Interceptor{

	// private String value;
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler sh = (StatementHandler) invocation.getTarget();
		// 注意, 这里使用的MyBatis 3.4.1版本, forObject具有四个参数, 最后一个参数填写new DefaultReflectorFactory()即可
		MetaObject mo = MetaObject.forObject(sh, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
		MappedStatement ms = (MappedStatement) mo.getValue("delegate.mappedStatement");
		// 获取配置文件中SQL的ID
		String id = ms.getId();
		// 判断id是否以ByPage结尾
		if(id.matches(".+ByPage$")) {
			BoundSql bs = sh.getBoundSql();
			// 获取原始的SQL语句
			String sql = bs.getSql();
			// 查询总条数的SQL语句
			String countSql = "SELECT count(*) FROM (" + sql + ")a";
			Connection conn = (Connection) invocation.getArgs()[0]; // java.sql包, 下同
			PreparedStatement countStmt = conn.prepareStatement(countSql);
			ParameterHandler ph = (ParameterHandler) mo.getValue("delegate.parameterHandler");
			ph.setParameters(countStmt);
			ResultSet rs = countStmt.executeQuery();
			
			// 获取参数
			Map<?, ?> param = (Map<?, ?>) bs.getParameterObject();
			Page page = (Page) param.get("page");
			if(rs.next()) {
				page.setTotalNumber(rs.getInt(1));
			}
			System.out.println(page.getTotalNumber());
			System.out.println(page.getDbIndex());
			System.out.println(page.getDbNumber());
			// 带分页SQL语句
			String pageSql = sql + " limit " + page.getDbIndex() + "," + page.getDbNumber();
			// 设置属性值, 替换SQL语句
			mo.setValue("delegate.boundSql.sql", pageSql);
		}
		// 仅用于测试setProperties方法
		// System.out.println(value);
		// 交回主权
		return invocation.proceed();
	}

	
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	// 该方法可以拿到配置文件中注册时所设置的属性值
	@Override
	public void setProperties(Properties properties) {
		// String value = properties.getProperty("test");
		// this.value = value;
	}
	
}
