<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="bestlunch" tagdir="/WEB-INF/tags" %>

<html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/logAndReg.css">
<jsp:include page="fragments/headTag.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bestlunch.auth.js"></script>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="bg-profile">
    <div class="wrapper">
        <div id="formContent">
            <!-- Profile Form -->
            <div id="regIcon" class="fadeIn">
                <img class="card-img" style="max-width: 20%; height: auto; margin-top: 5px"
                     src="${pageContext.request.contextPath}/resources/image/setting_icon.jpeg"
                     id="icon" alt="User Icon"/>
                <h5><spring:message code="app.profile.form.title"/></h5>
            </div>

            <%--@elvariable id="userDto" type="by.bestlunch.web.dto.UserDto"--%>
            <form:form cssClass="form-group" modelAttribute="userDto" method="post" action="profile">
                <input name="id" value="${userDto.id}" type="hidden">
                <div class="fadeIn">
                    <bestlunch:inputField id="name" name="name" labelCode="app.reg.username"/>
                </div>

                <div class="fadeIn">
                    <bestlunch:inputField id="email" name="email" labelCode="app.reg.email"/>
                </div>

                <div class="fadeIn">
                    <bestlunch:inputField id="password" inputType="password" name="password"
                                          labelCode="app.reg.password"/>
                </div>

                <div class="fadeIn">
                    <bestlunch:inputField id="matchingPassword" inputType="password" name="matchingPassword"
                                          labelCode="app.reg.confirm.password"/>
                </div>

                <div id="formFooter" style="margin-top: 5px">
                    <button type="submit" class="btn btn-orange-2" onclick="save()">
                        <span class="fa fa-check"></span>
                        <spring:message code="common.save"/>
                    </button>
                    <button type="button" class="btn btn-red-2" onclick="window.history.back()">
                        <span class="fa fa-close"></span>
                        <spring:message code="common.cancel"/>
                    </button>
                </div>
            </form:form>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
<script>
    $(document).ready(function () {
        if (${profile} || ${empty userDto.password} || ${empty userDto.matchingPassword}) {
            $("#password").removeClass("is-valid");
            $("#matchingPassword").removeClass("is-valid");
        }
    });

    $(document).ready(function () {
        $('input[name=password]').val('');
        $('input[name=matchingPassword]').val('');
    });
</script>
</html>