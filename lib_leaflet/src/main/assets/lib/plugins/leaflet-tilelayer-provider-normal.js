//基于Leaflet.ChineseTmsProviders改造而来
// this L.CRS.Baidu from https://github.com/muyao1987/leaflet-tileLayer-baidugaode/blob/master/src/tileLayer.baidu.js

if (L.Proj) {
    L.CRS.Baidu = new L.Proj.CRS('EPSG:900913',
        '+proj=merc +a=6378206 +b=6356584.314245179 +lat_ts=0.0 +lon_0=0.0 +x_0=0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +wktext  +no_defs',
        {
            resolutions: function () {
                const level = 19;
                const res = [];
                res[0] = Math.pow(2, 18);
                for (let i = 1; i < level; i++) {
                    res[i] = Math.pow(2, (18 - i))
                }
                return res;
            }(),
            origin: [0, 0],
            bounds: L.bounds([20037508.342789244, 0], [0, 20037508.342789244])
        });
}

L.TileLayer.LayerProvider = L.TileLayer.extend({

    initialize: function (name, options) { // (type, Object)
        const allMapProviders = L.TileLayer.LayerProvider.providers;
        options = options || {};
        const parts = name.split('.');
        const mapProvider = parts[0];
        const mapType = parts[1];
        const mapName = parts[2];

        const url = allMapProviders[mapProvider][mapType][mapName];
        options.subdomains = allMapProviders[mapProvider].Subdomains;
        options.key = options.key || allMapProviders[mapProvider].key;

        options.tms = 'tms' in allMapProviders[mapProvider] ? allMapProviders[mapProvider].tms : false;

        L.TileLayer.prototype.initialize.call(this, url, options);
    },
});

L.TileLayer.LayerProvider.providers = {
    //天地图
    TianDiTu: {
        Vector: {
            Normal: "https://t{s}.tianditu.gov.cn/DataServer?T=vec_w&X={x}&Y={y}&L={z}&tk={key}",
        },
        Satellite: {
            Normal: "https://t{s}.tianditu.gov.cn/DataServer?T=img_w&X={x}&Y={y}&L={z}&tk={key}",
        },
        Terrain: {
            Normal: "https://t{s}.tianditu.gov.cn/DataServer?T=ter_w&X={x}&Y={y}&L={z}&tk={key}",
        },
        Annotion: {
            Normal: "https://t{s}.tianditu.gov.cn/DataServer?T=cia_w&X={x}&Y={y}&L={z}&tk={key}",
        },
        Subdomains: ['0', '1', '2', '3', '4', '5', '6', '7'],
        key: "天地图申请key"
    },

    //高德地图
    GaoDe: {
        Vector: {
            Normal: 'https://webrd0{s}.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=8&x={x}&y={y}&z={z}'
        },
        Satellite: {
            Normal: 'https://webst0{s}.is.autonavi.com/appmaptile?style=6&x={x}&y={y}&z={z}',
        },
        Annotion: {
            Normal: "https://webst0{s}.is.autonavi.com/appmaptile?style=8&x={x}&y={y}&z={z}",
        },
        Subdomains: ["1", "2", "3", "4"]
    },

    //谷歌地图
    Google: {
        Vector: {
            Normal: "https://mt{s}.google.cn/vt/lyrs=m@197&gl=cn&x={x}&y={y}&z={z}",
            Simple: "https://mt{s}.google.cn/vt?pb=!1m4!1m3!1i{z}!2i{x}!3i{y}!2m3!1e0!2sm!3i285000000!3m14!2szh-CN!3sCN!5e18!12m1!1e47!12m3!1e37!2m1!1ssmartmaps!12m4!1e26!2m2!1sstyles!2zcy50OjN8cC52Om9mZixzLnQ6MXxwLnY6b2ZmLHMudDoyfHAudjpvZmY!4e0",
            GrayLight: "https://mt{s}.google.cn/maps/vt?pb=!1m5!1m4!1i{z}!2i{x}!3i{y}!4i256!2m3!1e0!2sm!3i519233408!3m23!2szh-CN!3sCN!5e18!12m4!1e68!2m2!1sset!2sRoadmap!12m3!1e37!2m1!1ssmartmaps!12m10!1e26!2m2!1sstyles!2zcy5lOmd8cC5jOiNmZmY1ZjVmNSxzLmU6bC5pfHAudjpvZmYscy5lOmwudC5mfHAuYzojZmY2MTYxNjEscy5lOmwudC5zfHAuYzojZmZmNWY1ZjUscy50OjIxfHMuZTpsLnQuZnxwLmM6I2ZmYmRiZGJkLHMudDoyfHMuZTpnfHAuYzojZmZlZWVlZWUscy50OjJ8cy5lOmwudC5mfHAuYzojZmY3NTc1NzUscy50OjQwfHMuZTpnfHAuYzojZmZlNWU1ZTUscy50OjQwfHMuZTpsLnQuZnxwLmM6I2ZmOWU5ZTllLHMudDozfHMuZTpnfHAuYzojZmZmZmZmZmYscy50OjUwfHMuZTpsLnQuZnxwLmM6I2ZmNzU3NTc1LHMudDo0OXxzLmU6Z3xwLmM6I2ZmZGFkYWRhLHMudDo0OXxzLmU6bC50LmZ8cC5jOiNmZjYxNjE2MSxzLnQ6NTF8cy5lOmwudC5mfHAuYzojZmY5ZTllOWUscy50OjY1fHMuZTpnfHAuYzojZmZlNWU1ZTUscy50OjY2fHMuZTpnfHAuYzojZmZlZWVlZWUscy50OjZ8cy5lOmd8cC5jOiNmZmM5YzljOSxzLnQ6NnxzLmU6bC50LmZ8cC5jOiNmZjllOWU5ZQ!2m2!1scloud_styling_enabled!2strue!2m2!1signore_epochs!2strue!4e0",
            GrayDark: "https://mt{s}.google.cn/maps/vt?pb=!1m5!1m4!1i{z}!2i{x}!3i{y}!4i256!2m3!1e0!2sm!3i519233408!3m23!2szh-CN!3sCN!5e18!12m4!1e68!2m2!1sset!2sRoadmap!12m3!1e37!2m1!1ssmartmaps!12m10!1e26!2m2!1sstyles!2zcy5lOmd8cC5jOiNmZjIxMjEyMSxzLmU6bC5pfHAudjpvZmYscy5lOmwudC5mfHAuYzojZmY3NTc1NzUscy5lOmwudC5zfHAuYzojZmYyMTIxMjEscy50OjF8cy5lOmd8cC5jOiNmZjc1NzU3NSxzLnQ6MTd8cy5lOmwudC5mfHAuYzojZmY5ZTllOWUscy50OjIxfHAudjpvZmYscy50OjE5fHMuZTpsLnQuZnxwLmM6I2ZmYmRiZGJkLHMudDoyfHMuZTpsLnQuZnxwLmM6I2ZmNzU3NTc1LHMudDo0MHxzLmU6Z3xwLmM6I2ZmMTgxODE4LHMudDo0MHxzLmU6bC50LmZ8cC5jOiNmZjYxNjE2MSxzLnQ6NDB8cy5lOmwudC5zfHAuYzojZmYxYjFiMWIscy50OjN8cy5lOmcuZnxwLmM6I2ZmMmMyYzJjLHMudDozfHMuZTpsLnQuZnxwLmM6I2ZmOGE4YThhLHMudDo1MHxzLmU6Z3xwLmM6I2ZmMzczNzM3LHMudDo0OXxzLmU6Z3xwLmM6I2ZmM2MzYzNjLHMudDo3ODV8cy5lOmd8cC5jOiNmZjRlNGU0ZSxzLnQ6NTF8cy5lOmwudC5mfHAuYzojZmY2MTYxNjEscy50OjR8cy5lOmwudC5mfHAuYzojZmY3NTc1NzUscy50OjZ8cy5lOmd8cC5jOiNmZjAwMDAwMCxzLnQ6NnxzLmU6bC50LmZ8cC5jOiNmZjNkM2QzZA!2m2!1scloud_styling_enabled!2strue!2m2!1signore_epochs!2strue!4e0",
            Retro: "https://mt{s}.google.cn/maps/vt?pb=!1m5!1m4!1i{z}!2i{x}!3i{y}!4i256!2m3!1e0!2sm!3i411111338!3m14!2szh-CN!3sCN!5e18!12m1!1e68!12m3!1e37!2m1!1ssmartmaps!12m4!1e26!2m2!1sstyles!2zcy5lOmd8cC5jOiNmZmViZTNjZCxzLnQ6MXxzLmU6Zy5zfHAuYzojZmZjOWIyYTYscy50OjF8cy5lOmwuaXxwLmM6I2ZmZDZjNjlhLHMudDoxfHMuZTpsLnQuZnxwLmM6I2ZmYWQ5OTc0LHMudDo4MnxzLmU6Z3xwLmM6I2ZmZGZkMWFjLHMudDoxMzE0fHMuZTpnfHAuYzojZmZlYmUzYzUscy50OjJ8cy5lOmd8cC5jOiNmZmRmZDJhZSxzLnQ6MnxzLmU6bC50LmZ8cC5jOiNmZmQyYmY3ZCxzLnQ6NDB8cy5lOmcuZnxwLmM6I2ZmYzljZThhLHMudDo0MHxzLmU6bC50LmZ8cC5jOiNmZmM2Y2U3OSxzLnQ6NDl8cC5jOiNmZmUzZDdiNyxzLnQ6NjV8cy5lOmd8cC5jOiNmZmRmZDJhZSxzLnQ6NjV8cy5lOmwudC5mfHAuYzojZmY4ZjdkNzcscy50OjY1fHMuZTpsLnQuc3xwLmM6I2ZmZWJlM2NkLHMudDo2NnxzLmU6Z3xwLmM6I2ZmZGZkMmFlLHMudDo2fHMuZTpnLmZ8cC5jOiNmZmQ1ZTljZCxzLnQ6NnxzLmU6bC5pfHAuYzojZmZiOWE2NzUscy50OjZ8cy5lOmwudC5mfHAuYzojZmZkYWJmNzA!4e0",
        },
        Satellite: {
            Normal: "https://mt{s}.google.cn/vt/lyrs=s@197&gl=cn&x={x}&y={y}&z={z}",
        },
        Annotion: {
            Normal: "https://mt{s}.google.cn/vt/lyrs=y@197&gl=cn&x={x}&y={y}&z={z}",
        },
        Subdomains: ["1", "2", "3"]
    },

    //智图
    Geoq: {
        Vector: {
            Normal: "https://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer/tile/{z}/{y}/{x}",
            //不带标注的暖色图
            WarmOnlySymbol: "http://thematic.geoq.cn/arcgis/rest/services/StreetThematicMaps/Warm_OnlySymbol/MapServer/tile/{z}/{y}/{x}",
            //暖色图的标注
            WarmRefrence: "http://thematic.geoq.cn/arcgis/rest/services/StreetThematicMaps/Warm_Reference/MapServer/tile/{z}/{y}/{x}",
            //不带标注的灰色图
            GrayOnlySymbol: "http://thematic.geoq.cn/arcgis/rest/services/StreetThematicMaps/Gray_OnlySymbol/MapServer/tile/{z}/{y}/{x}",
            //灰色图的标注
            GrayRefrence: "http://thematic.geoq.cn/arcgis/rest/services/StreetThematicMaps/Gray_Reference/MapServer/tile/{z}/{y}/{x}",
            //不带标注的蓝黑图
            PurplishBlueOnlySymbol: "http://thematic.geoq.cn/arcgis/rest/services/StreetThematicMaps/PurplishBlue_OnlySymbol/MapServer/tile/{z}/{y}/{x}",
            //蓝黑图的标注
            PurplishBlueRefrence: "http://thematic.geoq.cn/arcgis/rest/services/StreetThematicMaps/PurplishBlue_Reference/MapServer/tile/{z}/{y}/{x}",
            English: "https://map.geoq.cn/arcgis/rest/services/ChinaOnlineCommunityENG/MapServer/tile/{z}/{y}/{x}",
            CNBoundaryLine: "https://thematic.geoq.cn/arcgis/rest/services/ThematicMaps/administrative_division_boundaryandlabel/MapServer/tile/{z}/{y}/{x}",
            WorldHydro: "https://thematic.geoq.cn/arcgis/rest/services/ThematicMaps/WorldHydroMap/MapServer/tile/{z}/{y}/{x}"
        },
        Subdomains: []
    },

    //微软OpenStreetMap
    OSM: {
        Vector: {
            Normal: "https://{s}.tile.osm.org/{z}/{x}/{y}.png",
        },
        Subdomains: ['a', 'b', 'c']
    },

    //百度地图
    Baidu: {
        Vector: {
            Normal: 'http://online{s}.map.bdimg.com/onlinelabel/?qt=tile&x={x}&y={y}&z={z}&styles=pl&scaler=1&p=1'
        },
        Satellite: {
            Normal: 'http://shangetu{s}.map.bdimg.com/it/u=x={x};y={y};z={z};v=009;type=sate&fm=46',
        },
        Annotion: {
            Normal: "http://online{s}.map.bdimg.com/tile/?qt=tile&x={x}&y={y}&z={z}&styles=sl&v=020",
        },
        Subdomains: ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"],
        tms: true
    },

    //百度地图自定义地图
    BaiduCustom: {
        Normal: {
            Light: 'http://api{s}.map.bdimg.com/customimage/tile?&x={x}&y={y}&z={z}&scale=1&customid=light',
            Dark: 'http://api{s}.map.bdimg.com/customimage/tile?&x={x}&y={y}&z={z}&scale=1&customid=dark',
            MidNight: 'http://api{s}.map.bdimg.com/customimage/tile?&x={x}&y={y}&z={z}&scale=1&customid=midnight',
            GrayScale: 'http://api{s}.map.bdimg.com/customimage/tile?&x={x}&y={y}&z={z}&scale=1&customid=grayscale',
            HardEdge: 'http://api{s}.map.bdimg.com/customimage/tile?&x={x}&y={y}&z={z}&scale=1&customid=hardedge',
            RedAlert: 'http://api{s}.map.bdimg.com/customimage/tile?&x={x}&y={y}&z={z}&scale=1&customid=redalert',
            GoogleLite: 'http://api{s}.map.bdimg.com/customimage/tile?&x={x}&y={y}&z={z}&scale=1&customid=googlelite',
            GrassGreen: 'http://api{s}.map.bdimg.com/customimage/tile?&x={x}&y={y}&z={z}&scale=1&customid=grassgreen',
            Pink: 'http://api{s}.map.bdimg.com/customimage/tile?&x={x}&y={y}&z={z}&scale=1&customid=pink',
            DarkGreen: 'http://api{s}.map.bdimg.com/customimage/tile?&x={x}&y={y}&z={z}&scale=1&customid=darkgreen',
            Bluish: 'http://api{s}.map.bdimg.com/customimage/tile?&x={x}&y={y}&z={z}&scale=1&customid=bluish',
        },
        Subdomains: ["0", "1", "2"],
        tms: true
    },
};
