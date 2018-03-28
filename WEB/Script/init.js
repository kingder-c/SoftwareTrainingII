$(function(){
    AppStart();
})
//应用程序开始
function AppStart(){
    startMap();
    eventBand();

}
//初始化百度地图
function startMap(){
	// 百度地图API功能
	map = new BMap.Map("allmap");    // 创建Map实例(全局)
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
	//添加地图类型控件
	map.addControl(new BMap.MapTypeControl({
		mapTypes:[
            BMAP_NORMAL_MAP,
            BMAP_HYBRID_MAP
        ]}));	  
	map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
}
//绑定按钮点击事件
function eventBand(){
    //绑定雨量站事件
    $('#rainFallShow').click(function(){
        rainClass.getData();
      });
}