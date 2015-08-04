<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src=http://code.jquery.com/jquery-1.7.js></script>
<br>
<t:template>
<div id="content" align="center" class="container" style = "text-align: center; width: 500px;">
<div id="timer" align="center">
  00:15
  <!-- <br><input id="finish" type="button" value="Finish Test"> -->
</div>

<br>
<div id="question" align="center">
  <%@include file="question.jsp" %>
</div>
<div align="center">
  <br><input id="next" class="btn btn-lg btn-primary btn-block3" type="button" value="Далее" style="width: 150px">
</div>
  </div>
  </t:template>


<script>
  var min = 0;
  var sec = 15;
  var timerid;
  var result;
  var count = 0;

  function showque(){
    if($("input[type=radio]:checked").length == 1){
      result = $("input[type=radio]:checked").val();
    } else {
      result=0;
    }

    count++;
    if (count == 4){$("input[type=button]").val("Закончить тест");}
    if (count == 5){$("input[type=button]").val("Попробовать еще раз");}
    if (count < 5){
      sec = 15;
      $.ajax({
        type: "POST",
        url: "/question",
        data: {res: result},
        success: function(html){
          $("#question").html(html);
        }
      });
    } else {
      clearInterval(timerid);
      $.ajax({
        type: "POST",
        url: "/result",
        data: {res: result},
        success: function(html){
          $("#question").html(html);
        }
      });
    }
  }

  function timer(){
    sec--;
    if (sec<0){
      sec = 59;
      min--;
      }
    var smin = ''+min;
    var ssec = ''+sec;
    if (smin.length<2) smin = '0'+smin;
    if (ssec.length<2) ssec = '0'+ssec;
    document.getElementById("timer").innerHTML = smin+':'+ssec;
    if (min==0 && sec==0){
      sec = 15;
      showque();
      }
  }
  $("#next").click(function(){
            if ($("input[type=button]").val() == "Попробовать еще раз"){
              document.location = "/showtest";
              $("input[type=button]").css({visibility: "hidden", display: " "});
            } else if($("input[type=radio]:checked").length == 1) {
              showque();
            }else{
              alert("Выберите ответ");
            }
          }
  );
  timerid = setInterval(timer,1000);
</script>