<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="bg">
    <div>
        <h1 class="logo-text"><spring:message code="app.logo"/></h1>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>