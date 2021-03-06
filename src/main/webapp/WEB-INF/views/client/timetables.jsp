<%@ include file="../includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.client.select_timeslot"/></h3>

    <form method="post">
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
                        <input name="timeslot-id" type="radio"
                               class="d-none" id="timeslot-${row.id}"
                               value="${row.id}">

                        <label for="timeslot-${row.id}"
                               class="radio-label form-check-label list-group-item list-group-item-action flex-column align-items-start">
                            <h4 class="mb-1">
                                    ${row.fromTime} (${row.duration.toMinutes()} min)
                            </h4>
                            <p class="text-success">
                                <fmt:message key="label.client.available"/>
                            </p>
                        </label>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
        <button class="w-25 mx-auto my-5 btn btn-primary btn-lg btn-block" type="submit">Go to checkout</button>
    </form>
</div>

<%@ include file="../includes/footer.jsp" %>