let KXType = {
    KX_1MIN: 0x100, //01分钟数据
    KX_5MIN: 0x101, //05分钟数据
    KX_15MIN: 0x103, //15分钟数据
    KX_30MIN: 0x106, //30分钟数据
    KX_60MIN: 0x10C, //60分钟数据

    KX_DAY: 0x201, //日K线数据
    KX_WEEK: 0x301, //周K线数据

    KX_MONTH: 0x401, //月K线数据
    KX_03MNT: 0x403, //季K线数据
    KX_06MNT: 0x406, //半年K线数据
    KX_12MNT: 0x40C //年K线数据
}

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

export { KXType, GetQueryString }