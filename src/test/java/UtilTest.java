import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void utilEmailTest() {
        assertTrue("This email is valid", Util.validEmail("orban_viktor@kormany.hu"));
        assertTrue("This email is valid", Util.validEmail("orban_viktor@asd.com"));
        assertTrue("This email is valid", Util.validEmail("orbanviktor63@kormany.hu"));
        assertTrue("This email is valid", Util.validEmail("orbanviktor63@kormany.com"));
        assertFalse("Empty", Util.validEmail(""));
        assertFalse("Whitespace only", Util.validEmail(" "));
        assertFalse("Lot of whitespace", Util.validEmail(" orban viktor @ kormany . hu"));
        assertFalse("Just a string", Util.validEmail("orban"));
        assertFalse("2 part string", Util.validEmail("orban viktor"));
        assertFalse("String", Util.validEmail("orban_viktor"));
        assertFalse("String whit an 'at'", Util.validEmail("orban_viktor@"));
        assertFalse("Without a .[hu]", Util.validEmail("orban_viktor@kormany"));
        assertFalse("Without an ending", Util.validEmail("orban_viktor@kormany."));
        assertFalse("Bad ending", Util.validEmail("orban_viktor@kormany.h"));
        assertFalse("Whitespace", Util.validEmail("orban viktor63@kormany.com"));
        assertFalse("Wrong top level domain", Util.validEmail("orban_viktor@kormany.usa"));
        assertFalse("Wrong top level domain", Util.validEmail("orban_viktor@kormanyol.huasd"));
    }

    @Test
    public void utilEmptyTest() {
        Map<String, String> map = new HashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");
        assertTrue("No empty fields.", Util.noEmptyFields(map));
        Map<String, String> map1 = new HashMap<>();
        map1.put("one", "");
        map1.put("two", "2");
        map1.put("three", "3");
        assertFalse("First value empty.", Util.noEmptyFields(map1));
        Map<String, String> map2 = new HashMap<>();
        map2.put("one", "1");
        map2.put("two", "");
        map2.put("three", "3");
        assertFalse("Second value empty.", Util.noEmptyFields(map2));
        Map<String, String> map3 = new HashMap<>();
        map3.put("one", "1");
        map3.put("two", "2");
        map3.put("three", "");
        assertFalse("Third value empty.", Util.noEmptyFields(map3));
        Map<String, String> map4 = new HashMap<>();
        map4.put("one", "");
        map4.put("two", "");
        map4.put("three", "");
        assertFalse("All values empty.", Util.noEmptyFields(map4));
        Map<String, String> map5 = new HashMap<>();
        map5.put("one", "1");
        map5.put("two", "2");
        map5.put("three", "3");
        map5.put("OrderDate", "orderDateValue");
        assertTrue("All values empty.", Util.noEmptyFields(map5));
        // orderDate can be empty
        Map<String, String> map6 = new HashMap<>();
        map6.put("one", "1");
        map6.put("two", "2");
        map6.put("three", "3");
        map6.put("OrderDate", "");
        assertTrue("All values empty.", Util.noEmptyFields(map6));

    }

    @Test
    public void utilOrderDateTest() {
        String pattern = "yyyy-MM-dd";
        String orderDate = "2020-01-01";
        assertTrue("Good date", Util.validOrderDate(orderDate, pattern));
        String orderDate1 = "2020-01-40";
        assertFalse("Wrong day", Util.validOrderDate(orderDate1, pattern));
        String orderDate2 = "2020-20-01";
        assertFalse("Wrong month", Util.validOrderDate(orderDate2, pattern));
        String orderDate3 = "-01-01";
        assertFalse("Wrong year", Util.validOrderDate(orderDate3, pattern));
        String orderDate4 = "2018/01/01";
        assertFalse("Wrong pattern", Util.validOrderDate(orderDate4, pattern));
    }

    @Test
    public void utilPostCodeTest() {
        String integer = "1";
        assertTrue("The minimum value", Util.validPostcode(integer));
        String integer1 = "1234";
        assertTrue("Random postcode", Util.validPostcode(integer1));
        String integer2 = "";
        assertFalse("Empty string", Util.validPostcode(integer2));
        String integer3 = "12a4";
        assertFalse("A char between the numbers", Util.validPostcode(integer3));
        String integer4 = "123 4";
        assertFalse("Whitespace between the numbers ", Util.validPostcode(integer4));
        String integer5 = "asdf";
        assertFalse("Just characters", Util.validPostcode(integer5));
        String integer6 = "-1234";
        assertFalse("Negative int", Util.validPostcode(integer6));
        String integer7 = "0";
        assertFalse("Null value", Util.validPostcode(integer7));
    }

    @Test
    public void utilSalePriceTest() {
        String bigdec = "1,00";
        assertNotNull("Minimum price", Util.validSalePrice(bigdec));
        String bigdec1 = "23,05";
        assertNotNull("Good price", Util.validSalePrice(bigdec1));
        String bigdec2 = "872235,12";
        assertNotNull("Horrific price", Util.validSalePrice(bigdec2));
        String bigdec3 = "15";
        assertNotNull("Integer", Util.validSalePrice(bigdec3));
        String bigdec4 = "23,";
        assertNotNull("Number ending with a coma", Util.validSalePrice(bigdec4));
        String bigdec5 = ",52";
        assertNull("Number beginning with a coma 0,[...] its smaller than 1,00", Util.validSalePrice(bigdec5));
        String bigdec6 = "0,99";
        assertNull("Smaller than 1.00", Util.validSalePrice(bigdec6));
        String bigdec7 = "-23,04";
        assertNull("Negative", Util.validSalePrice(bigdec7));
        String bigdec8 = "2 3";
        assertNull("Whitespace", Util.validSalePrice(bigdec8));
        String bigdec9 = "twopointtwelve";
        assertNull("String", Util.validSalePrice(bigdec9));
    }

    @Test
    public void utilShippingPriceTest() {
        String bigdec = "0";
        assertNotNull("Smallest amount of shipping price", Util.validShippingPrice(bigdec));
        String bigdec1 = ",56";
        assertNotNull("Bigger than 0", Util.validShippingPrice(bigdec1));
        String bigdec2 = "15,";
        assertNotNull("Integer", Util.validShippingPrice(bigdec2));
        String bigdec3 = "234,4";
        assertNotNull("Big shipping price", Util.validShippingPrice(bigdec3));
        String bigdec4 = "3634563,56";
        assertNotNull("Horrific shipping price", Util.validShippingPrice(bigdec4));
        String bigdec5 = "-,51";
        assertNull("Negative", Util.validShippingPrice(bigdec5));
        String bigdec6 = "-234,34";
        assertNull("Bigger negative", Util.validShippingPrice(bigdec6));
        String bigdec7 = "-12";
        assertNull("Negative integer", Util.validShippingPrice(bigdec7));
        String bigdec8 = "2 456";
        assertNull("Whitespace between the numbers", Util.validShippingPrice(bigdec8));
        String bigdec9 = "alma";
        assertNull("String", Util.validShippingPrice(bigdec9));
    }

    @Test
    public void utilStatusTest() {
        String status = "IN_STOCK";
        assertTrue("Perfect", Util.validStatus(status));
        String status1 = "OUT_OF_STOCK";
        assertTrue("Perfect", Util.validStatus(status1));
        String status2 = "";
        assertFalse("Empty string", Util.validStatus(status2));
        String status3 = "in_stock";
        assertFalse("Lowercase", Util.validStatus(status3));
        String status4 = "IN STOCK";
        assertFalse("Missing separation", Util.validStatus(status4));
        String status5 = "in stock";
        assertFalse("Small and bad separation", Util.validStatus(status5));
    }

}
