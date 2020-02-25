<%@ include file="includes/header.jsp" %>

<div class="d-flex h-100 justify-content-center align-items-center">
    <form class="w-25" method="post">
        <h3 class="pb-3 text-center"><fmt:message key="label.log_in"/></h3>

        <label for="inputEmail" class="sr-only"></label>
        <input name="email" type="email" id="inputEmail" class="form-control"
               placeholder="<fmt:message key="label.email"/>"
               required autofocus>

        <label for="inputPassword" class="sr-only"></label>
        <input name="password" type="password" id="inputPassword" class="mt-1 form-control"
               placeholder="<fmt:message key="label.password"/>"
               required>

        <button class="mt-3 btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="label.log_in"/></button>
    </form>
</div>

<%@ include file="includes/footer.jsp" %>
