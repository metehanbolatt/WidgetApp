package com.metehanbolat.widgetexample

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

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
                        val intent = Intent(context, MainActivity::class.java)
                        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                        val views = RemoteViews(context.packageName, R.layout.example_widget)
                        views.setOnClickPendingIntent(R.id.exampleWidgetButton, pendingIntent)
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                    }
                }
            }
        }

    }
}