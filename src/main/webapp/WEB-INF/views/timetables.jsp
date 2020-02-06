<%@ include file="includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.timetable"/></h3>
    <div class="list-group">
        <c:forEach items="${requestScope.timetables}" var="timetable">
            <p class="my-1">
                <c:out value="${timetable.date}"/>
            </p>
            <div class="list-group">
                <c:if test="${fn:length(timetable.rows) == 0}">
                    <div class="list-group-item text-secondary">
                        No available slots
                    </div>
                </c:if>
                <c:forEach items="${timetable.rows}" var="row">
                    <div class="form-check-label list-group-item list-group-item-action flex-column align-items-start">
                        <h5 class="mb-1">
                            <c:out value="${row.fromTime}"/> to <c:out value="${row.toTime}"/>
                        </h5>
                        <c:choose>
                            <c:when test="${row.order ne null}">
                                <p>
                                    <c:out value="${row.order}"/>
                                </p>
                            </c:when>
                            <c:otherwise>
                                <p class="text-secondary">
                                    Available
                                </p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="includes/footer.jsp" %>