package com.tencent.hoollyzhang.mbus.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: Campus<br>
 * Description: <br>
 * Create DateTime: 2015/1/22 19:23 <br>
 * SVN last modify person: hoollyzhang <br>
 * SVN last modify DateTime: 2015/1/22 时间需+1小时 <br>
 * SVN last version: V1.0.0 <br>
 *
 * @author hoollyzhang
 */
public class Const {

    public static final int CITY_ID_OF_SHENZHEN = 860515;
    public static final String GET_ALL_LINE = "http://busapi.gpsoo.net/v1/bus/get_lines_by_city?type=handset&city_id=";
    public static final String GET_LINE_INFO = "http://busapi.gpsoo.net/v1/bus/mbcommonservice?method=getlineinfo&sublineID=%d&mapType=BAIDU_MAP&posmaptype=BAIDU";
    public static final String GET_COMING_BUS = "http://busapi.gpsoo.net/v1/bus/mbcommonservice?method=getnearcarinfo&ids=&sublineid=%A&stationId=%B&mapType=BAIDU";

}
