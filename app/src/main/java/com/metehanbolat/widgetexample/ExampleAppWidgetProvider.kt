package com.metehanbolat.widgetexample

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.metehanbolat.widgetexample.Constants.KEY_BUTTON_TEXT
import com.metehanbolat.widgetexample.Constants.SHARED_PRES

class ExampleAppWidgetProvider: AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        appWidgetIds?.let {
            for (appWidgetId in it) {
                context?.let { context ->
                    appWidgetManager?.let { appWidgetManager ->
                        val intent = Intent(context, ExampleAppWidgetConfig::class.java)
                        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                        val prefs = context.getSharedPreferences(SHARED_PRES, Context.MODE_PRIVATE)
                        val buttonText  = prefs.getString(KEY_BUTTON_TEXT + appWidgetId, "Press Me")
                        val views = RemoteViews(context.packageName, R.layout.example_widget)
                        views.setOnClickPendingIntent(R.id.exampleWidgetButton, pendingIntent)
                        views.setCharSequence(R.id.exampleWidgetButton, "setText", buttonText)
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                    }
                }
            }
        }

    }
}