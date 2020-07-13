package com.time.ygslys_gerisayim;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class widgetLysTransparent extends AppWidgetProvider {
	private static final String LOG_TAG = "ExampleWidget";
	public long systemMs, ygsMs = 1457906400000L, lysMs = 1466373600000L,
			gecici;
	long lysGun, lysSaat, lysDakika, lysSaniye, lysSaniyeBolum; // lys variable

	/**
	 * Custom Intent name that is used by the AlarmManager to tell sus to update
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
					R.layout.lys_widget_transparent);
			 views.setOnClickPendingIntent(R.id.lysRelative, pendingIntent);
			// Tell the AppWidgetManager to perform an update on the current app
			// widget
			appWidgetManager.updateAppWidget(appWidgetId, views);

			// Update The clock label using a shared method
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	}

	public void lysZaman() {
		if (System.currentTimeMillis() >= lysMs) 
			lysMs += 31536000000L;
		systemMs = System.currentTimeMillis();

		gecici = (lysMs - systemMs) / 1000;
		lysSaniye = gecici % 60;
		lysSaniyeBolum = gecici / 60;
		lysDakika = lysSaniyeBolum % 60;
		lysSaat = lysSaniyeBolum / 60;
		lysGun = lysSaat;
		lysSaat %= 24;
		lysGun /= 24;


	}

	public void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId) {
		// String currentTime = df.format(new Date());

		lysZaman();
		RemoteViews updateViews = new RemoteViews(context.getPackageName(),
				R.layout.lys_widget_transparent);

		updateViews
				.setTextViewText(R.id.tvGun, String.valueOf(lysGun) + " gün");

		appWidgetManager.updateAppWidget(appWidgetId, updateViews);

	}

}