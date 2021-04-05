(function($){
   /**
   *
   */
   function Column (config){
     var position = config.position || 0;
     var html = config.html || '';
     function create() {
         var column =  document.createElement("ul");
         column.id = config.id;
         column.className = "column";
         column.style = "width:"+config.width+"px";
         return $(column);
     };
     var $el = create();
     return {
          appendCell:function(data) {
            var h = "";
            if(html instanceof Function) {
              h = $(html(data));
            }else{
              h = html;
            }
            $el.append(h);
          },
          element:function(){
             return  $el;
          }
      }
   };

   /**
   *  �ٲ���
   *  el        string
   *  colWidth  number
   *  cellHtml  function
   */
   function FixedWaterFall(options) {
      var container = document.getElementById(options.el || 'waterfall') ,
      colWidth = options.colWidth || 200,
//      colNum = options.colNum || 3,
      loadFinish = false,page=0,columns = [];
      var $container = $(container);
      $container.addClass("flow");

      var cellHtml = options.cellHtml || "";


      function load(api,callback) {
          if(api) {
             $.getJSON(api,function(resp,status){
               callback(resp);
             });
          }
      };

      function init() {
        var width = $container.width();
        var colNum = Math.floor(width/colWidth);
        for(var i=0;i<colNum;i++) {
              var column =  new Column({id:'col_'+i,position:i,width:colWidth,html:cellHtml});
              $container.append(column.element());
              columns.push(column);
        }
      };
      function detectFitCol() {
         var target = null;
         var min = Number.MAX_VALUE;
         for(var i=0;i<columns.length;i++) {
            var colTemp = columns[i];
            var height = colTemp.element().height();
            if(height<min) {
                min = height;
                target = colTemp;
            }
         }
         return target;
      }
      init();
      var sense = {

        justify:function(){

        },

        more:function(api,callback) {

           load(api,callback);
        },

        append:function(data) {
          detectFitCol().appendCell(data);
          return this;
        }
      }
      $container.on("resize",function(){
         sense.justify();
      });
      return sense;
    }
   $.fn.FixedWaterFall = FixedWaterFall;
})(jQuery || layui.jquery)