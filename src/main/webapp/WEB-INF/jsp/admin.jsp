<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bestlunch.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bestlunch.admin.js"></script>
<div class="bg-admin">

    <div class="mask d-flex justify-content-center align-items-center">
        <div class="container-filter bg-dark" style="color: white; margin-top: 100px">
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
                <button class="btn btn-orange pull-right" type="button" style="height: 38px;"
                        onclick="add()">
                    <span class="fa fa-plus"></span>
                    <spring:message code="restaurant.add"/>
                </button>
            </form>
        </div>
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
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>
    </div>

    <div class="modal fade" tabindex="-1" id="editRow">
        <div class="modal-dialog" style="color: white">
            <div class="modal-content bg-dark">
                <div class="modal-header">
                    <h4 class="modal-title" id="modalTitle"></h4>
                    <button type="button" class="close" data-dismiss="modal" style="color: white" onclick="closeNoty()">&times;</button>
                </div>
                <div class="modal-body">
                    <form id="detailsForm">
                        <input type="hidden" id="id" name="id">

                        <div class="form-group">
                            <label for="name" class="col-form-label modal-label"><spring:message
                                    code="restaurant.name"/></label>
                            <input type="text" class="form-control modal-input" id="name" name="name">
                        </div>

                        <div class="form-group">
                            <label for="description" class="col-form-label modal-label"><spring:message
                                    code="restaurant.description"/></label>
                            <input type="text" class="form-control modal-input" id="description" name="description">
                        </div>

                        <div class="form-group">
                            <label for="address" class="col-form-label modal-label"><spring:message
                                    code="restaurant.address"/></label>
                            <input type="text" class="form-control modal-input" id="address" name="address">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-red" data-dismiss="modal" onclick="closeNoty()">
                        <span class="fa fa-close"></span>
                        <spring:message code="common.cancel"/>
                    </button>
                    <button type="button" class="btn btn-orange" onclick="save()">
                        <span class="fa fa-check"></span>
                        <spring:message code="common.save"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="restaurant"/>
</jsp:include>
<script>
    $('#name').bind('keyup blur', function () {
        var node = $(this);
        node.val(node.val().replace(/[^a-zA-Zа-яА-Я ]/g, ''));
    });
</script>
</html>