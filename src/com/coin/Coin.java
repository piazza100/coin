package com.coin;

import java.math.BigDecimal;

public class Coin implements Comparable<Coin> {
	private String name = "";
	private String code = "";
	private String candleDateTimeKst = "";
	private BigDecimal curPrice = null;
	private BigDecimal curVolume = null;
	private BigDecimal prePrice = null;
	private BigDecimal preVolume = null;
	private BigDecimal totalVolume = null;
	private BigDecimal volumePercent = null;
	private int priceCompareTo = 0;
	private int volumeCompareTo = 0;

	public int compareTo(Coin coin) {
		return totalVolume.compareTo(coin.getTotalVolume());
	}

	public Coin(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCandleDateTimeKst() {
		return candleDateTimeKst;
	}

	public void setCandleDateTimeKst(String candleDateTimeKst) {
		this.candleDateTimeKst = candleDateTimeKst;
	}

	public BigDecimal getCurPrice() {
		return curPrice;
	}

	public void setCurPrice(BigDecimal curPrice) {
		this.curPrice = curPrice;
	}

	public BigDecimal getCurVolume() {
		return curVolume;
	}

	public void setCurVolume(BigDecimal curVolume) {
		this.curVolume = curVolume;
	}

	public BigDecimal getPrePrice() {
		return prePrice;
	}

	public void setPrePrice(BigDecimal prePrice) {
		this.prePrice = prePrice;
	}

	public BigDecimal getPreVolume() {
		return preVolume;
	}

	// 같으면 0, 크면 1, 작으면 -1
	public void setPreVolume(BigDecimal preVolume) {
		this.preVolume = preVolume;
	}

	public int getPriceCompareTo() {
		return priceCompareTo;
	}

	// 같으면 0, 크면 1, 작으면 -1
	public void setPriceCompareTo(int priceCompareTo) {
		this.priceCompareTo = priceCompareTo;
	}

	public int getVolumeCompareTo() {
		return volumeCompareTo;
	}

	public void setVolumeCompareTo(int volumeCompareTo) {
		this.volumeCompareTo = volumeCompareTo;
	}

	public BigDecimal getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(BigDecimal totalVolume) {
		this.totalVolume = totalVolume;
	}

	public BigDecimal getVolumePercent() {
		return volumePercent;
	}

	public void setVolumePercent(BigDecimal volumePercent) {
		this.volumePercent = volumePercent;
	}

	@Override
	public String toString() {
		return "Coin [name=" + name + ", code=" + code + ", candleDateTimeKst=" + candleDateTimeKst + ", curPrice="
				+ curPrice + ", curVolume=" + curVolume + ", prePrice=" + prePrice + ", preVolume=" + preVolume
				+ ", totalVolume=" + totalVolume + ", volumePercent=" + volumePercent + ", priceCompareTo="
				+ priceCompareTo + ", volumeCompareTo=" + volumeCompareTo + "]";
	}

}
