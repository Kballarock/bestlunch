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
            <form id="menuFilter" class="p-2">
                <label for="menuDate"><spring:message code="common.menuDate"/></label>
                <input id="menuDate" class="form-control-sm" style="height: 38px; width: 100px" name="date"
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

    <div class="modal fade" tabindex="-1" id="editRow">
        <div class="modal-dialog" style="color: white">
            <div class="modal-content bg-dark">
                <div class="modal-header">
                    <h4 class="modal-title" id="modalTitle"></h4>
                    <button type="button" class="close" data-dismiss="modal" style="color: white" onclick="closeNoty()">
                        &times;
                    </button>
                </div>
                <div class="modal-body">
                    <form id="detailsForm">
                        <input type="hidden" id="id" name="id">

                        <div class="form-group">
                            <label for="name" class="col-form-label modal-label"><spring:message
                                    code="menuItem.name"/></label>
                            <input type="text" class="form-control modal-input" id="name" name="name">
                        </div>

                        <div class="form-group">
                            <label for="price" class="col-form-label modal-label"><spring:message
                                    code="menuItem.price"/></label>
                            <input type="number" min="0" max="10000" step="0.01" class="form-control modal-input"
                                   id="price" name="price">
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
</html>