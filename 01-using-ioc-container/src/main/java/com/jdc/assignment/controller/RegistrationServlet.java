package com.jdc.assignment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdc.assignment.domain.Registration;
import com.jdc.assignment.model.OpenClassModel;
import com.jdc.assignment.model.RegistrationModel;

/**
 * @author Mya Thinzar
 */
@WebServlet(urlPatterns = { "/students", "registration-edit" })
public class RegistrationServlet extends AbstractBeanFactoryServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var classId = req.getParameter("classId");

		// Find Open Class
		var openClassModel = getBean("openClassModel", OpenClassModel.class);
		var openClass = openClassModel.findById(Integer.parseInt(classId));

		System.out.println("class id-->" + openClass.getStartDate());
		req.setAttribute("classes", openClass);

		var page = switch (req.getServletPath()) {
		case "/students" -> {
			var model = getBean("registrationModel", RegistrationModel.class);
			req.setAttribute("students", model.findAllByClassId(Integer.parseInt(classId)));
			yield "registrations";
		}
		default -> "registration-edit";
		};

		getServletContext().getRequestDispatcher("/%s.jsp".formatted(page)).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get req parameter
		var classId = req.getParameter("classId");
		var courseId = req.getParameter("courseId");
		var student = req.getParameter("student");
		var phone = req.getParameter("phone");
		var email = req.getParameter("email");

		// find open class by id
		var openClassModel = getBean("openClassModel", OpenClassModel.class);
		var openClass = openClassModel.findById(Integer.parseInt(classId));

		// create Registrtation object
		var registration = new Registration();
		registration.setOpenClass(openClass);
		registration.setPhone(phone);
		registration.setEmail(email);
		registration.setStudent(student);

		// save to db
		var registrationModel = getBean("registrationModel", RegistrationModel.class);
		registrationModel.create(registration);
		
		resp.sendRedirect("/classes?courseId="+courseId);
	}

}
