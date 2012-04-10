package com.chauhai.android.batsg.test.util;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.chauhai.android.batsg.util.StringUtil;

public class StringUtilTest extends TestCase {

  public void testCamelToUnderscore() {
    assertEquals("hello_how_are_you", StringUtil.camelToUnderscore("HelloHowAreYou"));
  }

  public void testJoinListOfString() {
    ArrayList<String> list = new ArrayList<String>();
    // List has no element.
    assertEquals("", StringUtil.join(list));
    // List has one element.
    list.add("string 1");
    assertEquals("string 1",
        StringUtil.join(list));
    // List has many elements.
    list.add("string 2");
    list.add("string 3");
    assertEquals("string 1, string 2, string 3",
        StringUtil.join(list));
  }

  public void testJoinListOfStringString() {
    ArrayList<String> list = new ArrayList<String>();
    // List has no element.
    assertEquals("", StringUtil.join(list, "+-"));
    // List has one element.
    list.add("string 1");
    assertEquals("string 1",
        StringUtil.join(list, "+-"));
    // List has many elements.
    list.add("string 2");
    list.add("string 3");
    assertEquals("string 1+-string 2+-string 3",
        StringUtil.join(list, "+-"));
  }


  public void testJoinStringArrayDefault() {
    // Array has no element.
    assertEquals("", StringUtil.join(new String[0]));
    // Array has one element.
    assertEquals("string 1",
        StringUtil.join(new String[] {"string 1"}));
    // Array has many elements.
    assertEquals("string 1, string 2, string 3",
        StringUtil.join(new String[] {"string 1", "string 2", "string 3"}));
  }

  public void testJoinStringArrayWithSeparator() {
    // Array has no element.
    assertEquals("", StringUtil.join(new String[0], "+-"));
    // Array has one element.
    assertEquals("string 1",
        StringUtil.join(new String[] {"string 1"}, "+-"));
    // Array has many elements.
    assertEquals("string 1+-string 2+-string 3",
        StringUtil.join(new String[] {"string 1", "string 2", "string 3"}, "+-"));
  }

}
