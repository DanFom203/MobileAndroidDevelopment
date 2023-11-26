package com.itis.android_homework.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.itis.android_homework.MainActivity
import com.itis.android_homework.R

class NotificationsHandler {
    private var channelId: String? = null
    private var id: Int = 1

    fun createNotification(ctx: Context,
                           header: String,
                           message: String,
                           selectedPriority : Int,
                           selectedPrivacy : Int,
                           isDetailedMessageChecked : Boolean,
                           isButtonsVisibilityChecked : Boolean) {
        (ctx.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.let { manager ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    channelId = ctx.getString(R.string.my_channel_id) + selectedPriority
                    val channel = NotificationChannel(
                        channelId,
                        ctx.getString(R.string.my_channel),
                        when (selectedPriority) {
                            2 -> NotificationManager.IMPORTANCE_LOW
                            3 -> NotificationManager.IMPORTANCE_DEFAULT
                            4 -> NotificationManager.IMPORTANCE_HIGH
                            else -> NotificationManager.IMPORTANCE_DEFAULT
                        }
                    )
                    channel.lightColor = Color.GREEN
                    channel.enableVibration(true)
                    manager.createNotificationChannel(channel)

                //нажитие на уведомление
                val intent = Intent(ctx, MainActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(
                    ctx,
                    101,
                    intent,
                    PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
                )

                //нажитие на левую кнопку
                val showTextIntent = Intent(ctx, MainActivity::class.java)
                showTextIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                showTextIntent.putExtra("actn", MainActivity.ACTION_LAUNCH_APP_KEY)
                val showTextPendingIntent = PendingIntent.getActivity(
                    ctx,
                    102,
                    showTextIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

                //нажитие на правую кнопку
                val openSettingsIntent = Intent(ctx, MainActivity::class.java)
                openSettingsIntent.putExtra("actn", MainActivity.ACTION_LAUNCH_SETTINGS_KEY)
                val openSettingsPendingIntent = PendingIntent.getActivity(
                    ctx,
                    103,
                    openSettingsIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                val action1 = NotificationCompat.Action.Builder(
                    R.drawable.ic_launcher_foreground,
                    "Launch app",
                    showTextPendingIntent
                ).build()

                val action2 = NotificationCompat.Action.Builder(
                    R.drawable.ic_launcher_foreground,
                    "Settings",
                    openSettingsPendingIntent
                ).build()

                val publicNotification = NotificationCompat.Builder(ctx, channelId!!)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(header)
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setPriority(selectedPriority)
                    .setChannelId(channelId!!)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)


                val privateNotification = NotificationCompat.Builder(ctx, channelId!!)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(header)
                    .setContentIntent(pendingIntent)
                    .setPriority(selectedPriority)
                    .setChannelId(channelId!!)
                    .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)

                val secretNotification = NotificationCompat.Builder(ctx, channelId!!)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(header)
                    .setContentIntent(pendingIntent)
                    .setPriority(selectedPriority)
                    .setChannelId(channelId!!)
                    .setVisibility(NotificationCompat.VISIBILITY_SECRET)

                val notification = when (selectedPrivacy) {
                    NotificationCompat.VISIBILITY_PUBLIC -> publicNotification
                    NotificationCompat.VISIBILITY_SECRET -> secretNotification
                    NotificationCompat.VISIBILITY_PRIVATE -> privateNotification
                    else -> publicNotification
                }

                if (isDetailedMessageChecked) {
                    val bigTextStyle = NotificationCompat.BigTextStyle()
                        .bigText(message)
                    notification.setStyle(bigTextStyle)
                }
                if (isButtonsVisibilityChecked) {
                    notification.addAction(action1)
                    notification.addAction(action2)
                }
                manager.notify(id, notification.build())
                id++
            }
        }

    }

}