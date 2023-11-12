package com.leafbodhi.nostr.db.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "invoice_payment")
public class InvoiceModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public InvoiceModel() {
	}

	public InvoiceModel(String pubkey, String paymentHash, String bolt11, Long amount, String status, String memo, Long confirmedAt, Long expiredAt, Long createdAt, Long createdTime) {
		this.pubkey = pubkey;
		this.paymentHash = paymentHash;
		this.bolt11 = bolt11;
		this.amount = amount;
		this.status = status;
		this.memo = memo;
		this.confirmedAt = confirmedAt;
		this.expiredAt = expiredAt;
		this.createdAt = createdAt;
		this.createdTime = createdTime;
	}

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 32-bytes hex-encoded public key of the event creator
	 */
	@TableField("pubkey")
	private String pubkey;

	/**
	 * payment hash
	 */
	@JsonProperty("payment_hash")
	@TableField("payment_hash")
	private String paymentHash;

	@TableField("bolt11")
	private String bolt11;

	@TableField("amount")
	private Long amount;

	@TableField("status")
	private String status;

	@TableField("memo")
	private String memo;

	/**
	 * unix timestamp in seconds
	 */
	@JsonProperty("confirmed_at")
	@TableField("confirmed_at")
	private Long confirmedAt;
	/**
	 * unix timestamp in seconds
	 */
	@JsonProperty("expired_at")
	@TableField("expired_at")
	private Long expiredAt;
	/**
	 * unix timestamp in seconds
	 */
	@JsonProperty("created_at")
	@TableField("created_at")
	private Long createdAt;

	/**
	 * unix timestamp in seconds
	 */
	@JsonProperty("created_time")
	@TableField("created_time")
	private Long createdTime;

}
