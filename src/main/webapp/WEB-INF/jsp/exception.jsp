<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="alert alert-exc alert-danger-exc" style="margin-top: 120px;" role="alert">

    <h4 class="alert-heading">${status}</h4>
    <p>${typeMessage}</p>
    <hr>
    <p class="mb-0">${message}</p>

    <button class="btn btn-red-2" style="margin-top: 10px" onclick="window.history.back()">
        <spring:message code="app.user.back"/></button>
</div>
</body>
</html>