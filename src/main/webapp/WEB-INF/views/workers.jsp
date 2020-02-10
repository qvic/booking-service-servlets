<%@ include file="includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.client.select_worker"/></h3>

    <form method="post">
        <div class="list-group">
            <c:forEach items="${requestScope.workers}" var="item">
                <input name="worker-id" class="d-none" type="radio"
                       id="worker-<c:out value="${item.id}"/>"
                       value="<c:out value="${item.id}"/>">

                <label for="worker-<c:out value="${item.id}"/>"
                       class="radio-label form-check-label list-group-item list-group-item-action flex-column align-items-start">
                    <h5 class="mb-1">
                        <c:out value="${item.name}"/>
                    </h5>
                </label>
            </c:forEach>
            <button class="w-25 mx-auto my-5 btn btn-primary btn-lg btn-block" type="submit">Next</button>
        </div>
    </form>
</div>

<%@ include file="includes/footer.jsp" %>