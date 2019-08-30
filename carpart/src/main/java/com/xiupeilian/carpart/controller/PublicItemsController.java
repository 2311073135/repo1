package com.xiupeilian.carpart.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.Brand;
import com.xiupeilian.carpart.model.Items;
import com.xiupeilian.carpart.model.Parts;
import com.xiupeilian.carpart.service.BrandService;
import com.xiupeilian.carpart.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/public")
public class PublicItemsController {
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private BrandService brandService;
    @RequestMapping("/publicItems")
    public String publicItems(Items items, Integer pageNo, Integer pageSize,
                              HttpServletRequest request,String brandName){
        pageNo=pageNo==null?1:pageNo;
        pageSize=pageSize==null?8:pageSize;
        PageHelper.startPage(pageNo,pageSize);
        List<Items> itemsList = itemsService.findItemsByQueryVo(items);
        PageInfo<Items> page = new PageInfo<>(itemsList);
        //��ҳ���ݴ�request
        request.setAttribute("page",page);
        //������������
        request.setAttribute("items",items);
        //ҳ�浱��һЩ������Ҫ��ʼ����Ʒ�ơ�������
        List<Brand> brandList = brandService.findBrandAll();
        List<Parts> partsList = brandService.findPartsAll();
        //Ʒ���б�������
        request.setAttribute("brandList",brandList);
        request.setAttribute("partsList",partsList);
        request.setAttribute("brandName",brandName);
        return "public/publicItems";
    }

}
