<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Using IoC Container</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>

</head>
<body>

	<div class="container mt-4">

		<h1>Using IoC Container</h1>

		<h3>Add New Registration for ${ classes.course.name } by ${ classes.teacher }</h3>

			<div class="row">

			<div class="col-4">

				<c:url var="save" value="/registration-edit"></c:url>
				<form action="${save}" method="post">

					<div class="mb-3">
						<label class="form-label">Student</label> <input type="text"
							name="student" placeholder="Enter Student's Name"
							required="required" class="form-control" />
					</div>
					<div class="mb-3">
						<label class="form-label">Phone Number</label> <input type="text"
							name="phone" placeholder="Enter Phone Number"
							required="required" class="form-control" />
					</div>
					<div class="mb-3">
						<label class="form-label">Email</label> <input type="text"
							name="email" placeholder="Enter Email Address"
							required="required" class="form-control" />
					</div>
					<input type="hidden" name="classId" value="${ classes.id }">
					<input type="hidden" name="courseId" value="${ classes.course.id }">
					
					<input type="submit" value="Save Regiration Form" class="btn btn-primary" />

				</form>

			</div>

		</div>

	</div>

</body>
</html>