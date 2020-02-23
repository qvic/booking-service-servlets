<%@ include file="../includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.worker.timetable"/></h3>

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
                        No available places
                    </div>
                </c:if>

                <c:forEach items="${timetable.rows}" var="row">
                    <div class="list-group-item list-group-item-action flex-column align-items-start">
                        <h4 class="my-2">
                                ${row.fromTime} to ${row.fromTime.plus(row.duration)}
                        </h4>
                        <c:forEach items="${row.orders}" var="order">
                            <div class="mb-2">
                                <h5>Order #${order.id}</h5>
                                <p class="mb-0">Worker: ${order.worker.name} (${order.worker.email})</p>
                                <p class="mb-0">Client: ${order.client.name} (${order.client.email})</p>
                                <p class="mb-1">Service: ${order.service.name} ($${order.service.price})</p>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>