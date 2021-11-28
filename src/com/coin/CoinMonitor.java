package com.coin;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class CoinMonitor {
	private static List<Coin> coinList = new ArrayList<Coin>();
	private int minutes = 3;
	static {

		coinList.add(new Coin("�̿���", "EOS"));
		coinList.add(new Coin("��", "GNT"));
		coinList.add(new Coin("��Ʈ����", "BTC"));
		coinList.add(new Coin("���̴�", "ADA"));
		coinList.add(new Coin("��Ʈ����ĳ��", "BCC"));
		coinList.add(new Coin("Ʈ��", "TRX"));
		coinList.add(new Coin("�������", "ONT"));
		coinList.add(new Coin("����", "XRP"));
		coinList.add(new Coin("�̱״Ͻ�", "IGNIS"));
		coinList.add(new Coin("�þ�����", "SC"));
		coinList.add(new Coin("�ƴ�", "ARDR"));
		coinList.add(new Coin("�̴�����", "ETH"));

		coinList.add(new Coin("������", "GTO"));
		coinList.add(new Coin("����", "STORM"));
		coinList.add(new Coin("�׷ν�������", "GRS"));
		coinList.add(new Coin("����", "QTUM"));
		coinList.add(new Coin("�׿�", "NEO"));
		coinList.add(new Coin("�������ͽ���Ʈ��ũ��ū", "SNT"));
		coinList.add(new Coin("���ڶ���", "XLM"));
		coinList.add(new Coin("�̴�����Ŭ����", "ETC"));
		coinList.add(new Coin("��Ʈ���ΰ��", "BTG"));
		coinList.add(new Coin("�����޷�", "SBD"));
		coinList.add(new Coin("������", "ICX"));
		coinList.add(new Coin("���ƽ��", "TIX"));
		coinList.add(new Coin("����", "STEEM"));

		coinList.add(new Coin("����ũ", "LSK"));
		coinList.add(new Coin("��ť��", "MER"));
		coinList.add(new Coin("��׷�", "XMR"));
		coinList.add(new Coin("�����ڳ�̹����Ʈ", "XEM"));
		coinList.add(new Coin("���ν�Ÿ�̴�", "EMC2"));
		coinList.add(new Coin("���̼���", "OMG"));
		coinList.add(new Coin("����Ʈ����", "LTC"));
		coinList.add(new Coin("���̺�", "WAVES"));
		coinList.add(new Coin("��Ʈ��Ƽ��", "STRAT"));
		coinList.add(new Coin("����", "MCO"));
		coinList.add(new Coin("�Ŀ�����", "POWR"));
		coinList.add(new Coin("��Ż", "MTL"));

		coinList.add(new Coin("�ڸ�", "KMD"));
		coinList.add(new Coin("�Ǻ���", "PIVX"));
		coinList.add(new Coin("���丮��", "STORJ"));
		coinList.add(new Coin("���", "REP"));
		coinList.add(new Coin("��Ʈ����", "VTC"));
		coinList.add(new Coin("���", "DASH"));
		coinList.add(new Coin("��ĳ��", "ZEC"));
		coinList.add(new Coin("��ũ", "ARK"));

	}

	public void run() {

		try {
			for (Coin coin : this.coinList) {
				this.checkCoinTotalVolume(coin);
			}
			Collections.sort(this.coinList);
//			for (int i = 0; i < coinList.size() / 2; i++) {
//				this.coinList.remove(0);
//			}

			while (true) {
				for (Coin coin : this.coinList) {
					this.checkCoinInfo(coin);
				}
				Thread.sleep(60000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkCoinTotalVolume(Coin coin) throws ClientProtocolException, IOException {
		int count = 1;

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			String url = "https://crix-api-endpoint.upbit.com/v1/crix/candles/days?code=CRIX.UPBIT.KRW-" + coin.getCode() + "&count=" + count + "&to=" + "";

			HttpGet httpGet = new HttpGet(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String jsonRes = httpClient.execute(httpGet, responseHandler);
			// System.out.println("jsonRes = " + jsonRes);

			this.setCoinTotalVolume(coin, jsonRes);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (Exception ex) {
			}
		}
	}

	private void checkCoinInfo(Coin coin) throws ClientProtocolException, IOException {
		int count = 2;

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			// minutes : 1, 3, 5, 10, 15, 30, 60, 240
			String url = "https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/" + minutes + "?code=CRIX.UPBIT.KRW-" + coin.getCode() + "&count=" + count + "&to=" + "";

			HttpGet httpGet = new HttpGet(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String jsonRes = httpClient.execute(httpGet, responseHandler);
			// System.out.println("jsonRes = " + jsonRes);

			this.setCoinInfo(coin, jsonRes);
			this.checkSendMsg(coin);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (Exception ex) {
			}
		}
	}

	private void setCoinTotalVolume(Coin coin, String jsonRes) {
		org.json.JSONArray array = new org.json.JSONArray(jsonRes);
		for (int i = 0; i < array.length(); i++) {
			if (i == 0) {
				org.json.JSONObject obj = (org.json.JSONObject) array.get(i);
				String candleAccTradeVolume = obj.get("candleAccTradeVolume").toString();
				coin.setTotalVolume(new BigDecimal(candleAccTradeVolume));
			}
		}
	}

	private void setCoinInfo(Coin coin, String jsonRes) {
		org.json.JSONArray array = new org.json.JSONArray(jsonRes);

		BigDecimal curPrice = null;
		BigDecimal curVolume = null;
		BigDecimal prePrice = null;
		BigDecimal preVolume = null;

		for (int i = 0; i < array.length(); i++) {
			if (i == 0) {
				org.json.JSONObject obj = (org.json.JSONObject) array.get(i);
				String candleDateTimeKst = obj.get("candleDateTimeKst").toString();
				String tradePrice = obj.get("tradePrice").toString();
				String candleAccTradeVolume = obj.get("candleAccTradeVolume").toString();
				curPrice = new BigDecimal(tradePrice);
				curVolume = new BigDecimal(candleAccTradeVolume);
				coin.setCandleDateTimeKst(candleDateTimeKst);
				coin.setCurPrice(curPrice);
				coin.setCurVolume(curVolume);
			} else {
				org.json.JSONObject obj = (org.json.JSONObject) array.get(i);
				String tradePrice = obj.get("tradePrice").toString();
				String candleAccTradeVolume = obj.get("candleAccTradeVolume").toString();
				prePrice = new BigDecimal(tradePrice);
				preVolume = new BigDecimal(candleAccTradeVolume);
				coin.setPrePrice(prePrice);
				coin.setPreVolume(preVolume);
			}
		}
	}

	private void checkSendMsg(Coin coin) {
		BigDecimal multiplyVolume5 = coin.getPreVolume().multiply(new BigDecimal(5));
		BigDecimal multiplyVolume9 = coin.getPreVolume().multiply(new BigDecimal(9));
		BigDecimal multiplyVolume10 = coin.getPreVolume().multiply(new BigDecimal(10));
		coin.setPriceCompareTo(coin.getCurPrice().compareTo(coin.getPrePrice()));
		coin.setVolumeCompareTo(coin.getCurVolume().compareTo(multiplyVolume9));

		// System.out.println(coin.toString());
		BigDecimal volumePercent = null;
		try {
			volumePercent = coin.getCurVolume().divide(coin.getTotalVolume(), BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
			coin.setVolumePercent(volumePercent);
		} catch (Exception e) {
		}

//		 System.out.println(coin.toString());
		if (coin.getCurPrice().intValue() < 100000 && coin.getPriceCompareTo() > -1 && coin.getVolumeCompareTo() > -1 && coin.getVolumePercent().doubleValue() >= 3) {
//			System.out.println("ok, " + coin.toString());
			this.sendMsg(coin.toString());
		}
	}

	private void sendMsg(String msg) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			String url = "https://api.telegram.org/bot388664840:AAGrB_eTwqJnnFicaLGwYGu3_dagfCGzQnM/sendMessage";

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("chat_id", "41039887"));
			nvps.add(new BasicNameValuePair("text", msg));

			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("User-Agent", "Mozilla/5.0");
			httpPost.setHeader("content-type", "application/x-www-form-urlencoded");
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
//			HttpClient client = WebClientWrapper.wrapClient(httpClient);
//			String res = client.execute(httpPost, responseHandler);
			String res = httpClient.execute(httpPost, responseHandler);
			// System.out.println("res = " + res);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (Exception ex) {
			}
		}
	}

	public static void main(String[] args) {
		CoinMonitor cm = new CoinMonitor();
		cm.run();
	}
}
