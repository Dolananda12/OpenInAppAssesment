package com.example.openinappassesment.Navigaiton
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import com.example.openinappassesment.R

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageBitmap?= null,
    val route : String = ""
) {
    @Composable
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        val context = LocalContext.current
        return listOf(
            BottomNavigationItem(
                label = "Links",
                icon = BitmapFactory.decodeResource(context.resources,R.drawable.link).asImageBitmap(),
                route = Screens.Links.route
            ),
            BottomNavigationItem(
                label = "Courses",
                icon = BitmapFactory.decodeResource(context.resources,R.drawable.courses).asImageBitmap(),
                route = Screens.Courses.route
            ),
            BottomNavigationItem(
                label = "",
                icon = null,
                route = Screens.Home.route
            ),
            BottomNavigationItem(
                label = "Campaigns",
                icon = BitmapFactory.decodeResource(context.resources,R.drawable.campaigns).asImageBitmap(),
                route = Screens.Campaigns.route
            ),
            BottomNavigationItem(
                label = "Profile",
                icon = BitmapFactory.decodeResource(context.resources,R.drawable.profile).asImageBitmap(),
                route = Screens.Profile.route
            )
        )
    }
}
