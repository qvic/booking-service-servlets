<%@ include file="../includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.admin.timetable"/></h3>

    <hr>

    <form method="get" class="form-inline">
        <div class="form-group">
            <label for="from-date" class="mr-2"><fmt:message key="label.from"/></label>
            <input id="from-date" class="form-control mr-2" type="date" name="from-date">
        </div>
        <div class="form-group">
            <label for="to-date" class="mr-2"><fmt:message key="label.to"/></label>
            <input id="to-date" class="form-control mr-2" type="date" name="to-date">
        </div>
        <input class="btn btn-primary" type="submit" value="<fmt:message key="label.go"/>">
    </form>

    <hr>

    <div class="list-group">
        <c:forEach items="${requestScope.timetables}" var="timetable">
            <p class="mt-3 mb-1">
                    ${cf:formatLocalDate(timetable.date ,'dd.MM.yyyy')}
            </p>
            <div class="list-group">
                <c:if test="${fn:length(timetable.rows) == 0}">
                    <div class="list-group-item text-secondary">
                        <fmt:message key="label.client.no_available_places"/>
                    </div>
                </c:if>

                <c:forEach items="${timetable.rows}" var="row">
                    <div class="list-group-item list-group-item-action flex-column align-items-start">
                        <h4 class="my-2">
                                ${row.fromTime} - ${row.fromTime.plus(row.duration)}
                        </h4>
                        <c:forEach items="${row.orders}" var="order">
                            <div class="mb-2">
                                <h5><fmt:message key="label.order"/> #${order.id}</h5>
                                <div class="mb-1">
                                    <h5><fmt:message key="label.service"/></h5>
                                    <p><c:out value="${order.service.name}"/> ($<c:out
                                            value="${order.service.price}"/>)</p>
                                </div>
                                <div class="mb-1">
                                    <h5><fmt:message key="label.worker"/></h5>
                                    <p><c:out value="${order.worker.name}"/> (<c:out
                                            value="${order.worker.email}"/>)</p>
                                </div>
                                <div class="mb-1">
                                    <h5><fmt:message key="label.client"/></h5>
                                    <p><c:out value="${order.client.name}"/> (<c:out
                                            value="${order.client.email}"/>)</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>