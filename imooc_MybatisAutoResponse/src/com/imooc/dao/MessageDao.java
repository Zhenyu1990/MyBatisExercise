package com.imooc.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.imooc.bean.Message;
import com.imooc.db.DBAccess;

/**
 * 与Message相关的数据库操作
 * @author Zhenyu
 *
 */
public class MessageDao {

	/**
	 * 根据查询条件查询消息列表(JDBC)
	 */
//	public List<Message> queryMessageList(String command, String description) {
//		List<Message> messageList = new ArrayList<>();
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection conn;
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/micro_message?useUnicode=true&characterEncoding=utf-8&useSSL=false", "admin", "123456");
//			StringBuilder sb = new StringBuilder("select id,command,description,content from message where 1=1");
//			List<String> paramList = new ArrayList<>();
//			if(command != null && !"".equals(command.trim())) {
//				sb.append(" and command=?");
//				paramList.add(command);
//			}
//			if(description != null && !"".equals(description.trim())) {
//				sb.append(" and description like '%' ? '%'");
//				paramList.add(description);
//			}
//			String sql = sb.toString();
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			for(int i = 0; i < paramList.size(); i++) {
//				pstmt.setString(i + 1, paramList.get(i));
//			}
//			ResultSet rs = pstmt.executeQuery();
//			while(rs.next()) {
//				Message message = new Message();
//				message.setId(rs.getString("id"));
//				message.setCommand(rs.getString("command"));
//				message.setDescription(rs.getString("description"));
//				message.setContent(rs.getString("content"));
//				messageList.add(message);
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return messageList;
//	}
	
	/**
	 * 使用MyBatis查询消息列表
	 */
	public List<Message> queryMessageList(Map<String,Object> parameter) {
		DBAccess dbAccess = new DBAccess();
		List<Message> msgList = new ArrayList<>();
		SqlSession ss = null;
		try {
			ss = dbAccess.getSqlSession();
			// Message m = new Message();
			// m.setCommand(command);
			// m.setDescription(description);
			// 通过SqlSession执行SQL语句
			// msgList = ss.selectList("Message.queryMessageList", m);
			
			// 接口式编程执行SQL
			// 获取接口的实现类
			IMessage iMsg = ss.getMapper(IMessage.class);
			// 执行查询
			msgList = iMsg.queryMessageList(parameter);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ss != null) {
				ss.close();
			}
		}
		return msgList;
	}
	
	/**
	 * 根据查询条件分页查询消息列表
	 */
	public List<Message> queryMessageListByPage(Map<String,Object> parameter) {
		DBAccess dbAccess = new DBAccess();
		List<Message> messageList = new ArrayList<Message>();
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			// 通过sqlSession执行SQL语句
			IMessage imessage = sqlSession.getMapper(IMessage.class);
			messageList = imessage.queryMessageListByPage(parameter);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		return messageList;
	}
	
	/**
	 * 单条删除
	 * @param id
	 */
	public void deleteOne(int id) {
		DBAccess dbAccess = new DBAccess();
		SqlSession ss = null;
		try {
			ss = dbAccess.getSqlSession();
			// 通过SqlSession执行SQL语句
			ss.delete("Message.deleteOne", id);
			// 除查询之外的操作都需要手动提交
			ss.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ss != null) {
				ss.close();
			}
		}
	}
	
	/**
	 * 批量删除
	 */
	public void deleteBatch(List<Integer> ids) {
		DBAccess dbAccess = new DBAccess();
		SqlSession ss = null;
		try {
			ss = dbAccess.getSqlSession();
			// 通过SqlSession执行SQL语句
			ss.delete("Message.deleteBatch", ids);
			// 除查询之外的操作都需要手动提交
			ss.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ss != null) {
				ss.close();
			}
		}
	}
	
	/**
	 * 根据查询条件查询消息列表的条数
	 * @param msg
	 * @return
	 */
	public int count(Message msg) {
		DBAccess dbAccess = new DBAccess();
		SqlSession ss = null;
		int result = 0;
		try {
			ss = dbAccess.getSqlSession();
			// 通过SqlSession执行SQL语句
			IMessage iMsg = ss.getMapper(IMessage.class);
			result = iMsg.count(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ss != null) {
				ss.close();
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		// MessageDao md = new MessageDao();
	}
}
