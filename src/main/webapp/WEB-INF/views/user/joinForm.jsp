<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>KIM'S BLOG</title>
</head>

<body>
<div class="container">

    <form>
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" name="username" class="form-control" placeholder="Enter username" id="username" required="required" />
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" placeholder="Enter password" id="password" required="required"/>
        </div>

        <div class="form-group">
            <label for="email">Email address:</label>
            <input type="email" class="form-control" placeholder="Enter email" id="email" required="required"/>
        </div>

    </form>

    <button id="btn-save" class="btn btn-primary">회원가입 하기</button>
</div>

<script src="/js/user.js"></script>

</body>
<%@ include file="../layout/footer.jsp" %>
</html>


