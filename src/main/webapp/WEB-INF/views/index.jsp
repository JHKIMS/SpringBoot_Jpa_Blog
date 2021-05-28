<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="layout/header.jsp"%>
<div class="container">

    <!-- 글 목록 뿌리는 부분 -->
    <c:forEach var="board" items="${boards.content}">
        <div class="card m-2">
            <div class="card-body">
                <h4 class="card-title">${board.title}</h4>
                <a href="/board/${board.id}" class="btn btn-primary">상세보기</a> <!-- 상세보기 부분 -->
            </div>
        </div>
    </c:forEach>
    <!-- 글 목록 뿌리는 부분 -->

    <!-- 페이징 하는 부분 -->
    <ul class="pagination justify-content-center">
        <c:choose>
            <c:when test="${boards.first}">
                <li class="page-item disabled">
                    <a class="page-link" href="?page=${boards.number-1}">Previous</a>
                </li>
            </c:when>

            <c:otherwise>
                <li class="page-item">
                    <a class="page-link" href="?page=${boards.number-1}">Previous</a>
                </li>
            </c:otherwise>

        </c:choose>

        <c:choose>
            <c:when test="${boards.last}">
                <li class="page-item disabled">
                    <a class="page-link" href="?page=${boards.number+1}">Next</a>
                </li>
            </c:when>

            <c:otherwise>
                <li class="page-item">
                    <a class="page-link" href="?page=${boards.number+1}">Next</a>
                </li>
            </c:otherwise>
        </c:choose>

    </ul>
    <!-- 페이징 처리 끝 -->
</div>



<%@ include file="layout/footer.jsp"%>
