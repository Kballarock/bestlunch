<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bestlunch.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bestlunch.restaurant.js"></script>

<div class="bg-user">
    <div class="my-bg">
        <div class="container-filter bg-dark" style="color: white">
            <form id="filter" class="p-2">
                <label for="dateVotingFrom"><spring:message code="common.votingDayFrom"/></label>
                <input id="dateVotingFrom" class="form-control-sm" style="height: 38px; width: 100px" name="startDate"
                       autocomplete="off">

                <label for="dateVotingTo"><spring:message code="common.votingDayTo"/></label>
                <input id="dateVotingTo" class="form-control-sm" style="height: 38px; width: 100px" name="endDate"
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
            </form>
        </div>

        <div class="container bg-dark" style="color: white">
            <h3 class="text-center p-3" style="color: white;"><spring:message code="restaurant.title"/></h3>
            <table class="table table-striped table-dark" id="datatable">
                <thead>
                <tr>
                    <th><spring:message code="restaurant.menu"/></th>
                    <th><spring:message code="restaurant.name"/></th>
                    <th><spring:message code="restaurant.description"/></th>
                    <th><spring:message code="restaurant.address"/></th>
                    <th><spring:message code="restaurant.added"/></th>
                    <th><spring:message code="restaurant.votes"/></th>
                    <th><spring:message code="restaurant.vote"/></th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="restaurant"/>
</jsp:include>
</html>