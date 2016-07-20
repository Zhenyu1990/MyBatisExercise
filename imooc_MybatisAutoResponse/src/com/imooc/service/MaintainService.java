package com.imooc.service;

import java.util.ArrayList;
import java.util.List;

import com.imooc.dao.MessageDao;

/**
 * ά�����ҵ����
 * @author Zhenyu
 *
 */
public class MaintainService {

	/**
	 * ����ɾ��
	 */
	public void deleteOne(String id) {
		if(id != null && !"".equals(id.trim())) {
			MessageDao md = new MessageDao();
			md.deleteOne(Integer.valueOf(id));
		}
	}
	
	/**
	 * ����ɾ��
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
