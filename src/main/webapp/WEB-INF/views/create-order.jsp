<%@ include file="includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center">Create order</h3>

    <form class="needs-validation" novalidate="" method="post">
        <div class="input-group mb-3 mx-auto">
            <div class="input-group-prepend">
                <span class="input-group-text">Timeslot</span>
            </div>
            <input name="timeslot-id" type="text" class="form-control w-50" id="timeslot-input" value="" required="">
        </div>

        <div class="input-group mb-3 mx-auto">
            <div class="input-group-prepend">
                <span class="input-group-text">Worker</span>
            </div>
            <input name="worker-id" type="text" class="form-control w-50" id="author-input" placeholder="" value=""
                   required="">
        </div>

        <div class="input-group mb-3 w-50 mx-auto">
            <div class="input-group-prepend">
                <span class="input-group-text">Service</span>
            </div>
            <input name="service-id" type="text" class="form-control w-25" id="service-input">
        </div>

        <button type="submit" class="w-25 my-5 mx-auto btn btn-lg btn-primary btn-block">Add</button>
    </form>
</div>

<%@ include file="includes/footer.jsp" %>