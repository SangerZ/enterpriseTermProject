<%@include file="/WEB-INF/jspf/header.jspf" %>

<div id="singleColumn">

    <p id="confirmationText">
        [ text ]
        <br><br>
        [ order reference number ]
    </p>

    <div class="summaryColumn" >

        <table id="orderSummaryTable" class="detailsTable" >
            <tr class="header">
                <th style="padding:10px">[ order summary table ]</th>
            </tr>
        </table>

    </div>

    <div class="summaryColumn" >

        <table id="deliveryAddressTable" class="detailsTable">
            <tr class="header">
                <th style="padding:10px">[ customer details ]</th>
            </tr>
        </table>
    </div>
</div>

<%@include file="/WEB-INF/jspf/footer.jspf" %>