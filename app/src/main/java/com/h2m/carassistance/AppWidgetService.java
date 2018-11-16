package com.h2m.carassistance;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import java.util.List;

public class AppWidgetService extends RemoteViewsService {

    public static void updateWidget(Context context, List<CarItemsEntry> itemsEntry) {
        Prefs.saveRecipe(context, itemsEntry);
        Log.d("responseeeeeeeeeeeeee", " " + itemsEntry.size());
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, ItemsAppWidget.class));
        ItemsAppWidget.updateAppWidgets(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        return new ListRemoteViewsFactory(getApplicationContext());
    }

}