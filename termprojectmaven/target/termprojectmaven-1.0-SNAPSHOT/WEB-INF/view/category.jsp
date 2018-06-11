<%@include file="/WEB-INF/jspf/header.jspf" %>

<div id="categoryLeftColumn">
    <c:forEach var="category" items="${categoryItems}" begin="0" end="3">
        <c:choose>
            <c:when test="${category.id == pageContext.request.queryString}" >
                <div class="categoryButton" id="selectedCategory">
                    <span class="categoryText">${category.name}</span>
                </div>
            </c:when>
            <c:otherwise>
                <a href="category?${category.id}" class="categoryButton">
                    <span class="categoryText">${category.name}</span>
                </a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>

<div id="categoryRightColumn">

    <c:forEach var="category" items="${categoryItems}" begin="0" end="3">
        <c:if test="${category.id == pageContext.request.queryString}">
            <p id="categoryTitle">${category.name}</p>
        </c:if>
    </c:forEach>



    <table id="productTable">

        <c:forEach var="product" items="${productItems}" begin="0" end="15">
            <c:if test="${product.categoryId == pageContext.request.queryString}">
                <tr class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
                    <td>
                        <img src="${initParam.productImagePath}/${product.name}.jpg" alt="${product.name}" width="142" height="80">
                    </td>
                    <td>
                        ${product.name}
                        <br>
                        <span class="smallText">${product.description}</span>
                    </td>
                    <td>
                        &dollar; ${product.price} / unit
                    </td>
                    <td>
                        <form action="addToCart" method="post">
                            <input type="hidden" name="productId" value="${product.id}">
                            <input type="submit" value="add to cart">
                        </form>
                    </td>
                </tr>
            </c:if>
        </c:forEach>

    </table>
</div>

<%@include file="/WEB-INF/jspf/footer.jspf" %>