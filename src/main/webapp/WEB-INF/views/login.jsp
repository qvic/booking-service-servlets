<%@ include file="includes/header.jsp" %>

<div class="container">
    <form class="form-signin" method="post">
        <h3 class="pb-3 text-center"><fmt:message key="label.log_in"/></h3>

        <label for="inputEmail" class="sr-only"></label>
        <input name="email" type="text" id="inputEmail" class="form-control" placeholder="Your email" required=""
               autofocus="">

        <label for="inputPassword" class="sr-only"></label>
        <input name="password" type="password" id="inputPassword" class="mt-1 form-control" placeholder="Password"
               required="">

        <button class="mt-3 btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="label.log_in"/></button>
    </form>
</div>

<%@ include file="includes/footer.jsp" %>
