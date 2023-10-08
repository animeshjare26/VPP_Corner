package com.vpppcoe.vppcorner

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationHelper(var co: Context, var msg: String){
    private val CHANNEL_ID = "message id"
    private val NOTIFICATION_ID = 123

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingPermission")
    fun Notification(){
        createNotificationChannel()
        val senInt = Intent(co,LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingInt = PendingIntent.getActivities(co,0, arrayOf(senInt),PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val icon = BitmapFactory.decodeResource(co.resources,R.drawable.profile_icon)
        val isnotification = NotificationCompat.Builder(co,CHANNEL_ID)
            .setSmallIcon(R.drawable.profile_icon)
            .setLargeIcon(icon)
            .setContentTitle("Order Status")
            .setContentText(msg)
            .setContentIntent(pendingInt)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        NotificationManagerCompat.from(co)
            .notify(NOTIFICATION_ID,isnotification)
    }


    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val name = CHANNEL_ID
            val des = "Channel Des"
            val imports = NotificationManager.IMPORTANCE_DEFAULT
            val channels = NotificationChannel(CHANNEL_ID,name,imports).apply {
                description = des
            }
            val notificationManager = co.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channels)

        }
    }
}