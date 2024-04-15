package com.example.dianyinggoupiao_springboot.util;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ALiPayUtil {

    @Value("${alipay.url}")
    private String url;
    @Value("${alipay.appid}")
    private String appid;
    @Value("${alipay.privateKey}")
    private String privateKey;
    @Value("${alipay.publicKey}")
    private String publicKey;
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;
    @Value("${alipay.returnUrl}")
    private String returnUrl;

    /**
     * 支付宝支付
     * @param id 商户订单号
     * @param price 支付金额
     * @param title 订单标题
     * @return
     */
    public String pay(String id, String price, String title) {
        AlipayClient alipayClient = new DefaultAlipayClient(url, appid, privateKey, "json", "UTF-8", publicKey, "RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl(notifyUrl);
        //同步跳转地址，仅支持http/https
        request.setReturnUrl(returnUrl);
        /******必传参数******/
        JSONObject bizContent = new JSONObject();
        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", id);
        //支付金额，最小值0.01元
        bizContent.put("total_amount", price);
        //订单标题，不可使用特殊符号
        bizContent.put("subject", title);
        //电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

        /******可选参数******/
        //bizContent.put("time_expire", "2022-08-01 22:00:00");

        //// 商品明细信息，按需传入
        //JSONArray goodsDetail = new JSONArray();
        //JSONObject goods1 = new JSONObject();
        //goods1.put("goods_id", "goodsNo1");
        //goods1.put("goods_name", "子商品1");
        //goods1.put("quantity", 1);
        //goods1.put("price", 0.01);
        //goodsDetail.add(goods1);
        //bizContent.put("goods_detail", goodsDetail);

        //// 扩展信息，按需传入
        //JSONObject extendParams = new JSONObject();
        //extendParams.put("sys_service_provider_id", "2088511833207846");
        //bizContent.put("extend_params", extendParams);

        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = null;
        String form = null;
        try {
            response = alipayClient.pageExecute(request);
            form = response.getBody();
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return form;
    }

    /**
     * 提供所有支付宝支付订单的查询
     * @param out_trade_no 创建交易传入的商户订单号
     * @param trade_no 支付宝交易号
     * @return 查询的数据
     */
    public String query(String out_trade_no,String trade_no){
        AlipayClient alipayClient = new DefaultAlipayClient(url, appid, privateKey, "json", "UTF-8", publicKey, "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "  \"out_trade_no\":\""+out_trade_no+"\"," +
                "  \"trade_no\":\""+trade_no+"\"," +
                "  \"query_options\":[" +
                "    \"trade_settle_info\"" +
                "  ]" +
                "}");
        AlipayTradeQueryResponse response = null;
        String body=null;
        try {
            response = alipayClient.execute(request);
            body=response.getBody();
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return body;

    }

    /**
     * 用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭。
     * @param out_trade_no 创建交易传入的商户订单号
     * @param trade_no 支付宝交易号
     * @return
     */
    public String noPay(String out_trade_no,String trade_no){
        AlipayClient alipayClient = new DefaultAlipayClient(url, appid, privateKey, "json", "UTF-8", publicKey, "RSA2");
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put(out_trade_no,trade_no );
        request.setBizContent(bizContent.toString());
        AlipayTradeCloseResponse response = null;
        String str=null;
        try {
            response = alipayClient.execute(request);
            str=response.getBody();
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return str;
    }


}
