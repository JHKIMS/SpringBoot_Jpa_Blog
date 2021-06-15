<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="layout/header.jsp"%>
<div class="container">

    <!-- 글 목록 뿌리는 부분 -->
    <c:forEach var="board" items="${boards}">
        <div class="card m-2">
            <div class="card-body">
                <h4 class="card-title">${board.title}</h4>
                <a href="/board/${board.id}" class="btn btn-primary">상세보기</a> <!-- 상세보기 부분 -->
            </div>
        </div>
    </c:forEach>
    <!-- 글 목록 뿌리는 부분 -->

</div>



<%@ include file="layout/footer.jsp"%>
