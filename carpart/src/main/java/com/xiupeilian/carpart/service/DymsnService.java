package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Dymsn;
import com.xiupeilian.carpart.model.News;
import com.xiupeilian.carpart.model.Notice;

import java.util.List;

/**
 * @Description: 动态消息
 * @Author: Tu Xu
 * @CreateDate: 2019/8/21 15:59
 * @Version: 1.0
 **/
public interface DymsnService {
    public List<Dymsn> findDymsns();

    List<Notice> findNotice();
    List<News> findNews();
}
