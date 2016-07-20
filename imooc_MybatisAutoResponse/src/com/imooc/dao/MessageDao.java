package com.imooc.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.imooc.bean.Message;
import com.imooc.db.DBAccess;

/**
 * ��Message��ص����ݿ����
 * @author Zhenyu
 *
 */
public class MessageDao {

	/**
	 * ���ݲ�ѯ������ѯ��Ϣ�б�(JDBC)
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
	 * ʹ��MyBatis��ѯ��Ϣ�б�
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
			// ͨ��SqlSessionִ��SQL���
			// msgList = ss.selectList("Message.queryMessageList", m);
			
			// �ӿ�ʽ���ִ��SQL
			// ��ȡ�ӿڵ�ʵ����
			IMessage iMsg = ss.getMapper(IMessage.class);
			// ִ�в�ѯ
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
	 * ���ݲ�ѯ������ҳ��ѯ��Ϣ�б�
	 */
	public List<Message> queryMessageListByPage(Map<String,Object> parameter) {
		DBAccess dbAccess = new DBAccess();
		List<Message> messageList = new ArrayList<Message>();
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			// ͨ��sqlSessionִ��SQL���
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
	 * ����ɾ��
	 * @param id
	 */
	public void deleteOne(int id) {
		DBAccess dbAccess = new DBAccess();
		SqlSession ss = null;
		try {
			ss = dbAccess.getSqlSession();
			// ͨ��SqlSessionִ��SQL���
			ss.delete("Message.deleteOne", id);
			// ����ѯ֮��Ĳ�������Ҫ�ֶ��ύ
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
	 * ����ɾ��
	 */
	public void deleteBatch(List<Integer> ids) {
		DBAccess dbAccess = new DBAccess();
		SqlSession ss = null;
		try {
			ss = dbAccess.getSqlSession();
			// ͨ��SqlSessionִ��SQL���
			ss.delete("Message.deleteBatch", ids);
			// ����ѯ֮��Ĳ�������Ҫ�ֶ��ύ
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
	 * ���ݲ�ѯ������ѯ��Ϣ�б������
	 * @param msg
	 * @return
	 */
	public int count(Message msg) {
		DBAccess dbAccess = new DBAccess();
		SqlSession ss = null;
		int result = 0;
		try {
			ss = dbAccess.getSqlSession();
			// ͨ��SqlSessionִ��SQL���
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
