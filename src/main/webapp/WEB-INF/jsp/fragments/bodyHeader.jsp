<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<nav class="navbar navbar-dark bg-dark py-0">
    <div class="container">
        <sec:authorize access="isAnonymous()">
            <a id="navIcon" href="welcome" class="navbar-brand">
                <img style="width: 25px" src="${pageContext.request.contextPath}/resources/image/fried-potatoes64px.png"
                     alt="Potatoes">
                <spring:message code="app.user.barMessage"/>
            </a>
        </sec:authorize>

        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a id="navIcon" href="admin" class="navbar-brand">
                <img style="width: 25px" src="${pageContext.request.contextPath}/resources/image/fried-potatoes64px.png"
                     alt="Potatoes">
                <spring:message code="app.user.barMessage"/>
            </a>
        </sec:authorize>

        <sec:authorize access="hasRole('ROLE_USER')">
            <a id="navIcon" href="restaurant" class="navbar-brand">
                <img style="width: 25px" src="${pageContext.request.contextPath}/resources/image/fried-potatoes64px.png"
                     alt="Potatoes">
                <spring:message code="app.user.barMessage"/>
            </a>
        </sec:authorize>

        <sec:authorize access="isAnonymous()">
            <form class="form-inline my-2">
                <a id="singIn" class="btn mr-2 btn-orange" href="login"><spring:message code="app.sing.in"/></a>
                <a id="singUp" class="btn mr-1 btn-red" href="registration"><spring:message code="app.sing.up"/></a>
            </form>
        </sec:authorize>

        <sec:authorize access="isAuthenticated()">
            <form:form id="logoutForm" class="form-inline my-2" action="logout" method="post">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <a class="btn btn-orange mr-2" href="restaurant"><span class="fa fa-bookmark"></span>
                        <spring:message code="app.restaurant"/></a>
                    <a class="btn btn-orange mr-2" href="users"> <span class="fa fa-users"></span>
                        <spring:message code="app.users"/></a>
                </sec:authorize>

                <div class="nav-item dropdown" style="color: #F3AD2E;">
                    <a class="nav-link dropdown-toggle btn mr-1 btn-orange" style="height: 40px" id="dropdownuser" data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                        <span class="fa fa-user"> </span>
                        <sec:authentication property="principal.userDto.name"/>
                    </a>

                    <div class="dropdown-menu bg-dark" aria-labelledby="dropdownuser">
                        <a class="dropdown-item bg-dark" style="color: #F3AD2E"
                           href="profile">
                            <span class="fa fa-cog"></span>
                            <spring:message code="app.profile"/></a>
                        <a class="dropdown-item bg-dark" style="color: #F3AD2E"
                           onclick="document.forms['logoutForm'].submit()">
                            <span class="fa fa-sign-out"> </span>
                            <spring:message code="app.logout"/></a>
                    </div>
                </div>
            </form:form>
        </sec:authorize>
    </div>

    <div class="nav-item dropdown" style="color: #F3AD2E; margin-right: 40px">
        <a class="nav-link dropdown-toggle" id="dropdownlang" data-toggle="dropdown"
           aria-haspopup="true" aria-expanded="false">
            <span class="fa fa-language"> </span>
            ${pageContext.response.locale.language.toUpperCase()}</a>
        <div class="dropdown-menu bg-dark" aria-labelledby="dropdownlang">
            <a class="dropdown-item bg-dark" style="color: #F3AD2E"
               href="${requestScope['javax.servlet.forward.request_uri']}?lang=en">
                <span class="flag-icon flag-icon-gb"> </span>
                <spring:message code="app.language.en"/></a>
            <a class="dropdown-item bg-dark" style="color: #F3AD2E"
               href="${requestScope['javax.servlet.forward.request_uri']}?lang=ru">
                <span class="flag-icon flag-icon-ru"> </span>
                <spring:message code="app.language.ru"/></a>
        </div>
    </div>
</nav>

<script type="text/javascript">
    var localeCode = "${pageContext.response.locale}";
</script>