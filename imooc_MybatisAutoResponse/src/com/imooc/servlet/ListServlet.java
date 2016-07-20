package com.imooc.servlet;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.entity.Page;
import com.imooc.service.QueryService;

/**
 * �б�ҳ���ʼ���Ͳ�ѯ����
 * @author Zhenyu
 *
 */
@SuppressWarnings("serial")
public class ListServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ���ñ���
		req.setCharacterEncoding("UTF-8");
		// ����ҳ�洫ֵ
		String command = req.getParameter("command");
		String description = req.getParameter("description");
		String currentPage = req.getParameter("currentPage");
		// ������ҳ����
		Page page = new Page();
		Pattern p = Pattern.compile("[0-9]{1,9}");
		if(currentPage == null || !p.matcher(currentPage).matches()) {
			page.setCurrentPage(1);
		}else {
			page.setCurrentPage(Integer.valueOf(currentPage));
		}
		QueryService ls = new QueryService();
		// ��ѯ��Ϣ�б�����ҳ��
		req.setAttribute("messageList", ls.queryMessageListByPage(command, description, page));
		// ��ҳ�洫ֵ
		req.setAttribute("command", command);
		req.setAttribute("description", description);
		req.setAttribute("page", page);
		// ��ҳ����ת
		req.getRequestDispatcher("WEB-INF/jsp/back/list.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

	
	
}
