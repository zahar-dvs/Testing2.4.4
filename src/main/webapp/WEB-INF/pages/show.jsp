<script type="text/javascript" src=http://code.jquery.com/jquery-1.7.js></script>

<script type="text/javascript">

  window.onload = function(){

    (function(){

      var date = new Date();

      var time = date.getHours()+':'+date.getMinutes()+':'+date.getSeconds();

      document.getElementsByTagName('div')[0].innerHTML = time;

      window.setTimeout(arguments.callee, 1000);

    })();

  };

</script>
