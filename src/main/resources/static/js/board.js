let index={
    init: function () {
        $("#btn-save").on("click",()=>{
            this.save();
        });

        $("#btn-delete").on("click",()=>{
            this.deleteById();
        });

        $("#btn-update").on("click",()=>{
            this.update();
        });
    },

    save:function(){

        let data={
            title : $("#title").val(),
            content : $("#content").val(),
        }

        $.ajax({
            type:"POST",
            url:"/api/board",
            data: JSON.stringify(data),
            contentType : "application/json; charset=utf-8",
            dataType : "json"
        }).done(function(resp){
            if(resp.status === 500){
                alert("글쓰기 실패! ! !");
            }else{
                alert("글쓰기 완료");
                location.href="/";
            }

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    deleteById:function(){

        let id=$("#id").text();

        $.ajax({
            type: "DELETE",
            url: "/api/board/"+id,
            dataType : "json"
        }).done(function(resp) {
            alert("삭제가 완료되었다.");
            location.href="/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    update:function(){
        let id = $("#id").val();

        let data={
            title : $("#title").val(),
            content : $("#content").val(),
        }

        $.ajax({
            type:"PUT",
            url:"/api/board/"+id,
            data: JSON.stringify(data),
            contentType : "application/json; charset=utf-8",
            dataType : "json"
        }).done(function(resp){
            if(resp.status === 500){
                alert("글수정 실패! ! !");
            }else{
                alert("글수정 완료");
                location.href="/";
            }

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },


}

index.init();