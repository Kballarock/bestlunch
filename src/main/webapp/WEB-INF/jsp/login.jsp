<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/logAndReg.css">
<jsp:include page="fragments/headTag.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bestlunch.auth.js"></script>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="bg-login">
    <div id="log" class="wrapper fadeInDown">
        <div id="formContent">

            <!-- Icon -->
            <div id="userIcon" class="fadeIn first">
                <img style="width: 170px" src="${pageContext.request.contextPath}/resources/image/user_icon.png"
                     alt="User Icon"/>
                <h3><spring:message code="app.sing.in.message"/></h3>
            </div>

            <!-- Logout message -->
            <c:if test="${param.logout != null}">
                <div class="alert alert-success">
                    <spring:message code="user.logout.success"/>
                </div>
            </c:if>

            <!-- Login error message -->
            <c:if test="${param.error}">
                <div class="alert alert-danger">${SPRING_SECURITY_LAST_EXCEPTION}</div>
            </c:if>

            <!-- Login Form -->
            <form:form id="formLogin" name="formLogin" action="login" method='POST'>
                <div id="username" class="form-group fadeIn second">
                    <div class="col-form-label">
                        <label><spring:message code="app.user.login.username"/></label>
                    </div>
                    <input type="text" name="email" placeholder="<spring:message code="app.user.login"/>"
                           required="">
                </div>

                <div id="password" class="form-group fadeIn third">
                    <div class="col-form-label">
                        <label><spring:message code="app.user.password"/></label>
                    </div>
                    <input type="password" name="password" placeholder="<spring:message code="app.user.password"/>"
                           required>
                </div>

                <div id="submit" class="fadeIn fourth">
                    <input id="s" type="submit" value="<spring:message code="app.sing.in"/>">
                </div>

                <sec:authorize access="isAnonymous()">
                    <div id="submit" class="fadeIn fourth" style="margin-top: -15px;">
                        <input type="submit" onclick="login('user@user.com', 'user')"
                               value="<spring:message code="app.login"/> User&nbsp;&nbsp;&nbsp;">
                    </div>
                    <div id="submit" class="fadeIn fourth" style="margin-top: -15px">
                        <input type="submit" onclick="login('admin@admin.com', 'admin')"
                               value="<spring:message code="app.login"/> Admin">
                    </div>
                </sec:authorize>
            </form:form>

            <div id="formFooter">
                <a href="#" onclick="window.history.back()"><< <spring:message code="app.user.back"/></a>&emsp;&emsp;
                <a href="registration"><spring:message code="app.sing.up"/> >></a>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
<script type="text/javascript">
    <c:if test="${not empty param.username}">
    setCredentials("${param.username}", "");
    </c:if>

    function login(email, password) {
        setCredentials(email, password);
        $("#formLogin").submit();
    }

    function setCredentials(email, password) {
        $('input[name="email"]').val(email);
        $('input[name="password"]').val(password);
    }
</script>
</body>
<script>
    $(document).ready(function () {
        var reqAtr = $("input[required], select[required]");
        reqAtr.attr("oninvalid", "this.setCustomValidity('<spring:message code="jsp.message.required"/>')");
        reqAtr.attr("oninput", "setCustomValidity('')");
    });
</script>
</html>