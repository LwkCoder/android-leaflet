//腾讯地图的切片服务
L.TileLayer.TencentProvider = L.TileLayer.extend({

    initialize: function (name, options) { // (type, Object)
        const allMapProviders = L.TileLayer.TencentProvider.providers;
        options = options || {};
        const parts = name.split('.');
        const mapProvider = parts[0];
        const mapType = parts[1];
        const mapName = parts[2];

        const url = allMapProviders[mapProvider][mapType][mapName];
        options.subdomains = allMapProviders[mapProvider].Subdomains;
        options.tms = false;

        L.TileLayer.prototype.initialize.call(this, url, options);
    },
    //重写getTileUrl方法，配置不同的URL匹配规则
    getTileUrl: function (tilePoint) {
        return L.Util.template(this._url, this._tencentTileUrlArg(tilePoint));
    },
    //腾讯的URL配置规则
    _tencentTileUrlArg: function (tilePoint) {
        return {
            s: this._getSubdomain(tilePoint),
            x: tilePoint.x,
            y: Math.pow(2, tilePoint.z) - 1 - tilePoint.y,
            z: tilePoint.z,
            r: Math.floor(tilePoint.x / 16),
            c: Math.floor((Math.pow(2, tilePoint.z) - 1 - tilePoint.y) / 16)
        }
    }
});

L.TileLayer.TencentProvider.providers = {

    //腾讯地图
    Tencent: {
        Vector: {
            Normal: "https://rt{s}.map.gtimg.com/realtimerender?z={z}&x={x}&y={y}&type=vector&style=2",
        },
        Satellite: {
            Normal: "https://p{s}.map.gtimg.com/sateTiles/{z}/{r}/{c}/{x}_{y}.jpg?version=239",
        },
        Terrain: {
            Normal: "https://p{s}.map.gtimg.com/demTiles/{z}/{r}/{c}/{x}_{y}.jpg",
        },
        Annotion: {
            Normal: "https://rt{s}.map.gtimg.com/tile?z={z}&x={x}&y={y}&type=vector&styleid=3&version=695",
        },
        Subdomains: ["0", "1", "2", "3"],
    }
};

L.tileLayer.tencentProvider = function (type, options) {
    return new L.TileLayer.LayerProvider(type, options);
};