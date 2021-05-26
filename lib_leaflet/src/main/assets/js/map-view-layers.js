/**
 * 查询是否有指定图层
 * @param {string} name
 */
function hasLayer(name) {
    map.eachLayer(function (layer) {
        if (layer.getAttribution() === name) {
            return true;
        }
    });
    return false;
}

/**
 * 根据名字移除图层
 * @param name [string] 图层名称
 */
function removeLayer(name) {
    map.eachLayer(function (layer) {
        if (layer.getAttribution() === name) {
            map.removeLayer(layer);
        }
    });
}

const MAP_BASE_LAYER = "MapBaseLayer";

/**
 * 切换天地图卫星图
 * @param token 天地图token
 * @param showAnnotion 是否显示标注
 */
function showTiandituSatellite(token, showAnnotion) {
    _resetCRS(false);

    if (typeof (token) == 'undefined') {
        alert("You have to defined the token of Tianditu");
        token = "";
    }
    showAnnotion = typeof (showAnnotion) == 'undefined' ? true : showAnnotion;

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('TianDiTu.Satellite.Normal', {
        key: token,
        attribution: MAP_BASE_LAYER
    }).addTo(map);
    if (showAnnotion) {
        L.tileLayer.layerProvider('TianDiTu.Annotion.Normal', {
            key: token,
            attribution: MAP_BASE_LAYER
        }).addTo(map);
    }
}

/**
 * 切换天地图矢量图
 * @param token 天地图token
 * @param showAnnotion 是否显示标注
 */
function showTiandituVector(token, showAnnotion) {
    _resetCRS(false);

    if (typeof (token) == 'undefined') {
        alert("You have to defined the token of Tianditu");
        token = "";
    }
    showAnnotion = typeof (showAnnotion) == 'undefined' ? true : showAnnotion;

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('TianDiTu.Vector.Normal', {
        key: token,
        attribution: MAP_BASE_LAYER
    }).addTo(map);
    if (showAnnotion) {
        L.tileLayer.layerProvider('TianDiTu.Annotion.Normal', {
            key: token,
            attribution: MAP_BASE_LAYER
        }).addTo(map);
    }
}

/**
 * 切换天地图地形图
 * @param token 天地图token
 * @param showAnnotion 是否显示标注
 */
function showTiandituTerrain(token, showAnnotion) {
    _resetCRS(false);

    if (typeof (token) == 'undefined') {
        alert("You have to defined the token of Tianditu");
        token = "";
    }
    showAnnotion = typeof (showAnnotion) == 'undefined' ? true : showAnnotion;

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('TianDiTu.Terrain.Normal', {
        key: token,
        attribution: MAP_BASE_LAYER
    }).addTo(map);
    if (showAnnotion) {
        L.tileLayer.layerProvider('TianDiTu.Annotion.Normal', {
            key: token,
            attribution: MAP_BASE_LAYER
        }).addTo(map);
    }
}

/**
 * 切换高德卫星图
 * @param showAnnotion 是否显示标注
 */
function showGaodeSatellite(showAnnotion) {
    _resetCRS(false);

    showAnnotion = typeof (showAnnotion) == 'undefined' ? true : showAnnotion;

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('GaoDe.Satellite.Normal', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
    if (showAnnotion) {
        L.tileLayer.layerProvider('GaoDe.Annotion.Normal', {
            attribution: MAP_BASE_LAYER
        }).addTo(map);
    }
}

/**
 * 切换高德矢量图
 */
function showGaodeVector() {
    _resetCRS(false);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('GaoDe.Vector.Normal', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换谷歌卫星图（需要翻墙）
 * @param showAnnotion 是否显示标注
 */
function showGoogleSatellite(showAnnotion) {
    _resetCRS(false);

    showAnnotion = typeof (showAnnotion) == 'undefined' ? true : showAnnotion;

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('Google.Satellite.Normal', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
    if (showAnnotion) {
        L.tileLayer.layerProvider('Google.Annotion.Normal', {
            attribution: MAP_BASE_LAYER
        }).addTo(map);
    }
}

/**
 * 切换谷歌普通矢量图（需要翻墙）
 */
function showGoogleNormalVector() {
    _resetCRS(false);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('Google.Vector.Normal', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换谷歌简洁矢量图（需要翻墙）
 */
function showGoogleSimpleVector() {
    _resetCRS(false);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('Google.Vector.Simple', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换谷歌浅灰矢量图（需要翻墙）
 */
function showGoogleGrayLightVector() {
    _resetCRS(false);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('Google.Vector.GrayLight', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换谷歌深灰矢量图（需要翻墙）
 */
function showGoogleGrayDarkVector() {
    _resetCRS(false);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('Google.Vector.GrayDark', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换谷歌复古矢量图（需要翻墙）
 */
function showGoogleRetroVector() {
    _resetCRS(false);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('Google.Vector.Retro', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换百度卫星图
 * @param showAnnotion 是否显示标注
 */
function showBaiduSatellite(showAnnotion) {
    _resetCRS(true);

    showAnnotion = typeof (showAnnotion) == 'undefined' ? true : showAnnotion;

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('Baidu.Satellite.Normal', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
    if (showAnnotion) {
        L.tileLayer.layerProvider('Baidu.Annotion.Normal', {
            attribution: MAP_BASE_LAYER
        }).addTo(map);
    }
}

/**
 * 切换百度矢量图
 */
function showBaiduVector() {
    _resetCRS(true);

    removeLayer(MAP_BASE_LAYER);

    L.tileLayer.layerProvider('Baidu.Vector.Normal', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换百度自定义Light图
 */
function showBaiduCustomLight() {
    _resetCRS(true);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('BaiduCustom.Normal.Light', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换百度自定义Dark图
 */
function showBaiduCustomDark() {
    _resetCRS(true);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('BaiduCustom.Normal.Dark', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换百度自定义MidNight图
 */
function showBaiduCustomMidNight() {
    _resetCRS(true);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('BaiduCustom.Normal.MidNight', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换百度自定义GrayScale图
 */
function showBaiduCustomGrayScale() {
    _resetCRS(true);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('BaiduCustom.Normal.GrayScale', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换百度自定义HardEdge图
 */
function showBaiduCustomHardEdge() {
    _resetCRS(true);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('BaiduCustom.Normal.HardEdge', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换百度自定义RedAlert图
 */
function showBaiduCustomRedAlert() {
    _resetCRS(true);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('BaiduCustom.Normal.RedAlert', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换百度自定义GoogleLite图
 */
function showBaiduCustomGoogleLite() {
    _resetCRS(true);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('BaiduCustom.Normal.GoogleLite', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换百度自定义GrassGreen图
 */
function showBaiduCustomGrassGreen() {
    _resetCRS(true);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('BaiduCustom.Normal.GrassGreen', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换百度自定义Pink图
 */
function showBaiduCustomPink() {
    _resetCRS(true);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('BaiduCustom.Normal.Pink', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换百度自定义DarkGreen图
 */
function showBaiduCustomDarkGreen() {
    _resetCRS(true);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('BaiduCustom.Normal.DarkGreen', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换百度自定义Bluish图
 */
function showBaiduCustomBluish() {
    _resetCRS(true);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('BaiduCustom.Normal.Bluish', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换OpenStreetMap矢量图
 */
function showOSMVector() {
    _resetCRS(false);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('OSM.Vector.Normal', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换智图普通矢量图
 */
function showGeoqNormalVector() {
    _resetCRS(false);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('Geoq.Vector.Normal', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换智图暖色矢量图
 * @param showAnnotion 是否显示标注
 */
function showGeoqWarmVector(showAnnotion) {
    _resetCRS(false);

    showAnnotion = typeof (showAnnotion) == 'undefined' ? true : showAnnotion;

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('Geoq.Vector.WarmOnlySymbol', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
    if (showAnnotion) {
        L.tileLayer.layerProvider('Geoq.Vector.WarmRefrence', {
            attribution: MAP_BASE_LAYER
        }).addTo(map);
    }
}

/**
 * 切换智图灰色矢量图
 * @param showAnnotion 是否显示标注
 */
function showGeoqGrayVector(showAnnotion) {
    _resetCRS(false);

    showAnnotion = typeof (showAnnotion) == 'undefined' ? true : showAnnotion;

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('Geoq.Vector.GrayOnlySymbol', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
    if (showAnnotion) {
        L.tileLayer.layerProvider('Geoq.Vector.GrayRefrence', {
            attribution: MAP_BASE_LAYER
        }).addTo(map);
    }
}

/**
 * 切换智图蓝黑矢量图
 * @param showAnnotion 是否显示标注
 */
function showGeoqPurplishBlueVector(showAnnotion) {
    _resetCRS(false);

    showAnnotion = typeof (showAnnotion) == 'undefined' ? true : showAnnotion;

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('Geoq.Vector.PurplishBlueOnlySymbol', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
    if (showAnnotion) {
        L.tileLayer.layerProvider('Geoq.Vector.PurplishBlueRefrence', {
            attribution: MAP_BASE_LAYER
        }).addTo(map);
    }
}

/**
 * 切换智图蓝黑矢量图
 */
function showGeoqEnglishVector() {
    _resetCRS(false);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('Geoq.Vector.English', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换智图中国行政边界矢量图
 */
function showGeoqCNBoundaryLineVector() {
    _resetCRS(false);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('Geoq.Vector.CNBoundaryLine', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换智图水系图层矢量图
 */
function showGeoqWorldHydroVector() {
    _resetCRS(false);

    removeLayer(MAP_BASE_LAYER);
    L.tileLayer.layerProvider('Geoq.Vector.WorldHydro', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换腾讯地图矢量图
 */
function showTencentVector() {
    _resetCRS(false);

    removeLayer(MAP_BASE_LAYER);
    // L.tileLayer.layerProvider('Tencent.Vector.Normal', {
    //     attribution: MAP_BASE_LAYER
    // }).addTo(map);
    L.tileLayer.tencentProvider('Tencent.Vector.Normal', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
}

/**
 * 切换腾讯地图卫星图
 */
function showTencentSatellite(showAnnotion) {
    _resetCRS(false);

    showAnnotion = typeof (showAnnotion) == 'undefined' ? true : showAnnotion;

    removeLayer(MAP_BASE_LAYER);
    // L.tileLayer.layerProvider('Tencent.Satellite.Normal', {
    //     attribution: MAP_BASE_LAYER
    // }).addTo(map);
    // if (showAnnotion) {
    //     L.tileLayer.layerProvider('Tencent.Annotion.Normal', {
    //         attribution: MAP_BASE_LAYER
    //     }).addTo(map);
    // }
    L.tileLayer.tencentProvider('Tencent.Satellite.Normal', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
    if (showAnnotion) {
        L.tileLayer.tencentProvider('Tencent.Annotion.Normal', {
            attribution: MAP_BASE_LAYER
        }).addTo(map);
    }
}

/**
 * 切换腾讯地图地形图
 */
function showTencentTerrain(showAnnotion) {
    _resetCRS(false);

    showAnnotion = typeof (showAnnotion) == 'undefined' ? true : showAnnotion;

    removeLayer(MAP_BASE_LAYER);
    // L.tileLayer.layerProvider('Tencent.Terrain.Normal', {
    //     attribution: MAP_BASE_LAYER
    // }).addTo(map);
    // if (showAnnotion) {
    //     L.tileLayer.layerProvider('Tencent.Annotion.Normal', {
    //         attribution: MAP_BASE_LAYER
    //     }).addTo(map);
    // }
    L.tileLayer.tencentProvider('Tencent.Terrain.Normal', {
        attribution: MAP_BASE_LAYER
    }).addTo(map);
    if (showAnnotion) {
        L.tileLayer.tencentProvider('Tencent.Annotion.Normal', {
            attribution: MAP_BASE_LAYER
        }).addTo(map);
    }
}

/**
 * 重新设置参考坐标系
 * @param isBaiduCRS 是否为百度坐标，默认false
 * @private
 */
function _resetCRS(isBaiduCRS) {
    isBaiduCRS = typeof (isBaiduCRS) == 'undefined' ? false : isBaiduCRS;

    var center = map.getCenter();
    if (isBaiduCRS) {
        map.options.crs = L.CRS.Baidu;
    } else {
        map.options.crs = L.CRS.EPSG3857;
    }
    map.setView(center);
    map._resetView(map.getCenter(), map.getZoom());
}

function testMarker() {
    //设置参照物
    L.marker([39.905530, 116.391305]).addTo(map).bindPopup('<p>我是天安门标记位置</p>').openPopup();
}
