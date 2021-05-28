<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
    <form>
    <div class="form-group">
        <label for="title">Title</label>
        <input type="text" class="form-control" placeholder="Enter Title" id="title">
    </div>

    <div class="form-group">
        <label for="content">Content</label>
        <textarea class="form-control summernote" rows="5" id="content"></textarea>
    </div>


    <a href="https://kauth.kakao.com/oauth/authorize?client_id=9cb8e85f7d86457608c1c50b988da6a2
						&redirect_uri=http://localhost:8000/auth/kakao/callback
						&response_type=code">
        <img height="38px" src="/image/kakao_login_button.png"/>
    </a>
    </form>
    <button id="btn-save" class="btn btn-primary">글쓰기완료</button>
</div>

<%--SummerNote 추가하는 부분--%>
<script>
    $('.summernote').summernote({
        tabsize: 2,
        height: 300
    });
</script>


<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>
