<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="name" required="true" description="Name of corresponding property in bean object" %>
<%@ attribute name="labelCode" required="true" description="Field label" %>
<%@ attribute name="inputType" required="false" description="Input type" %>
<%@ attribute name="id" required="false" description="ID" %>

<spring:bind path="${name}">
    <div class="form-group ${status.error ? 'error' : '' }">
        <div class="col-form-label">
            <form:label path="name" class=""><spring:message code="${labelCode}"/></form:label>
        </div>
        <c:choose>
            <c:when test="${inputType == 'password'}"><form:input path="${name}" type="password"
                                      cssClass="form-control ${status.error ? 'is-invalid' : 'is-valid'}"/>
            </c:when>
            <c:otherwise> <form:input id="${name}" path="${name}" type="text"
                                      cssClass="form-control ${status.error ? 'is-invalid' : 'is-valid' }"
                                      placeholder=""/></c:otherwise>
        </c:choose>
        <div class="invalid-feedback">${status.errorMessage}</div>
    </div>

    <script>
        $(document).ready(function () {
            if (${status.error}) {
                $("div#regForm").removeClass("fadeInDown");
                $("div#regIcon").removeClass("fadeIn first");
                $("div#regName").removeClass("fadeIn second");
                $("div#regEmail").removeClass("fadeIn second");
                $("div#regPassword").removeClass("fadeIn third");
                $("div#regMatchingPassword").removeClass("fadeIn third");
                $("#submit").removeClass("fadeIn fourth");
            }
        });

        $("#name").attr('placeholder', '<spring:message code="app.reg.username"/>');
        $("#email").attr('placeholder', '<spring:message code="app.reg.email"/>');
        $("#password").attr('placeholder', '<spring:message code="app.reg.password"/>');
        $("#matchingPassword").attr('placeholder', '<spring:message code="app.reg.confirm.password"/>');

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
</spring:bind>