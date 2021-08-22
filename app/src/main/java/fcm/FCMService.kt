package fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notesapp.R
import com.example.notesapp.login.appConstants.AppConstants
import com.example.notesapp.login.appConstants.AppConstants.Companion.CHANNEL_ID
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    private val TAG = "FCMService"

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, message.from.toString())
        Log.d(TAG, message.notification?.body.toString())

        createNotification(message)
    }

    private fun createNotification(message: RemoteMessage) {

        val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentTitle("NotesAppMessage")
            .setContentText(message.notification?.body)
            .setSound(ringtone)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "TodoNotesAppChannel"
            val descriptionText = message.notification?.body
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            notificationManager.notify(123,builder)
        }


    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "recieved new token - $token")
    }
}