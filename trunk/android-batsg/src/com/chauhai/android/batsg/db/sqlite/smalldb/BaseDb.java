package com.chauhai.android.batsg.db.sqlite.smalldb;

import com.chauhai.android.batsg.util.StringUtil;

/**
 * Base class of db adapter and bean classes.
 *
 * @author umbalaconmeogia
 */
public class BaseDb {

  /**
   * Get the class name, without the package.
   * @return
   */
  public String className() {
    return getClass().getSimpleName();
  }

  /**
   * Store the database table name.
   */
  private String dbTableName;

  /**
   * Get the database table name.
   * <p>
   * This method get the database table by converting the class name
   * from camel case to underscore, removing the first character.
   * So you may override it for faster implementation.
   * For example, returning the table name directly.
   * <pre>
   * public String tableName() {
   *   return "table_name";
   * }
   * </pre>
   * @return
   */
  public String tableName() {
    // Generate the table name if it is not defined.
    if (dbTableName == null) {
      dbTableName = classNameToTableName(className());
    }
    return dbTableName;
  }

  /**
   * Get the database table name from the DbAdapter or Bean class name.
   * <p>
   * This will remove the first character of the class name,
   * then convert it from camel case to under score.
   * <p>
   * For example, if the class name is <code>BMyTable</code>,
   * then the return value is <code>my_table</code>.
   * @param className
   * @return
   */
  public static String classNameToTableName(String className) {
    // Remove the first character
    if (className.length() > 2) {
      className = className.substring(1);
    }
    // Convert to underscore case.
    return StringUtil.camelToUnderscore(className);
  }
}
