package com.imooc.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.imooc.bean.Command;
import com.imooc.db.DBAccess;

/**
 * 与Command相关的数据库操作
 * @author Zhenyu
 *
 */
public class CommandDao {

	/**
	 * 使用MyBatis查询指令列表
	 */
	public List<Command> queryCommandList(String name, String description) {
		DBAccess dbAccess = new DBAccess();
		List<Command> cmdList = new ArrayList<>();
		SqlSession ss = null;
		try {
			ss = dbAccess.getSqlSession();
			Command c = new Command();
			c.setName(name);
			c.setDescription(description);
			// 通过SqlSession执行SQL语句
			cmdList = ss.selectList("Command.queryCommandList", c);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ss != null) {
				ss.close();
			}
		}
		return cmdList;
	}
	
}
