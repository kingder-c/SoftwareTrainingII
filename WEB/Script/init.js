$(function () {
    StartApp();
})

function StartApp() {
    initMap()
    initEvent()

}

function initMap() {
    // 百度地图API功能
    map = new BMap.Map("allmap");    // 创建Map实例（强制全局）
    map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
    //添加地图类型控件
    map.addControl(new BMap.MapTypeControl({
        mapTypes: [
            BMAP_NORMAL_MAP,
            BMAP_HYBRID_MAP
        ]
    }));
    map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
    map.enableScrollWheelZoom(true);
    document.getElementById("allmap").style.height=document.body.clientHeight-51+"px";
}
function initEvent() {
    $("#rainFallShow").click(function () {
        if ($("#rainFallShow").hasClass("active")) {
            //隐藏
            $("#rainFallShow").removeClass();
            mapTool.hideAll();

        }
        else {
            mapTool.hideAll();

            //显示
            $("#rainFallShow").addClass("active");
            rainClass.getData();

        }

    })
}