<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<nav class="navbar navbar-dark bg-dark navbar-expand-lg fixed-top bg-secondary" id="mainNav">
    <div class="container">
        <sec:authorize access="isAnonymous()">
            <a id="navIcon" href="welcome" class="navbar-brand p-1">
                <img style="width: 36px" src="${pageContext.request.contextPath}/resources/image/fried-potatoes64px.png"
                     alt="Potatoes">
                <spring:message code="app.user.barMessage"/>
            </a>
        </sec:authorize>

        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a id="navIcon" href="admin" class="navbar-brand p-1">
                <img style="width: 36px" src="${pageContext.request.contextPath}/resources/image/fried-potatoes64px.png"
                     alt="Potatoes">
                <spring:message code="app.user.barMessage"/>
            </a>
        </sec:authorize>

        <sec:authorize access="hasRole('ROLE_USER')">
            <a id="navIcon" href="restaurant" class="navbar-brand p-1">
                <img style="width: 36px" src="${pageContext.request.contextPath}/resources/image/fried-potatoes64px.png"
                     alt="Potatoes">
                <spring:message code="app.user.barMessage"/>
            </a>
        </sec:authorize>

        <button data-toggle="collapse" data-target="#navbarResponsive"
                class="navbar-toggler navbar-toggler-right text-uppercase bg-dark text-white rounded"
                aria-controls="navbarResponsive"
                aria-expanded="false" aria-label="Toggle navigation"><i class="fa fa-bars"></i></button>

        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="nav navbar-nav ml-auto">
                <sec:authorize access="isAuthenticated()">
                    <form:form cssClass="form-inline" id="logoutForm" action="logout" method="post">
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <li class="nav-item p-1">
                                <a class="btn btn-orange mr-2" href="admin"><span class="fa fa-cogs"></span>
                                    <spring:message code="app.admin.home"/></a>
                            </li>

                            <li class="nav-item p-1">
                                <a class="btn btn-orange mr-2" href="users"> <span class="fa fa-users"></span>
                                    <spring:message code="app.users"/></a>
                            </li>
                        </sec:authorize>

                        <li class="nav-item p-1">
                            <a class="btn btn-orange mr-2" href="restaurant"><span class="fa fa-bookmark"></span>
                                <spring:message code="app.restaurant"/></a>
                        </li>

                        <li class="nav-item p-1">
                            <div class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle btn mr-1 btn-orange"
                                   style="height: 40px; color: #F3AD2E;"
                                   id="dropdownuser"
                                   data-toggle="dropdown"
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
                        </li>
                    </form:form>
                </sec:authorize>

                <sec:authorize access="isAnonymous()">
                    <li class="nav-item p-1">
                        <a id="singIn" class="btn mr-2 btn-orange" href="login"><spring:message code="app.sing.in"/></a>
                    </li>

                    <li class="nav-item p-1">
                        <a id="singUp" class="btn mr-1 btn-red" href="registration"><spring:message
                                code="app.sing.up"/></a>
                    </li>
                </sec:authorize>

                <li class="nav-item p-1">
                    <div class="nav-item dropdown">
                        <a class="nav-link  dropdown-toggle" style="color: #F3AD2E;"
                           id="dropdownlang" data-toggle="dropdown"
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
                </li>
            </ul>
        </div>
    </div>
</nav>

<script type="text/javascript">
    var localeCode = "${pageContext.response.locale}";
    var updateMsg = '<spring:message code="common.edit" />';
    var daleteMsg = '<spring:message code="common.delete" />';
    var menuMsg = '<spring:message code="common.menu" />';
    var voteMsg = '<spring:message code="common.vote" />';
</script>