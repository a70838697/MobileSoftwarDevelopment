package com.example.casper.test;

import com.example.casper.data.ShopLoader;
import com.example.casper.data.model.Shop;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShopLoaderTest {

    ShopLoader shopLoader;

    @Before
    public void setUp() throws Exception {
        shopLoader = new ShopLoader();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getShops() {
        Assert.assertNotNull(shopLoader.getShops());
    }

    @Test
    public void download() {
        String content = shopLoader.download("http://www.jiaozuoye.com/joj/backup/my.json");
        assertEquals(364, content.length());
        assertTrue(content.contains("\"longitude\": \"113.526421\","));
    }

    @Test
    public void parseJson() {
        String content = "{"
                + "  \"shops\": [{"
                + "    \"name\": \"暨南大学珠海校区\","
                + "    \"latitude\": \"22.255925\","
                + "    \"longitude\": \"113.541112\","
                + "    \"memo\": \"暨南大学珠海校区\""
                + "  },"
                + "    {"
                + "      \"name\": \"明珠商业广场\","
                + "      \"latitude\": \"22.251953\","
                + "      \"longitude\": \"113.526421\","
                + "      \"memo\": \"珠海二城广场\""
                + "    }"
                + "  ]"
                + "}";
        shopLoader.parseJson(content);
        assertEquals(2, shopLoader.getShops().size());
        Shop shop = shopLoader.getShops().get(1);
        assertEquals("明珠商业广场", shop.getName());
        assertEquals("珠海二城广场", shop.getMemo());
        assertTrue(Math.abs(22.251953 - shop.getLatitude()) < 1e-6);
        assertTrue(Math.abs(113.526421 - shop.getLongitude()) < 1e-6);
    }
}