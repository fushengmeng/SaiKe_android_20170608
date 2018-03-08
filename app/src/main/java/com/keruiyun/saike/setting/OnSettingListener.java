package com.keruiyun.saike.setting;

/**
 * Created by Administrator on 2018/1/18.
 */

public interface OnSettingListener {

    /**
     * @param adminLev 管理员等级
     * */
    public void onSettingVaild(int adminLev);

    /**
     * 是否判断有效期
     * @param isAuth 是否验证有效期
     *
     * @return  是否已过有效期
     * */
    public boolean onAuthVaild(boolean isAuth);
}
