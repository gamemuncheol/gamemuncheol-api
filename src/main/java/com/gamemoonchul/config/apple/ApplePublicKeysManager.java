package com.gamemoonchul.config.apple;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.config.apple.entities.AppleJWKSet;
import com.gamemoonchul.config.apple.entities.ApplePublicKey;
import com.gamemoonchul.config.apple.enums.AppleTokenStatus;
import com.gamemoonchul.config.apple.httpclient.AppleHttpClient;
import com.google.gson.Gson;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/************************************************************************
 Copyright 2020 eBay Inc.
 Author/Developer(s): Chetan Hibare; Dhairyasheel Desai; Swanand Abhyankar

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 https://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 **************************************************************************/

public class ApplePublicKeysManager {

    /** Number of milliseconds before expiration time to force a refresh. */
    private long refreshSkewMilliseconds = 86400000;

    /** Singleton object. */
    private static final ApplePublicKeysManager applePublicKeysManager = new ApplePublicKeysManager();

    private List<PublicKey> applePublicKeys;

    /** Expiration time in milliseconds to refresh fetching of public key */
    private long expirationTimeInMillis;

    /** Indicates whether Proxy is enabled or not. */
    private boolean isProxyEnabled = false;

    /** Proxy host to be set by consumer, if needed. */
    private String proxyHost;

    /** Proxy port to be set by consumer, if needed. */
    private int proxyPort;

    /** Lock on the public keys. */
    private final Lock lock = new ReentrantLock();

    private AppleHttpClient appleHttpClient = AppleHttpClient.getAppleHttpClient();

    /** Singleton constructor. */
    public static ApplePublicKeysManager getApplePublicKeysManager() {
        return applePublicKeysManager;
    }

    private ApplePublicKeysManager() {}

    /** Returns whether proxy is enabled or not. */
    public boolean isProxyEnabled() {
        return isProxyEnabled;
    }

    
    public void setProxyEnabled(boolean isProxyEnabled) {
		this.isProxyEnabled = isProxyEnabled;
	}

	/** Returns whether proxy host. */
    public String getProxyHost() {
        return proxyHost;
    }


    public ApplePublicKeysManager setProxyHost(String proxyHost)  {
        if(!StringUtils.isEmpty(proxyHost)) {
            this.proxyHost = proxyHost;
            this.isProxyEnabled = true;
            return this;
        } else {
            throw new ApiException(AppleTokenStatus.PROXY_SETUP_ERROR);
        }

    }

    public int getProxyPort() {
        return proxyPort;
    }

    /**
     * Set the proxy port used while connecting to Apple API for fetching public keys
     * @param proxyPort proxy host in int
     * @return ApplePublicKeysManager object after setting proxy port
     * @throws ApiException
     */
    public ApplePublicKeysManager setProxyPort(int proxyPort) {
        if(proxyPort > 0) {
            this.proxyPort = proxyPort;
            this.isProxyEnabled = true;
            return this;
        } else {
            throw new ApiException(AppleTokenStatus.PROXY_SETUP_ERROR);
        }

    }

    public long getRefreshSkewMilliseconds() {
        return refreshSkewMilliseconds;
    }

    public ApplePublicKeysManager setRefreshSkewMilliseconds(long refreshSkewMilliseconds) {
        this.refreshSkewMilliseconds = refreshSkewMilliseconds;
        return this;
    }

    /**
     * Function that returns list of Apple PublicKey objects.
     * Existing initialized keys would be returned if 
     *
     * @return
     * @throws ApiException
     */
    public final List<PublicKey> getApplePublicKeys() {
        lock.lock();
        try {
            if(applePublicKeys == null || System.currentTimeMillis() + refreshSkewMilliseconds > expirationTimeInMillis) {
                refreshApplePublicKeys();
            }
            return applePublicKeys;
        } finally {
            lock.unlock();
        }
    }

    public ApplePublicKeysManager refreshApplePublicKeys() {
        lock.lock();
        try {

            applePublicKeys = new ArrayList<PublicKey>();
            AppleJWKSet keys = fetchRawPublicKeys();

            if(keys == null || CollectionUtils.isEmpty(keys.getKeys())){
                throw new ApiException(AppleTokenStatus.APPLE_PUBLIC_KEY_UNAVAILABLE);
            }

            String modulus, exponent;
            BigInteger modulusAsBigInt, exponentAsBigInt;
            KeyFactory factory = KeyFactory.getInstance("RSA");

            for(ApplePublicKey applePublicKey: keys.getKeys()) {

                modulus = applePublicKey.getModulus();
                exponent = applePublicKey.getExponent();

                modulusAsBigInt = new BigInteger(1, Base64.decodeBase64(modulus));
                exponentAsBigInt = new BigInteger(1, Base64.decodeBase64(exponent));

                RSAPublicKeySpec spec = new RSAPublicKeySpec(modulusAsBigInt, exponentAsBigInt);
                applePublicKeys.add(factory.generatePublic(spec));
            }
            applePublicKeys = Collections.unmodifiableList(applePublicKeys);
            expirationTimeInMillis = System.currentTimeMillis() + refreshSkewMilliseconds;
            return this;

        } catch (NoSuchAlgorithmException e) {
            throw new ApiException(AppleTokenStatus.APPLE_SIGNIN_PUBLIC_KEY_ERROR);
        } catch (Exception e) {
            throw new ApiException(AppleTokenStatus.APPLE_PUBLIC_KEY_UNAVAILABLE);
        } finally {
            lock.unlock();
        }
    }

    private AppleJWKSet fetchRawPublicKeys() throws Exception {
        try {
            String response = appleHttpClient.fetchApplePublicKeyResponse();
            if (!StringUtils.isEmpty(response)) {
                return new Gson().fromJson(response, AppleJWKSet.class);
            }
        } catch (Exception e) {
            throw new ApiException(AppleTokenStatus.APPLE_PUBLIC_KEY_UNAVAILABLE);
        }
        throw new ApiException(AppleTokenStatus.APPLE_PUBLIC_KEY_UNAVAILABLE);
    }
}