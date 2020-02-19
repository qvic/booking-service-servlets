<%@ include file="../includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.client.select_timeslot"/></h3>

    <form method="post">
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
                        <c:choose>
                            <c:when test="${row.order eq null}">
                                <input name="timeslot-id" type="radio"
                                       class="d-none" id="timeslot-<c:out value="${row.id}"/>"
                                       value="<c:out value="${row.id}"/>">

                                <label for="timeslot-<c:out value="${row.id}"/>"
                                       class="radio-label form-check-label list-group-item list-group-item-action flex-column align-items-start">
                                    <h5 class="mb-1">
                                        <c:out value="${row.fromTime}"/> to <c:out value="${row.fromTime.plus(row.duration)}"/>
                                    </h5>
                                    <p class="text-success">
                                        Available
                                    </p>
                                </label>
                            </c:when>
                            <c:otherwise>
                                <div class="list-group-item flex-column align-items-start">
                                    <h5 class="mb-1">
                                        <c:out value="${row.fromTime}"/> to <c:out value="${row.fromTime.plus(row.duration)}"/>
                                    </h5>
                                    <p class="text-secondary">
                                        Booked
                                    </p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
        <button class="w-25 mx-auto my-5 btn btn-primary btn-lg btn-block" type="submit">Next</button>
    </form>
</div>

<%@ include file="../includes/footer.jsp" %>