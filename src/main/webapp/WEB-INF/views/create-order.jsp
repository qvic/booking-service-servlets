<%@ include file="includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center">Create order</h3>

    <div class="jumbotron w-50 mx-auto text-center">
        <h3>Already selected:</h3>
        <ul class="h5">
            <c:if test="${sessionScope.timeslotId ne null}">
                <li>Timeslot</li>
            </c:if>
            <c:if test="${sessionScope.serviceId ne null}">
                <li>Service</li>
            </c:if>
            <c:if test="${sessionScope.workerId ne null}">
                <li>Worker</li>
            </c:if>
        </ul>

        <form method="post">
            <button class="w-50 my-5 mx-auto btn btn-primary btn-lg btn-block" type="submit">Submit</button>
        </form>
    </div>
</div>

<%@ include file="includes/footer.jsp" %>