//显示雨量站
var rainClass={
    addMark:function(x,y,info){
        var sContent ='<button type="button" id="myButton" data-loading-text="Loading..." class="btn btn-primary" autocomplete="off">Loading state</button>';
        var point = new BMap.Point(x, y);
        var myIcon = new BMap.Icon("http://lbsyun.baidu.com/jsdemo/img/fox.gif", new BMap.Size(300,157));
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

        //获取数据传入回调
        this.callBack(data);

    },
    callBack:function(data){
        this.addMark(116.417, 39.909,"");

    }

}