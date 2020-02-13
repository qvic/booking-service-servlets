<%@ include file="includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.admin.timetable"/></h3>

    <div class="list-group">
        <c:forEach items="${requestScope.timetables}" var="timetable">
            <p class="mt-3 mb-1">
                <c:out value="${timetable.date}"/>
            </p>
            <div class="list-group">
                <c:if test="${fn:length(timetable.rows) == 0}">
                    <div class="list-group-item text-secondary">
                        No available places
                    </div>
                </c:if>

                <c:forEach items="${timetable.rows}" var="row">
                    <div class="list-group-item list-group-item-action flex-column align-items-start">
                        <h5 class="mb-1">
                            <c:out value="${row.fromTime}"/> to <c:out value="${row.toTime}"/>
                        </h5>
                        <c:choose>
                            <c:when test="${row.order eq null}">
                                <p class="text-success">
                                    Available
                                </p>
                            </c:when>
                            <c:otherwise>
                                <div>
                                    <p class="mb-0">Worker: ${row.order.worker.name} (${row.order.worker.email})</p>
                                    <p class="mb-0">Client: ${row.order.client.name} (${row.order.client.email})</p>
                                    <p class="mb-1">Service: ${row.order.service.name} ($${row.order.service.price})</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="includes/footer.jsp" %>