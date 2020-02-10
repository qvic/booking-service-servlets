<%@ include file="includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.client.select_timeslot"/></h3>

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
                        <c:choose>
                            <c:when test="${row.order eq null}">
                                <h5 class="mb-1">
                                    <c:out value="${row.fromTime}"/> to <c:out value="${row.toTime}"/>
                                </h5>
                                <p class="text-success">
                                    Available
                                </p>
                            </c:when>
                            <c:otherwise>
                                <h5 class="mb-1">
                                    <c:out value="${item.date}"/>
                                </h5>
                                <div class="mb-1">
                                    <h5>Service</h5>
                                    <p><c:out value="${item.service.name}"/> - $<c:out
                                            value="${item.service.price}"/></p>
                                </div>
                                <div class="mb-1">
                                    <h5>Worker</h5>
                                    <p><c:out value="${item.worker.name}"/> - <c:out value="${item.worker.email}"/></p>
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