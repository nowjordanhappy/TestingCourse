package com.plcoding.testingcourse.part12.presentation

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.core.app.NotificationCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import androidx.test.rule.GrantPermissionRule.grant
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.plcoding.testingcourse.MainActivity
import com.plcoding.testingcourse.part12.data.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountdownNotificationTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    //Only for android 13+
//    @Rule
//    public val grantPermissionRule = GrantPermissionRule.grant(Manifest.permission.POST_NOTIFICATIONS)

//    @Rule
//    val permissions = emptyArray<String>()
//    @Rule
//    val permissionRule: GrantPermissionRule =
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            grant(*permissions, Manifest.permission.POST_NOTIFICATIONS)
//        } else {
//            grant(*permissions)
//        }

    @Test
    fun testNotificationCountdown() = runTest {
        runCurrent()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        for(i in 5 downTo 1) {
            composeRule.waitUntil {
                notificationManager.activeNotifications.isNotEmpty()
            }
            val notification = notificationManager.activeNotifications.first {
                it.id == 1
            }.notification

            val contentText = notification.extras.getString(NotificationCompat.EXTRA_TEXT)
            assertThat(contentText).isEqualTo("Counting down: $i")

            advanceTimeBy(1000L)
            runCurrent()
        }
        notificationManager.cancel(1)
    }
}