package com.time.ygslys_gerisayim;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class widgetYgsTransparent extends AppWidgetProvider {
	private static final String LOG_TAG = "ExampleWidget";
	public long systemMs, ygsMs = 1457906400000L, lysMs = 1466373600000L,
			gecici;
	long ygsGun, ygsSaat, ygsDakika, ygsSaniye, ygsSaniyeBolum, ygsSaatBolum; // ygs

	private static final DateFormat df = new SimpleDateFormat("hh:mm:ss");

	/**
	 * Custom Intent name that is used by the AlarmManager to tell us to update
	 * the clock once per second.
	 */
	public static String CLOCK_WIDGET_UPDATE = "com.time.ygslys_gerisayim.8BITCLOCK_WIDGET_UPDATE";

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);

		if (CLOCK_WIDGET_UPDATE.equals(intent.getAction())) {
			Log.d(LOG_TAG, "Clock update");
			// Get the widget manager and ids for this widget provider, then
			// call the shared
			// clock update method.
			ComponentName thisAppWidget = new ComponentName(
					context.getPackageName(), getClass().getName());
			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(context);
			int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
			for (int appWidgetID : ids) {
				updateAppWidget(context, appWidgetManager, appWidgetID);

			}
		}
	}

	private PendingIntent createClockTickIntent(Context context) {
		Intent intent = new Intent(CLOCK_WIDGET_UPDATE);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);

		return pendingIntent;
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		Log.d(LOG_TAG, "Widget Provider disabled. Turning off timer");
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(createClockTickIntent(context));
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		Log.d(LOG_TAG,
				"Widget Provider enabled.  Starting timer to update widget every second");
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, 1);
		alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),
				1000, createClockTickIntent(context));
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int N = appWidgetIds.length;

		Log.d(LOG_TAG, "Updating Example Widgets.");

		// Perform this loop procedure for each App Widget that belongs to this
		// provider
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];

			// Create an Intent to launch ExampleActivity
			Intent intent = new Intent(context, MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
					intent, 0);

			// Get the layout for the App Widget and attach an on-click listener
			// to the button
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.ygs_widget_transparent);
			 views.setOnClickPendingIntent(R.id.ygsRelative, pendingIntent);
			// Tell the AppWidgetManager to perform an update on the current app
			// widget
			appWidgetManager.updateAppWidget(appWidgetId, views);

			// Update The clock label using a shared method
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	}

	public void ygsZaman() {

		if (System.currentTimeMillis() >= ygsMs)
			ygsMs += 31536000000L;
		systemMs = System.currentTimeMillis();

		gecici = (ygsMs - systemMs) / 1000;
		ygsSaniye = gecici % 60;
		ygsSaniyeBolum = gecici / 60;
		ygsDakika = ygsSaniyeBolum % 60;
		ygsSaat = ygsSaniyeBolum / 60;
		ygsGun = ygsSaat;
		ygsSaat %= 24;
		ygsGun /= 24;

	}

	public void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId) {
		// String currentTime = df.format(new Date());

		ygsZaman();
		RemoteViews updateViews = new RemoteViews(context.getPackageName(),
				R.layout.ygs_widget_transparent);

		updateViews
				.setTextViewText(R.id.tvGun, String.valueOf(ygsGun) + " gün");

		appWidgetManager.updateAppWidget(appWidgetId, updateViews);

	}

}