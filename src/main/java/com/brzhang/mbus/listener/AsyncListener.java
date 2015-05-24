package com.brzhang.mbus.listener;

import com.brzhang.mbus.reponse.BaseResponse;

/**
 * Created by brzhang on 15/5/21.
 */
public interface AsyncListener {
    void onAsyncCallback(BaseResponse baseResponse);
}
