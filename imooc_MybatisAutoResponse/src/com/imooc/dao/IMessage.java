package com.imooc.dao;

import java.util.List;
import java.util.Map;

import com.imooc.bean.Message;

/**
 * ��Message�����ļ���Ӧ�Ľӿ�
 * @author Zhenyu
 *
 */
public interface IMessage {

	// ��ӦMessage.xml��idΪqueryMessageList�ı�ǩ
	public List<Message> queryMessageList(Map<String,Object> parameter);
	
	// ���ݲ�ѯ������ѯ��Ϣ�б������
	public int count(Message msg);
	
	// ���ݲ�ѯ������ҳ��ѯ��Ϣ�б�
	public List<Message> queryMessageListByPage(Map<String,Object> parameter);
}
