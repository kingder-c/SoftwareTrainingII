/*
描述：关于雨量站的相关功能
功能：获取数据，添加标注，清楚数据
作者：kingder
时间：2018/03/29
*/
var rainClass={
    addMark:function(x,y,info,data,id){
        var sContent ='<button type="button" id="myButton" data-loading-text="Loading..." class="btn btn-primary" autocomplete="off">Loading state</button>';
        var point = new BMap.Point(x, y);
        var myIcon = new BMap.Icon("images/symble/rain1.png", new BMap.Size(50,50));
        var marker = new BMap.Marker(point,{icon:myIcon});
        var infoWindow = new BMap.InfoWindow(sContent);
        map.addOverlay(marker);
        marker.addEventListener("click", function(){          
            this.openInfoWindow(infoWindow);
            //图片加载完毕重绘infowindow
            document.getElementById('imgDemo').onload = function (){
                infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
            }
         });
    },
    getData:function(){
        var data=[];
        //TODO:hou
        data.push({'x':116.417,'y':39.909,'info':'天安门积水点','data':0,'id':'w01'});
        data.push({'x':116.441063,'y':39.954548,'info':'东直门积水点','data':0,'id':'w02'});

        //获取数据传入回调
        this.callBack(data);

    },
    callBack:function(data){
        for(var i=0;i<data.length;i++)
          this.addMark(data[i].x, data[i].y,data[i].info,data[i].data,data[i].id);

    },
    hideAll:function(){
        map.clearOverlays();  
    }

}

var mapTool={
    hideAll:function(){
        map.clearOverlays(); 

    }
}