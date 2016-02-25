package com.ceshidemo;

/**
 * Created by
 */

public interface NetCallBack<T> {

    void successNet(T t);

    void errorNet(String errorMsg, int errorCode);
}
