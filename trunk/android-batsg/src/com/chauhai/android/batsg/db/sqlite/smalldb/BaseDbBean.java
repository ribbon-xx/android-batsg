package com.chauhai.android.batsg.db.sqlite.smalldb;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.database.Cursor;

import com.chauhai.android.batsg.util.ErrorUtil;
import com.chauhai.android.batsg.util.StringUtil;

/**
 * Base class for bean.
 * @author umbalaconmeogia
 */
public class BaseDbBean extends BaseDb {

  /**
   * Primary key.
   */
  public long _id;

  /**
   * Parse a cursor to a bean.
   * @param <T> Bean type.
   * @param cursor Cursor that hold the data.
   * @param beanClass Bean class.
   * @param cursorColumnIndex
   * @param cursorColumnIndex Column indexes of the cursor.
   *     If is <code>null</code>, then new CursorColumnIndex will be created.
   * @return
   */
  public static <T extends BaseDbBean> T parse(Cursor cursor,
      Class<T> beanClass,
      CursorColumnIndex cursorColumnIndex) {

    try {
      T bean = beanClass.newInstance();
      bean.parse(cursor, cursorColumnIndex);
      return bean;
    } catch (Exception e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Parse data from a cursor to this object.
   * <p>
   * This uses reflection to set the field value.
   * Subclass may override this to implement a faster way. For example:
   * <pre>
   * public void parse(Cursor cursor, CursorColumnIndex columnIndex) {
   *   if (columnIndex == null) {
   *     columnIndex = new CursorColumnIndex(cursor);
   *   }
   *   _id = cursor.getInt(columnIndex.getIndex("_id"));
   *   name = cursor.getInt(columnIndex2.getIndex("name"));
   * }
   * </pre>
   * @param cursor
   * @param cursorColumnIndex Column indexes of the cursor.
   *     If is <code>null</code>, then new CursorColumnIndex will be created.
   */
  public void parse(Cursor cursor, CursorColumnIndex cursorColumnIndex) {
    try {
      if (cursor != null) {
        parseUsingReflection(cursor);
      }
    } catch (Exception e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Parse data from a cursor to this object, using reflection to implement.
   * @param cursor
   * @throws SecurityException
   * @throws NoSuchFieldException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   */
  private void parseUsingReflection(Cursor cursor)
      throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

    Class<?> thisClass = this.getClass();
    for (int columnIndex = cursor.getColumnCount() - 1; columnIndex >= 0; columnIndex--) {
      String columnName = cursor.getColumnName(columnIndex);
      Field field = thisClass.getField(columnName);
      if (field.getType().equals(Integer.TYPE)) {
        field.setInt(this, cursor.getInt(columnIndex));
      } else if (field.getType().equals(Long.TYPE)) {
        field.setLong(this, cursor.getLong(columnIndex));
      } else if (field.getType().equals(Float.TYPE)) {
        field.setFloat(this, cursor.getFloat(columnIndex));
      } else if (field.getType().equals(Double.TYPE)) {
        field.setDouble(this, cursor.getDouble(columnIndex));
      } else if (field.getType().equals(String.class)) {
        field.set(this, cursor.getString(columnIndex));
      } else {
        throw new IllegalArgumentException("Not support " + field + " type: " + field.getType());
      }
    }
  }

  @Override
  public String toString() {
    // Get the class.
    Class<?> c = getClass();
    // Get public fields.
    Field[] publicFields = c.getFields();
    // Get values.
    ArrayList<String> fieldValues = new ArrayList<String>();
    // Access fields in reverse order. It may be the declaring order.
    for (int i = publicFields.length - 1; i >= 0; i--) {
      Field field = publicFields[i];
      try {
        fieldValues.add(field.getName() + ": " + field.get(this));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return c.getSimpleName() + "(" + StringUtil.join(fieldValues, ", ") + ")";
  }

  /**
   * Create ContentValue object from public fields that
   * has value defined of this bean.
   * @return
   */
  public ContentValues toContentValues() {
    ContentValues contentValues = new ContentValues(); // Return result.
    // Get public fields.
    Class<?> clazz = getClass();
    Field[] fields = clazz.getFields();
    // Set content values on public member (not static/final).
    for (Field field: fields) {
      int modifiers = field.getModifiers();
      if (!Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)) {
        try {
          String fieldName = field.getName();
          Class<?> fieldType = field.getType();
          if (fieldType.equals(Integer.TYPE)) {
            contentValues.put(fieldName, field.getInt(this));
          } else if (fieldType.equals(Long.TYPE)) {
            contentValues.put(fieldName, field.getLong(this));
          } else if (fieldType.equals(Float.TYPE)) {
            contentValues.put(fieldName, field.getFloat(this));
          } else if (fieldType.equals(Double.TYPE)) {
            contentValues.put(fieldName, field.getDouble(this));
          } else if (fieldType.equals(String.class)) {
            Object value = field.get(this);
            contentValues.put(fieldName, value == null ? null : value.toString());
          } else {
            throw new RuntimeException("Not support " + field +
                " type: " + fieldType);
          }
        } catch (Exception e) {
          throw ErrorUtil.runtimeException(e);
        }
      }
    }
    return contentValues;
  }

  /**
   * Keep information about column indexes of a cursor.
   */
  public static class CursorColumnIndex {

    /**
     * Connect column name and its column index.
     */
    private HashMap<String, Integer> columnIndexes;

    public CursorColumnIndex(Cursor cursor) {
      columnIndexes = new HashMap<String, Integer>();
      for (int columnIndex = cursor.getColumnCount() - 1; columnIndex >= 0; columnIndex--) {
        columnIndexes.put(cursor.getColumnName(columnIndex), columnIndex);
      }
    }

    /**
     * Get the column index of specified column name.
     * @param columnName
     * @return
     */
    public int getIndex(String columnName) {
      return columnIndexes.get(columnName);
    }
  }
}
