package com.quickstore.util.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.quickstore.R
import com.quickstore.ui.useCase.main.activity.MainActivity

private const val CHANEL_ID = "quick_store_chanel"
private const val NAME = "quick_store"

fun showNotification(context: Context, title: String, body: String){
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    val notification = NotificationCompat.Builder(context, CHANEL_ID)
            .setLargeIcon(((ContextCompat.getDrawable(context, R.drawable.ic_notification)) as BitmapDrawable).bitmap)
            .setSmallIcon(R.drawable.ic_notification_small)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle()
                    .bigText(body))
            .build()


    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notificationId = System.currentTimeMillis().toInt()

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val mChannel = NotificationChannel(CHANEL_ID, NAME, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(mChannel)
    }

    notificationManager.notify(notificationId, notification)
}