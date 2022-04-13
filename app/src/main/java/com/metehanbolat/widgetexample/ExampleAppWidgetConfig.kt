package com.metehanbolat.widgetexample

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import com.metehanbolat.widgetexample.Constants.KEY_BUTTON_TEXT
import com.metehanbolat.widgetexample.Constants.SHARED_PRES
import com.metehanbolat.widgetexample.databinding.ActivityExampleAppWidgetConfigBinding

class ExampleAppWidgetConfig : AppCompatActivity() {

    private lateinit var binding: ActivityExampleAppWidgetConfigBinding

    var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleAppWidgetConfigBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val configIntent = intent
        val extras = configIntent.extras
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
        }

        binding.button.setOnClickListener {
            val appWidgetManager = AppWidgetManager.getInstance(this)
            val intent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val views = RemoteViews(this.packageName, R.layout.example_widget)
            views.setOnClickPendingIntent(R.id.btn_account, pendingIntent)
            views.setCharSequence(R.id.btn_account, "setText", binding.editText.text.toString())
            appWidgetManager.updateAppWidget(appWidgetId, views)
            val prefs = getSharedPreferences(SHARED_PRES, MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString(KEY_BUTTON_TEXT + appWidgetId, binding.editText.text.toString())
            editor.apply()

            val resultValue = Intent()
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(RESULT_OK, resultValue)
            finish()
        }

    }
}