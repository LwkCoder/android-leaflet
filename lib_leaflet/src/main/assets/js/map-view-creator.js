//创建全局地图对象
//初始设置中心为北京市天安门
var map = L.map("map", {
    //最小缩放等级
    minZoom: 3,
    //最大缩放等级
    maxZoom: 18,
    //界面上显示缩放按钮
    zoomControl: false,
    //界面上显示属性标签
    attributionControl: false,
    //调用一次缩放控制方法的影响粒度，可以理解为zoomIn()、zoomOut()调用后，地图缩放的粒度，取值范围[0,1]
    zoomDelta: 0.5,
    //双击缩放地图
    doubleClickZoom: true,
    //拖动地图
    dragging: true,
    //默认位置为天安门广场
    center: [39.90725, 116.39128],
    //默认缩放等级为3级
    zoom: 3
});
// .setView([39.90725, 116.39128], 3);