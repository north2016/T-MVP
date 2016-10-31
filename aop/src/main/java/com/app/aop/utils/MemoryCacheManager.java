package com.app.aop.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by baixiaokang on 16/9/20.
 */
public class MemoryCacheManager {

    public static final String CREATE_COMPANY_CACHE = "create_company_bitmap";//创建公司缓存到本地

    private static class Holder {
        public static final MemoryCacheManager instance = new MemoryCacheManager();
    }

    public static MemoryCacheManager getInstance() {
        return Holder.instance;
    }

    private MemoryCacheManager() {
    }

    final static int cacheSize = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;
    static LruCache<String, Object> mMemoryCache = new LruCache<String, Object>(
            cacheSize);

    public static void add(String key, Object mObject) {
        mMemoryCache.put(key, mObject);
    }

    public static Object get(String key) {
        return mMemoryCache.get(key);
    }

    /***
     * 缓存创建公司认证材料Bitmap，方便再次进入回复
     *
     * @return
     */
    public static Bitmap getCreateCompanyBitmap() {
        Object obj = null;
        if (mMemoryCache != null) {
            obj = mMemoryCache.get(CREATE_COMPANY_CACHE);
        }
        if (obj != null && obj instanceof Bitmap) {
            return (Bitmap) obj;
        }
        return null;
    }

    public static void saveCreateBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        if (mMemoryCache != null) {
            mMemoryCache.put(CREATE_COMPANY_CACHE, bitmap);
        }
    }

    public static void clearCreateBitmap() {
        if (mMemoryCache != null) {
            mMemoryCache.remove(CREATE_COMPANY_CACHE);
        }
    }
}
