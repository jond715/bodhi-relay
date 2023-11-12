package com.leafbodhi.nostr.db.service;

import com.leafbodhi.nostr.db.model.InvoiceModel;

/**
 * @author jond
 */
public interface IInvoiceService {

    Integer save(InvoiceModel model);

    /**
     * 通过 主键id 删除
     * @param id 
     * @return 成功标志，新增成功返回值为 1
     */
    Integer removeById(Integer id);

    /**
     * 通过主键 id 查询一条记录
     * @param id
     * @return event信息
     */
    InvoiceModel getById(Integer id);

    /**
     * @return 成功标志，新增成功返回值为 1
     */
    Integer updateById(InvoiceModel model);

    InvoiceModel getByHash(String paymenHash);


    InvoiceModel getByPubkey(String pubkey);
}
