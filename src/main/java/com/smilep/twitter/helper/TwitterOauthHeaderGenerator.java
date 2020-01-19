package com.smilep.twitter.helper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

/**
 * Class to generate Oauth 1.0a header for Twitter
 *
 */
@Slf4j
public class TwitterOauthHeaderGenerator {

    private String consumerKey;
    private String consumerSecret;
    private String signatureMethod;
    private String token;
    private String tokenSecret;
    private String version;

    public TwitterOauthHeaderGenerator(String consumerKey, String consumerSecret, String token, String tokenSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.token = token;
        this.tokenSecret = tokenSecret;
        this.signatureMethod = "HMAC-SHA1";
        this.version = "1.0";
    }

    private static final String oauth_consumer_key = "oauth_consumer_key";
    private static final String oauth_token = "oauth_token";
    private static final String oauth_signature_method = "oauth_signature_method";
    private static final String oauth_timestamp = "oauth_timestamp";
    private static final String oauth_nonce = "oauth_nonce";
    private static final String oauth_version = "oauth_version";
    private static final String oauth_signature = "oauth_signature";
    private static final String HMAC_SHA1 = "HmacSHA1";

    /**
     * Generates oAuth 1.0a header which can be pass as Authorization header
     * 
     * @param httpMethod
     * @param url
     * @param requestParams
     * @return
     */
    public String generateHeader(String httpMethod, String url, Map<String, String> requestParams) {
        StringBuilder base = new StringBuilder();
        String nonce = getNonce();
        String timestamp = getTimestamp();
        String baseSignatureString = generateSignatureBaseString(httpMethod, url, requestParams, nonce, timestamp);
        String signature = encryptUsingHmacSHA1(baseSignatureString);
        base.append("OAuth ");
        append(base, oauth_consumer_key, consumerKey);
        append(base, oauth_token, token);
        append(base, oauth_signature_method, signatureMethod);
        append(base, oauth_timestamp, timestamp);
        append(base, oauth_nonce, nonce);
        append(base, oauth_version, version);
        append(base, oauth_signature, signature);
        base.deleteCharAt(base.length() - 1);
        log.info("header : " + base.toString());
        return base.toString();
    }

    /**
     * Generate base string to generate the oauth_signature
     * 
     * @param httpMethod
     * @param url
     * @param requestParams
     * @return
     */
    private String generateSignatureBaseString(String httpMethod, String url, Map<String, String> requestParams, String nonce, String timestamp) {
        Map<String, String> params = new HashMap<>();
        requestParams.entrySet().forEach(entry -> {
            put(params, entry.getKey(), entry.getValue());
        });
        put(params, oauth_consumer_key, consumerKey);
        put(params, oauth_nonce, nonce);
        put(params, oauth_signature_method, signatureMethod);
        put(params, oauth_timestamp, timestamp);
        put(params, oauth_token, token);
        put(params, oauth_version, version);
        Map<String, String> sortedParams = params.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        StringBuilder base = new StringBuilder();
        sortedParams.entrySet().forEach(entry -> {
            base.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        });
        base.deleteCharAt(base.length() - 1);
        String baseString = httpMethod.toUpperCase() + "&" + encode(url) + "&" + encode(base.toString());
        return baseString;
    }

    private String encryptUsingHmacSHA1(String input) {
        String secret = new StringBuilder().append(encode(consumerSecret)).append("&").append(encode(tokenSecret)).toString();
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        SecretKey key = new SecretKeySpec(keyBytes, HMAC_SHA1);
        Mac mac;
        try {
            mac = Mac.getInstance(HMAC_SHA1);
            mac.init(key);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
        byte[] signatureBytes = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(signatureBytes));
    }

    /**
     * Percentage encode String as per RFC 3986, Section 2.1
     * 
     * @param value
     * @return
     */
    private String encode(String value) {
        String encoded = "";
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sb = "";
        char focus;
        for (int i = 0; i < encoded.length(); i++) {
            focus = encoded.charAt(i);
            if (focus == '*') {
                sb += "%2A";
            } else if (focus == '+') {
                sb += "%20";
            } else if (focus == '%' && i + 1 < encoded.length() && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
                sb += '~';
                i += 2;
            } else {
                sb += focus;
            }
        }
        return sb.toString();
    }

    private void put(Map<String, String> map, String key, String value) {
        map.put(encode(key), encode(value));
    }

    private void append(StringBuilder builder, String key, String value) {
        builder.append(encode(key)).append("=\"").append(encode(value)).append("\",");
    }

    private String getNonce() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        return generatedString;

    }

    private String getTimestamp() {
        return Math.round((new Date()).getTime() / 1000.0) + "";
    }

}
