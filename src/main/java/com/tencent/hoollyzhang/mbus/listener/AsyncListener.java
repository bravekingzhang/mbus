package com.tencent.hoollyzhang.mbus.listener;

import com.tencent.hoollyzhang.mbus.reponse.BaseResponse;

/**
 * Created by brzhang on 15/5/21.
 */
public interface AsyncListener {
    void onAsyncCallback(BaseResponse baseResponse);
}
