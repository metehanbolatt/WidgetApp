package com.metehanbolat.widgetexample

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import android.widget.Toast
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
                        views.setOnClickPendingIntent(R.id.btn_account, pendingIntent)
                        views.setCharSequence(R.id.btn_account, "setText", buttonText)

                        val appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId)
                        resizeWidget(appWidgetOptions, views)
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                    }
                }
            }
        }
    }

    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: Bundle
    ) {
        val views = RemoteViews(context.packageName, R.layout.example_widget)
        resizeWidget(newOptions, views)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun resizeWidget(appWidgetOptions: Bundle, views: RemoteViews) {
        val minWidth = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)
        val maxWidth = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH)
        val minHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT)
        val maxHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT)

        println("minHeight: $minHeight")
        println("maxHeight: $maxHeight")
        println("minWidth: $minWidth")
        println("maxWidth: $maxWidth")

        if (maxHeight > 400) {
            views.setViewVisibility(R.id.buttonsLinear, View.VISIBLE)
        }else {
            views.setViewVisibility(R.id.buttonsLinear, View.GONE)
        }
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        Toast.makeText(context, "OnDeleted", Toast.LENGTH_SHORT).show()
    }

    override fun onEnabled(context: Context?) {
        Toast.makeText(context, "OnEnabled", Toast.LENGTH_SHORT).show()
    }

    override fun onDisabled(context: Context?) {
        Toast.makeText(context, "OnDisabled", Toast.LENGTH_SHORT).show()
    }

}