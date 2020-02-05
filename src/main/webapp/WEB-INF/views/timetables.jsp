<%@ include file="includes/header.jsp" %>

<div class="container mt-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.users"/></h3>
    <div class="list-group">
        <c:forEach items="${requestScope.timetables}" var="timetable">
            <p class="my-1">
                <c:out value="${timetable.date}"/>
            </p>
            <div class="list-group">
                <c:forEach items="${timetable.rows}" var="row">
                    <div class="form-check-label list-group-item list-group-item-action flex-column align-items-start">
                        <h5 class="mb-1">
                            <c:out value="${row.fromTime}"/> to <c:out value="${row.toTime}"/>
                        </h5>
                        <c:if test="${row.orderOwner ne null}">
                            <p>
                                Booked by <c:out value="${row.orderOwner}"/>
                            </p>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="includes/footer.jsp" %>