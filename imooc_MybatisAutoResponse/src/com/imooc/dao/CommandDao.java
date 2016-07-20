package com.imooc.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.imooc.bean.Command;
import com.imooc.db.DBAccess;

/**
 * ��Command��ص����ݿ����
 * @author Zhenyu
 *
 */
public class CommandDao {

	/**
	 * ʹ��MyBatis��ѯָ���б�
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
			// ͨ��SqlSessionִ��SQL���
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
