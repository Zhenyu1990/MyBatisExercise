package com.imooc.service;

import java.util.ArrayList;
import java.util.List;

import com.imooc.dao.MessageDao;

/**
 * 维护相关业务功能
 * @author Zhenyu
 *
 */
public class MaintainService {

	/**
	 * 单条删除
	 */
	public void deleteOne(String id) {
		if(id != null && !"".equals(id.trim())) {
			MessageDao md = new MessageDao();
			md.deleteOne(Integer.valueOf(id));
		}
	}
	
	/**
	 * 批量删除
	 */
	public void deleteBatch(String[] ids) {
		MessageDao md = new MessageDao();
		List<Integer> idList = new ArrayList<>();
		for (String id : ids) {
			idList.add(Integer.valueOf(id));
		}
		md.deleteBatch(idList);
	}
}
