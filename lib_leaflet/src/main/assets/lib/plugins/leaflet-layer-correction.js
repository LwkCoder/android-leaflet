//基于leaflet.mapCorrection插件改造而来
//坐标转换
L.CoordConver = function () {
    const a = 6378245.0;
    const ee = 0.00669342162296594323;
    const pi = 3.1415926535897932384626;
    const x_pi = pi * 3000.0 / 180.0;
    const R = 6378137;

    /**百度转84*/
    this.bd09_To_gps84 = function (lng, lat) {
        const gcj02 = this.bd09_To_gcj02(lng, lat);
        return this.gcj02_To_gps84(gcj02.lng, gcj02.lat);
    };
    /**84转百度*/
    this.gps84_To_bd09 = function (lng, lat) {
        const gcj02 = this.gps84_To_gcj02(lng, lat);
        return this.gcj02_To_bd09(gcj02.lng, gcj02.lat);
    };
    /**84转火星*/
    this.gps84_To_gcj02 = function (lng, lat) {
        let dLat = transformLat(lng - 105.0, lat - 35.0);
        let dLng = transformLng(lng - 105.0, lat - 35.0);
        const radLat = lat / 180.0 * pi;
        let magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        const sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLng = (dLng * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        const mgLat = lat + dLat;
        const mgLng = lng + dLng;
        return {
            lng: mgLng,
            lat: mgLat
        };
    };
    /**火星转84*/
    this.gcj02_To_gps84 = function (lng, lat) {
        const coord = transform(lng, lat);
        const lontitude = lng * 2 - coord.lng;
        const latitude = lat * 2 - coord.lat;
        return {
            lng: lontitude,
            lat: latitude
        };
    };
    /**火星转百度*/
    this.gcj02_To_bd09 = function (x, y) {
        const z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        const theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        const bd_lng = z * Math.cos(theta) + 0.0065;
        const bd_lat = z * Math.sin(theta) + 0.006;
        return {
            lng: bd_lng,
            lat: bd_lat
        };
    };
    /**百度转火星*/
    this.bd09_To_gcj02 = function (bd_lng, bd_lat) {
        const x = bd_lng - 0.0065;
        const y = bd_lat - 0.006;
        const z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        const theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        const gg_lng = z * Math.cos(theta);
        const gg_lat = z * Math.sin(theta);
        return {
            lng: gg_lng,
            lat: gg_lat
        };
    };

    function transform(lng, lat) {
        let dLat = transformLat(lng - 105.0, lat - 35.0);
        let dLng = transformLng(lng - 105.0, lat - 35.0);
        const radLat = lat / 180.0 * pi;
        let magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        const sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLng = (dLng * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        const mgLat = lat + dLat;
        const mgLng = lng + dLng;
        return {
            lng: mgLng,
            lat: mgLat
        };
    }

    function transformLat(x, y) {
        let ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    function transformLng(x, y) {
        let ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }
};

L.coordConver = function () {
    return new L.CoordConver();
};

L.tileLayer.layerProvider = function (type, options) {
    options = options || {};
    options.corrdType = getCorrdType(type);
    return new L.TileLayer.LayerProvider(type, options);

    //获取坐标类型
    function getCorrdType(type) {
        const parts = type.split('.');
        const providerName = parts[0];
        let corrdName = "wgs84";
        switch (providerName) {
            case "Geoq":
            case "GaoDe":
            case "Google":
                corrdName = "gcj02";
                break;
            case "Baidu":
            case "BaiduCustom":
                corrdName = "bd09";
                break;
            case "OSM":
            case "TianDiTu":
                corrdName = "wgs84";
                break;
        }
        return corrdName;
    }
};

L.tileLayer.tencentProvider = function (type, options) {
    options = options || {};
    options.corrdType = "gcj02";
    return new L.TileLayer.TencentProvider(type, options);
};

L.GridLayer.include({
    _setZoomTransform: function (level, _center, zoom) {
        let center = _center;
        if (center !== undefined && this.options) {
            if (this.options.corrdType === 'gcj02') {
                center = L.coordConver().gps84_To_gcj02(_center.lng, _center.lat);
            } else if (this.options.corrdType === 'bd09') {
                center = L.coordConver().gps84_To_bd09(_center.lng, _center.lat);
            }
        }
        const scale = this._map.getZoomScale(zoom, level.zoom),
            translate = level.origin.multiplyBy(scale)
                .subtract(this._map._getNewPixelOrigin(center, zoom)).round();

        if (L.Browser.any3d) {
            L.DomUtil.setTransform(level.el, translate, scale);
        } else {
            L.DomUtil.setPosition(level.el, translate);
        }
    },
    _getTiledPixelBounds: function (_center) {
        let center = _center;
        if (center !== undefined && this.options) {
            if (this.options.corrdType === 'gcj02') {
                center = L.coordConver().gps84_To_gcj02(_center.lng, _center.lat);
            } else if (this.options.corrdType === 'bd09') {
                center = L.coordConver().gps84_To_bd09(_center.lng, _center.lat);
            }
        }
        const map = this._map,
            mapZoom = map._animatingZoom ? Math.max(map._animateToZoom, map.getZoom()) : map.getZoom(),
            scale = map.getZoomScale(mapZoom, this._tileZoom),
            pixelCenter = map.project(center, this._tileZoom).floor(),
            halfSize = map.getSize().divideBy(scale * 2);

        return new L.Bounds(pixelCenter.subtract(halfSize), pixelCenter.add(halfSize));
    }
});