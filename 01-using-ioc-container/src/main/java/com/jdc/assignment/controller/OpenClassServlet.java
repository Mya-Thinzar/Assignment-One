package com.jdc.assignment.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jdc.assignment.domain.OpenClass;
import com.jdc.assignment.model.CourseModel;
import com.jdc.assignment.model.OpenClassModel;

@WebServlet(urlPatterns = {
		"/classes",
		"/class-edit"
})
public class OpenClassServlet extends AbstractBeanFactoryServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		var courseId = req.getParameter("courseId");
		// Find Course 
		var courseModel = getBean("courseModel", CourseModel.class);
		var course = courseModel.findById(Integer.parseInt(courseId));
		
		req.setAttribute("course", course);
     
		var page = switch(req.getServletPath()) {
		case "/classes" -> {
			var model = getBean("openClassModel", OpenClassModel.class);
			req.setAttribute("classes", model.findByCourse(Integer.parseInt(courseId)));
			yield "classes";
		}
		default -> "class-edit";
		};
		
		getServletContext().getRequestDispatcher("/%s.jsp".formatted(page)).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get request parameter
		var startDate=req.getParameter("startDate");
		var teacher=req.getParameter("teacher");
		var courseId=req.getParameter("courseId");
		
		// find Course by courseId
		var courseModel=getBean("courseModel", CourseModel.class);
		var course=courseModel.findById(Integer.parseInt(courseId));
		
		//create OpenClass object
		var openClass=new OpenClass();
		openClass.setCourse(course);
		openClass.setStartDate(LocalDate.parse(startDate));
		openClass.setTeacher(teacher);
		
		//save to db
		var openClassModel=getBean("openClassModel", OpenClassModel.class);
		openClassModel.create(openClass);
		
		resp.sendRedirect("/courses");
	}
}
