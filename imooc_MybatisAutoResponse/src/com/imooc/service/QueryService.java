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
 * 查询相关业务
 * @author Zhenyu
 */
public class QueryService {

	public List<Message> queryMessageList(String command, String description, Page page) {
		// 组织Message对象
		Message msg = new Message();
		msg.setCommand(command);
		msg.setDescription(description);
		MessageDao md = new MessageDao();
		// 根据条件查询条数
		int totalNumber = md.count(msg);
		// 组织分页查询参数
		page.setTotalNumber(totalNumber);
		Map<String,Object> parameter = new HashMap<String, Object>();
		parameter.put("message", msg);
		parameter.put("page", page);
		// 分页查询并返回结果
		return md.queryMessageList(parameter);
	}
	
	/**
	 * 根据查询条件分页查询消息列表
	 */
	public List<Message> queryMessageListByPage(String command,String description,Page page) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		// 组织消息对象
		Message message = new Message();
		message.setCommand(command);
		message.setDescription(description);
		parameter.put("message", message);
		parameter.put("page", page);
		MessageDao messageDao = new MessageDao();
		// 分页查询并返回结果
		return messageDao.queryMessageListByPage(parameter);
	}
	
	/**
	 * 通过指令查询自动回复的内容
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
				sb.append("回复[" + cmdList.get(i).getName() + "]可以查看 " + cmdList.get(i).getDescription());
			}
			return sb.toString();
		}
		cmdList = cd.queryCommandList(name, null);
		if(cmdList.size() > 0) {
			// 获取对应的CommandContent列表
			List<CommandContent> ccList = cmdList.get(0).getContList();
			// 获取[0, ccList.size())的随机数
			int i = new Random().nextInt(ccList.size());
			// 从CommandContent集合中随机选取指令对应的内容
			String content = ccList.get(i).getContent();
			return content;
		}
		return Iconst.NO_MATCHING_CONTENT;
	}
	
}
