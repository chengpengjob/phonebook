package com.cp.service.impl;

import com.cp.entity.PhoneBook;
import com.cp.mapper.PhoneBookMapper;
import com.cp.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feipeng
 * @site www.gcp168.cn
 * @create 2019-09-22 14:37
 */
@Service("phoneBookService")
public class PhoneBookServiceImpl implements PhoneBookService {

    @Autowired
    private PhoneBookMapper phoneBookMapper;

    @Override
    public List<PhoneBook> loadByInitial(String initial) {
        return phoneBookMapper.loadByInitial(initial);
    }

    @Override
    public Integer add(PhoneBook phoneBook) {
        return phoneBookMapper.add(phoneBook);
    }

    @Override
    public Integer update(PhoneBook phoneBook) {
        return phoneBookMapper.update(phoneBook);
    }

    @Override
    public PhoneBook findById(Integer id) {
        return phoneBookMapper.findById(id);
    }

    @Override
    public Integer delete(Integer id) {
        return phoneBookMapper.delete(id);
    }
}
