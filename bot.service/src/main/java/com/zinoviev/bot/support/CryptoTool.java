package com.zinoviev.bot.support;

import org.hashids.Hashids;

public class CryptoTool {

    private final Hashids hashids;
    private final int MIN_HASH_LEN = 10;

    public CryptoTool(String salt) {
        this.hashids = new Hashids(salt, 10);
    }

    public String linkOf(long value){
        return hashids.encode(value);
    }

    public Long idOf(String value){
        long[] res = hashids.decode(value);
        if (res != null && res.length > 0){
            return res[0];
        }
        return null;
    }
}
