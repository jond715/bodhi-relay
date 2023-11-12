package com.leafbodhi.nostr.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leafbodhi.nostr.db.mapper.InvoiceMapper;
import com.leafbodhi.nostr.db.model.InvoiceModel;
import com.leafbodhi.nostr.db.service.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jond
 */
@Component
public class InvoiceServiceImpl implements IInvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Override
    public Integer save(InvoiceModel model) {
        return invoiceMapper.insert(model);
    }

    @Override
    public Integer removeById(Integer id) {
        return invoiceMapper.deleteById(id);
    }

    @Override
    public InvoiceModel getById(Integer id) {
        return invoiceMapper.selectById(id);
    }

    @Override
    public Integer updateById(InvoiceModel model) {
        return invoiceMapper.updateById(model);
    }

    @Override
    public InvoiceModel getByHash(String paymentHash) {
        var queryWrapper = new QueryWrapper<InvoiceModel>();
        queryWrapper.eq("payment_hash",paymentHash);
        return invoiceMapper.selectOne(queryWrapper);
    }

    @Override
    public InvoiceModel getByPubkey(String pubkey) {
        var queryWrapper = new QueryWrapper<InvoiceModel>();
        queryWrapper.eq("pubkey",pubkey);
        queryWrapper.last("order by id desc limit 1");
        return invoiceMapper.selectOne(queryWrapper);
    }
}
