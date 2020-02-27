<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bestlunch.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bestlunch.users.js"></script>

<div class="bg-admin">
    <div class="p-5">
        <div class="container bg-dark" style="color: white">
            <h3 class="text-center p-3" style="color: white;"><spring:message code="user.title"/></h3>
            <button class="btn btn-orange" onclick="add()">
                <span class="fa fa-plus"></span>
                <spring:message code="user.add"/>
            </button>
            <table class="table table-striped table-dark" id="datatable">
                <thead>
                <tr>
                    <th><spring:message code="user.name"/></th>
                    <th><spring:message code="user.email"/></th>
                    <th><spring:message code="user.roles"/></th>
                    <th><spring:message code="user.active"/></th>
                    <th><spring:message code="user.registered"/></th>
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
                    <button type="button" class="close" data-dismiss="modal" style="color: white" onclick="closeNoty()">&times;</button>
                </div>
                <div class="modal-body">
                    <form id="detailsForm">
                        <input type="hidden" id="id" name="id">

                        <div class="form-group">
                            <label for="name" class="col-form-label modal-label"><spring:message code="user.name"/></label>
                            <input type="text" class="form-control modal-input" id="name" name="name">
                        </div>

                        <div class="form-group">
                            <label for="email" class="col-form-label modal-label"><spring:message code="user.email"/></label>
                            <input type="email" class="form-control modal-input" id="email" name="email">
                        </div>

                        <div class="form-group">
                            <label for="password" class="col-form-label modal-label"><spring:message code="user.password"/></label>
                            <input type="password" class="form-control modal-input" id="password" name="password">
                        </div>

                        <div class="form-group">
                            <label for="matchingPassword" class="col-form-label modal-label"><spring:message code="user.confirm.password"/></label>
                            <input type="password" class="form-control modal-input" id="matchingPassword" name="matchingPassword">
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
    <jsp:param name="page" value="user"/>
</jsp:include>
</html>