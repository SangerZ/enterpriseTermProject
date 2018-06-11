<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jspf/header.jspf" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<div id="indexLeftColumn">
    <div id="welcomeText">
        <p>The tennis shop born from mother earth.
           Please enjoy yourself while browsing through
           a very, very limited range of items.</p>
    </div>
</div>

<div id="indexRightColumn">
    <c:choose>
        <c:when test="${not empty categoryItems}">
            <c:forEach var="category" items="${categoryItems}" begin="0" end="3">
                <div class="categoryBox">
                    <a href="category?${category.id}">
                        <span class="categoryLabelText">${category.name}</span>
                        <img src="${initParam.categoryImagePath}/${category.name}.jpg"alts="${category.name}">
                    </a>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div id="indexRightColumn">
                <div class="categoryBox">
                    <a href="#">
                        <span class="categoryLabelText">1</span>
                    </a>
                </div>
                <div class="categoryBox">
                    <a href="#">
                        <span class="categoryLabelText">2</span>
                    </a>
                </div>
                <div class="categoryBox">
                    <a href="#">
                        <span class="categoryLabelText">3</span>
                    </a>
                </div>
                <div class="categoryBox">
                    <a href="#">
                        <span class="categoryLabelText">4</span>
                    </a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>


</div>

<%@include file="/WEB-INF/jspf/footer.jspf" %>