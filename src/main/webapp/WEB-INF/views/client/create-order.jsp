<%@ include file="../includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.client.create_order"/></h3>

    <div class="jumbotron w-75 mx-auto text-center">
        <h3 class="mb-5"><fmt:message key="label.client.already_selected"/></h3>
        <c:if test="${requestScope.timeslot ne null}">
            <h5><span class="text-muted"><fmt:message key="label.timeslot"/>:</span> <c:out value="${requestScope.timeslot.fromTime}"/> (<c:out
                    value="${requestScope.timeslot.duration.toMinutes()}"/> min)</h5>
        </c:if>
        <c:if test="${requestScope.service ne null}">
            <h5><span class="text-muted"><fmt:message key="label.service"/>:</span> <c:out value="${requestScope.service.name}"/></h5>
        </c:if>
        <c:if test="${requestScope.worker ne null}">
            <h5><span class="text-muted"><fmt:message key="label.worker"/>:</span> <c:out value="${requestScope.worker.name}"/></h5>
        </c:if>

        <form method="post">
            <button class="w-50 my-5 mx-auto btn btn-primary btn-lg btn-block" type="submit"><fmt:message
                    key="label.submit"/></button>
        </form>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>