package com.chauhai.android.batsg.db.sqlite.smalldb;

import com.chauhai.android.batsg.util.ErrorUtil;

/**
 * Get type of DbBean class specified by its class name.
 *
 * @author umbalaconmeogia
 *
 */
public class BaseDbBeanClassFactory {

  /**
   * Get type of DbBean class specified by its class name.
   * <p>
   * By default, this returns the class in the same package
   * with the factory class.
   * @param className The DbBean class simple name.
   * @return
   */
  public Class<?> beanClassBySimpleName(String className) {
    return classInTheSamePackage(className);
  }

  /**
   * Get type of a class in the same package with the factory class,
   * specified by its name.
   * @param className The class simple name.
   * @return
   */
  protected Class<?> classInTheSamePackage(String className) {
    try {
      className = getClass().getPackage().getName() + "." + className;
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }
}
