<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="alert alert-exc alert-danger-exc" role="alert">
    <h4 class="alert-heading">403 FORBIDDEN</h4>

    <p><spring:message code="error.accessDenied"/></p>
    <hr>

    <button class="btn btn-red-2" style="margin-top: 10px" onclick="window.history.back()">
        <spring:message code="app.user.back"/></button>
</div>
</body>
</html>