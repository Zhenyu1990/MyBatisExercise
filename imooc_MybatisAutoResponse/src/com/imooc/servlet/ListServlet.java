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
 * 列表页面初始化和查询控制
 * @author Zhenyu
 *
 */
@SuppressWarnings("serial")
public class ListServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 设置编码
		req.setCharacterEncoding("UTF-8");
		// 接受页面传值
		String command = req.getParameter("command");
		String description = req.getParameter("description");
		String currentPage = req.getParameter("currentPage");
		// 创建分页对象
		Page page = new Page();
		Pattern p = Pattern.compile("[0-9]{1,9}");
		if(currentPage == null || !p.matcher(currentPage).matches()) {
			page.setCurrentPage(1);
		}else {
			page.setCurrentPage(Integer.valueOf(currentPage));
		}
		QueryService ls = new QueryService();
		// 查询消息列表并传给页面
		req.setAttribute("messageList", ls.queryMessageListByPage(command, description, page));
		// 向页面传值
		req.setAttribute("command", command);
		req.setAttribute("description", description);
		req.setAttribute("page", page);
		// 向页面跳转
		req.getRequestDispatcher("WEB-INF/jsp/back/list.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

	
	
}
