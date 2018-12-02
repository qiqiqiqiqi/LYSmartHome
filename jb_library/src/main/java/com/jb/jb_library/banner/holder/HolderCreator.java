package com.jb.jb_library.banner.holder;

/**
 * Created by zhouwei on 17/5/26.
 */

public interface HolderCreator<VH extends ViewHolder> {
    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();
}
