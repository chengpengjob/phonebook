package com.cp.controller;

import com.cp.entity.PhoneBook;
import com.cp.entity.R;
import com.cp.service.PhoneBookService;
import com.cp.util.PinYinUtil;
import com.cp.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author feipeng
 * @site www.gcp168.cn
 * @create 2019-09-21 17:05
 */

@RestController
@RequestMapping("/phoneBook")
public class PhoneBookController {

    @Autowired
    private PhoneBookService phoneBookService;
    /**
     * 查询所有电话簿信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/loadAll")
    public R loadAll()throws Exception{
        Map<String,Object> map = new LinkedHashMap<>();
        char []letters={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','#'};
        for(int i=0;i<letters.length;i++){
            String letter = String.valueOf(letters[i]);
            List<PhoneBook> phoneBooks = phoneBookService.loadByInitial(letter);
            if(phoneBooks.size()>0){
                map.put(letter,phoneBooks);
            }
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("data",map);

        return R.ok(resultMap);
    }

    @RequestMapping("/save")
    public R save(@RequestBody  PhoneBook phoneBook)throws Exception{
        int resultTotal=0;
        String initial = String.valueOf(PinYinUtil.getPinYin(phoneBook.getName()).charAt(0)).toUpperCase();
        if(StringUtil.isAlpha(initial)){
            phoneBook.setInitial(initial);
        }else{
            phoneBook.setInitial("#");
        }
        if(phoneBook.getId()==null){
            resultTotal=phoneBookService.add(phoneBook);
        }else{
            resultTotal=phoneBookService.update(phoneBook);
        }if(resultTotal>0){
            return R.ok();
        }else{
            return R.error(-1,"保存失败，请联系管理员");
        }

    }

    /**
     * 根据id查询电话簿信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/findById")
    public R findById(Integer id)throws Exception{
        PhoneBook phoneBook=phoneBookService.findById(id);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("phoneBook",phoneBook);
        return R.ok(resultMap);
    }

    /**
     * 根据id删除记录
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public R delete(Integer id)throws Exception{
        int resultTotal=phoneBookService.delete(id);
        if(resultTotal>0){
            return R.ok();
        }else{
            return R.error(-1,"删除失败，请联系管理员");
        }
    }
}
