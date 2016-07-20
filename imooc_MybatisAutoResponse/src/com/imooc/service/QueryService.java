package com.imooc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.imooc.bean.Command;
import com.imooc.bean.CommandContent;
import com.imooc.bean.Message;
import com.imooc.dao.CommandDao;
import com.imooc.dao.MessageDao;
import com.imooc.entity.Page;
import com.imooc.util.Iconst;

/**
 * ��ѯ���ҵ��
 * @author Zhenyu
 */
public class QueryService {

	public List<Message> queryMessageList(String command, String description, Page page) {
		// ��֯Message����
		Message msg = new Message();
		msg.setCommand(command);
		msg.setDescription(description);
		MessageDao md = new MessageDao();
		// ����������ѯ����
		int totalNumber = md.count(msg);
		// ��֯��ҳ��ѯ����
		page.setTotalNumber(totalNumber);
		Map<String,Object> parameter = new HashMap<String, Object>();
		parameter.put("message", msg);
		parameter.put("page", page);
		// ��ҳ��ѯ�����ؽ��
		return md.queryMessageList(parameter);
	}
	
	/**
	 * ���ݲ�ѯ������ҳ��ѯ��Ϣ�б�
	 */
	public List<Message> queryMessageListByPage(String command,String description,Page page) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		// ��֯��Ϣ����
		Message message = new Message();
		message.setCommand(command);
		message.setDescription(description);
		parameter.put("message", message);
		parameter.put("page", page);
		MessageDao messageDao = new MessageDao();
		// ��ҳ��ѯ�����ؽ��
		return messageDao.queryMessageListByPage(parameter);
	}
	
	/**
	 * ͨ��ָ���ѯ�Զ��ظ�������
	 * @param command
	 * @return
	 */
	public String queryByCommand(String name) {
		CommandDao cd = new CommandDao();
		List<Command> cmdList = null;
		if(Iconst.HELP_COMMAND.equals(name)) {
			cmdList = cd.queryCommandList(null, null);
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < cmdList.size(); i++) {
				if(i != 0) {
					sb.append("<br/>");
				}
				sb.append("�ظ�[" + cmdList.get(i).getName() + "]���Բ鿴 " + cmdList.get(i).getDescription());
			}
			return sb.toString();
		}
		cmdList = cd.queryCommandList(name, null);
		if(cmdList.size() > 0) {
			// ��ȡ��Ӧ��CommandContent�б�
			List<CommandContent> ccList = cmdList.get(0).getContList();
			// ��ȡ[0, ccList.size())�������
			int i = new Random().nextInt(ccList.size());
			// ��CommandContent���������ѡȡָ���Ӧ������
			String content = ccList.get(i).getContent();
			return content;
		}
		return Iconst.NO_MATCHING_CONTENT;
	}
	
}
