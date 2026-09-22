package cn.keking.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * kkFileView #791 回归测试：不同路径下同名文件必须映射到不同的缓存 key，
 * 防止转换产物被互相覆盖（内容污染）。
 */
public class KkFileUtilsUrlCacheKeyTests {

    @Test
    void sameUrlProducesStableKey() {
        String url = "http://example.com/pathA/test.docx";
        assertEquals(KkFileUtils.urlCacheKey(url), KkFileUtils.urlCacheKey(url));
    }

    @Test
    void differentPathSameBasenameProducesDifferentKey() {
        String a = "http://example.com/pathA/test.docx";
        String b = "http://example.com/pathB/test.docx";
        assertNotEquals(KkFileUtils.urlCacheKey(a), KkFileUtils.urlCacheKey(b));
    }

    @Test
    void keyIsSafeForFileName() {
        String key = KkFileUtils.urlCacheKey("http://example.com/pathA/test.docx");
        assertEquals(8, key.length());
        assertFalse(key.contains("/"));
        assertFalse(key.contains("\\"));
        assertFalse(key.contains(".."));
        assertTrue(key.matches("[0-9a-f]{8}"));
    }
}
