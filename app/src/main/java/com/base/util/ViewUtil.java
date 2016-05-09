package com.base.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.ui.main.R;

import java.lang.reflect.Field;


public class ViewUtil {

	/**
	 * activity自动findview
	 */
	public static void autoFind(Activity activity) {
		try {
			Class<?> clazz = activity.getClass();
			Field[] fields = clazz.getDeclaredFields();// 获得Activity中声明的字段
			for (Field field : fields) {
				if (field.getGenericType().toString().contains("widget")
						|| field.getGenericType().toString().contains("view")
						|| field.getGenericType().toString()
								.contains("WebView")) {// 找到所有的view和widget,WebView
					try {
						String name = field.getName();
						Field idfield = R.id.class.getField(name);
						int id = idfield.getInt(new R.id());// 获得view的id
						field.setAccessible(true);
						field.set(activity, activity.findViewById(id));// 给我们要找的字段设置值
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fragment以及ViewHolder等自动findview
	 */

	public static void autoFind(Object obj, View view) {
		try {
			Class<?> clazz = obj.getClass();
			Field[] fields = clazz.getDeclaredFields();// 获得Activity中声明的字段
			for (Field field : fields) {
				if (field.getGenericType().toString().contains("widget")
						|| field.getGenericType().toString().contains("view")
						|| field.getGenericType().toString()
								.contains("WebView")) {// 找到所有的view和widget
					try {
						String name = field.getName();
						Field idfield = R.id.class.getField(name);
						int id = idfield.getInt(new R.id());
						field.setAccessible(true);
						field.set(obj, view.findViewById(id));// 给我们要找的字段设置值
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 隐藏软键盘
	 */
	public static void hideKeyboard(Activity c) {
		try {
			InputMethodManager imm = (InputMethodManager) c
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(c.getCurrentFocus().getWindowToken(), 0);
		} catch (NullPointerException e) {
		}
	}
}
