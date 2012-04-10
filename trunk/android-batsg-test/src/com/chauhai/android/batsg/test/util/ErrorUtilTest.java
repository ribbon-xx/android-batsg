package com.chauhai.android.batsg.test.util;

import junit.framework.TestCase;

import com.chauhai.android.batsg.util.ErrorUtil;

public class ErrorUtilTest extends TestCase {

  public void testRuntimeException() {
    RuntimeException re;

    // Test wrapping normal exception.
    Exception e = new Exception("An exception");
    re = ErrorUtil.runtimeException(e);
    assertEquals(e, re.getCause());

    // Test wrapping runtime exception.
    re = new RuntimeException("A RuntimeException");
    re = ErrorUtil.runtimeException(re);
    assertEquals(null, re.getCause());
  }
}
