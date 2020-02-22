<%@ include file="../includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center">Select order to leave feedback</h3>

    <form method="post">
        <div class="list-group">
            <c:forEach items="${requestScope.page.items}" var="item">
                <input name="worker-id" class="d-none" type="radio"
                       id="worker-<c:out value="${item.id}"/>"
                       value="<c:out value="${item.id}"/>">

                <label for="worker-<c:out value="${item.id}"/>"
                       class="radio-label form-check-label list-group-item list-group-item-action flex-column align-items-start">
                    <div class="d-flex w-100 mb-2 justify-content-between">
                        <h5>
                            <c:out value="${item.date}"/>
                        </h5>
                    </div>
                    <div class="mb-1">
                        <h5>Service</h5>
                        <p><c:out value="${item.service.name}"/> - $<c:out value="${item.service.price}"/></p>
                    </div>
                    <div class="mb-1">
                        <h5>Worker</h5>
                        <p><c:out value="${item.worker.name}"/> - <c:out value="${item.worker.email}"/></p>
                    </div>
                </label>
            </c:forEach>
        </div>
        <label>
            Feedback text:
            <input type="text" name="text">
        </label>
        <button class="w-25 mx-auto my-5 btn btn-primary btn-lg btn-block" type="submit">Submit</button>
    </form>
</div>

<%@ include file="../includes/footer.jsp" %>