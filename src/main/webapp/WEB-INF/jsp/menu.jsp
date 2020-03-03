<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bestlunch.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bestlunch.menu.js"></script>

<div class="bg-menu">

    <div class="p-5">
        <div class="container-filter bg-dark" style="color: white">
            <form id="filter" class="p-2">
                <%--@elvariable id="name" type="by.bestlunch.persistence.model.Restaurant"--%>
                <label for="date"><spring:message code="common.menuDate"/>${name}</label>
                <input id="date" class="form-control-sm" style="height: 38px; width: 100px" name="date"
                       autocomplete="off">

                <button class="btn btn-orange" type="button"
                        style="margin-top: -4px; height: 38px;" onclick="updateFilteredTable()">
                    <span class="fa fa-filter"></span>
                    <spring:message code="common.filter"/>
                </button>

                <button class="btn btn-red" type="button"
                        style="margin-top: -4px; height: 38px; margin-left: 5px" onclick="clearFilter()">
                    <span class="fa fa-remove"></span>
                    <spring:message code="common.clear"/>
                </button>
                <button class="btn btn-orange pull-right" type="button" style="height: 38px;"
                        onclick="add()">
                    <span class="fa fa-plus"></span>
                    <spring:message code="restaurant.add"/>
                </button>
            </form>
        </div>

        <div class="container bg-dark" style="color: white">
            <h3 class="text-center p-3" style="color: white;"><spring:message code="menu.title"/></h3>
            <table class="table table-striped table-dark" id="datatable">
                <thead>
                <tr>
                    <th><spring:message code="menuItem.name"/></th>
                    <th><spring:message code="menuItem.price"/></th>
                    <th><spring:message code="menuItem.date"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
            </table>
        </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="restaurant"/>
</jsp:include>
</html>
