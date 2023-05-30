package edu.uw.tcss450.codichun.team6tcss450.services;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.codichun.team6tcss450.AuthActivity;
import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom.ChatMessage;
import me.pushy.sdk.Pushy;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;

public class PushReceiver extends BroadcastReceiver {

    public static final String RECEIVED_NEW_MESSAGE = "new message from pushy";
    public static final String RECEIVED_NEW_CHATROOM = "new message from pushy";

    private static final String CHANNEL_ID = "1";

    @Override
    public void onReceive(Context context, Intent intent) {

        String typeOfMessage = intent.getStringExtra("type");

        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);

        /**
         * handle new messages
         */
        if ("msg".equals(typeOfMessage)) {
            ChatMessage message = null;
            int chatId = -1;
            try {
                message = ChatMessage.createFromJsonString(intent.getStringExtra("message"));
                chatId = intent.getIntExtra("chatid", -1);
            } catch (JSONException e) {
                throw new IllegalStateException("Error from Web Service. Contact Dev Support");
            }

            if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) {
                Log.d("PUSHY", "Message received in foreground: " + message);

                Intent i = new Intent(RECEIVED_NEW_MESSAGE);
                i.putExtra("chatMessage", message);
                i.putExtra("chatid", chatId);
                i.putExtras(intent.getExtras());

                context.sendBroadcast(i);

            } else {
                Log.d("PUSHY", "Message received in background: " + message.getMessage());

                Intent i = new Intent(context, AuthActivity.class);
                i.putExtras(intent.getExtras());

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                        i, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_notifications_active_24)
                        .setContentTitle("Message from: " + message.getSender())
                        .setContentText(message.getMessage())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent);

                Pushy.setNotificationChannel(builder, context);

                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

                notificationManager.notify(1, builder.build());
            }
            /**
             * handle new chat rooms
             */
        } else if ("newchatroom".equals(typeOfMessage)) {//show new chat room on all members' lists
            int chatRoomId = intent.getIntExtra("chatRoomId", -1);

            if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) {
                Log.d("PUSHY", "New chat room received in foreground: " + chatRoomId);
                Intent i = new Intent(RECEIVED_NEW_CHATROOM);
                i.putExtra("chatRoomId", chatRoomId);
                context.sendBroadcast(i);
                System.out.println("***** new chat room broadcast sent ******");


            } else {
                Log.d("PUSHY", "New chat room received in background: " + chatRoomId);

                Intent i = new Intent(context, AuthActivity.class);
                i.putExtra("chatRoomId", chatRoomId);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                        i, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_notifications_active_24)
                        .setContentTitle("You have been added to a new chat room!")
                        .setContentText("Click to open the new chat room.")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent);

                Pushy.setNotificationChannel(builder, context);

                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

                notificationManager.notify(1, builder.build());
            }

        }
    }

}
