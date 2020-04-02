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

<div class="bg-registration">
    <div id="regForm" class="wrapper fadeInDown" style="margin-top: 45px">
        <div id="formContent">
            <!-- Reg Form -->
            <div id="regIcon" class="fadeIn first">
                <img style="width: 170px" src="${pageContext.request.contextPath}/resources/image/user_icon.png"
                     id="icon" alt="User Icon"/>
                <h3><spring:message code="app.sing.up.message"/></h3>
            </div>

            <%--@elvariable id="userDto" type="by.bestlunch.web.dto.UserDto"--%>
            <form:form cssClass="form-group" modelAttribute="userDto" method="post" action="registration">
                <input name="id" value="${userDto.id}" type="hidden">
                <div id="regName" class="fadeIn second">
                    <bestlunch:inputField id="name" name="name" labelCode="app.reg.username"/>
                </div>

                <div id="regEmail" class="fadeIn second">
                    <bestlunch:inputField id="email" name="email" labelCode="app.reg.email"/>
                </div>

                <div id="regPassword" class="fadeIn third">
                    <bestlunch:inputField id="password" inputType="password" name="password"
                                          labelCode="app.reg.password"/>
                </div>

                <div id="regMatchingPassword" class="fadeIn third">
                    <bestlunch:inputField id="matchingPassword" inputType="password" name="matchingPassword"
                                          labelCode="app.reg.confirm.password"/>
                </div>
                <input id="submit" type="submit" class="fadeIn fourth" value="<spring:message code="app.sing.up"/>">
            </form:form>

            <div id="formFooter">
                <a href="#" onclick="window.history.back()"><< <spring:message code="app.user.back"/></a>&emsp;&emsp;
                <a href="login"><spring:message code="app.sing.in"/> >></a>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<script>
    $(document).ready(function () {
        if (${register} || ${empty userDto.password} || ${empty userDto.matchingPassword}) {
            $("#name").removeClass("is-valid");
            $("#email").removeClass("is-valid");
            $("#password").removeClass("is-valid");
            $("#matchingPassword").removeClass("is-valid");
        }
    });

    $(document).ready(function () {
        $("#name").attr('required', '');
        $("#password").attr('required', '');
        $("#email").attr('required', '');
        $("#matchingPassword").attr('required', '');

        var reqAtrName = $("#name, select[required]");
        reqAtrName.attr("oninvalid", "this.setCustomValidity('<spring:message code="userDto.NotBlank.name"/>')");
        reqAtrName.attr("oninput", "setCustomValidity('')");

        var reqAtrEmail = $("#email, select[required]");
        reqAtrEmail.attr("oninvalid", "this.setCustomValidity('<spring:message code="userDto.Email.Example"/>')");
        reqAtrEmail.attr("oninput", "setCustomValidity('')");

        var reqAtrPass = $("#password, select[required]");
        reqAtrPass.attr("oninvalid", "this.setCustomValidity('<spring:message code="userDto.NotBlank.password"/>')");
        reqAtrPass.attr("oninput", "setCustomValidity('')");

        var reqAtrConfPass = $("#matchingPassword, select[required]");
        reqAtrConfPass.attr("oninvalid", "this.setCustomValidity('<spring:message code="userDto.NotBlank.password"/>')");
        reqAtrConfPass.attr("oninput", "setCustomValidity('')");
    });
</script>
</html>