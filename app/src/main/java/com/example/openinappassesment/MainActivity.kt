package com.example.openinappassesment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.openinappassesment.Database.TokenDatabase
import com.example.openinappassesment.Database.TokenRepository
import com.example.openinappassesment.Navigaiton.BottomNavigationItem
import com.example.openinappassesment.Navigaiton.Screens
import com.example.openinappassesment.Network.RecentLink
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainActivityViewModel
    val gray = Color(0xFF999CA0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val dao = TokenDatabase.getInstance(application).dao
        val repository = TokenRepository(dao)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainActivityViewModel::class.java]
        setContent {
            val navController = rememberNavController()
            Scaffold(modifier = Modifier
                .fillMaxSize(),
                containerColor = Color(0xFF0E6FFF),
                topBar = { TopBar() },
                bottomBar = {
                    BottomBar(navController)
                }) {
                Box(modifier = Modifier.padding(it)){
                    NavGraoh(navController = navController)
                }
            }
        }
    }
    @Composable
    fun CoursesScreen(){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
               Text("Welcome to Courses Screen!!", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Color.Black)
            }
        }
    }
    @Composable
    fun CampaignsScreen(){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                Text("Welcome to Campaigns Screen!!", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Color.Black)
            }
        }
    }
    @Composable
    fun ProfileScreen(){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                Text("Hi Ajay Manva!!", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Color.Black)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                Text("Number: ${viewModel.response.support_whatsapp_number}", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Color.Black)
            }
        }
    }
    @Composable
    fun Home_Screen(){
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .border(1.dp, Color.Black)
        ) {
            var loading by remember {
                mutableStateOf(false)
            }
            if (!loading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text(text = "Loading", color = Color(0xFF0E6FFF))
                }
                viewModel.ApiService().observe(this@MainActivity, Observer { completed ->
                    if (completed) {
                        println(viewModel.response)
                        loading = true
                    }
                })
                /*        viewModel.insertToken("bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI")
              */
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    RoundedCornerShape(30.dp),
                    colors = CardColors(
                        Color(0xFFF5F5F5),
                        CardDefaults.elevatedCardColors().contentColor,
                        CardDefaults.outlinedCardColors().disabledContentColor,
                        CardDefaults.elevatedCardColors().contentColor
                    )
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Greeting()
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(5.dp), RoundedCornerShape(30.dp),
                        colors = CardColors(
                            Color.White,
                            CardDefaults.elevatedCardColors().contentColor,
                            CardDefaults.outlinedCardColors().disabledContentColor,
                            CardDefaults.elevatedCardColors().contentColor
                        )
                    ) {
                        if (viewModel.response.data.overall_url_chart != null) {
                            LineGraph(
                                xData = viewModel.getLineChartData().first,
                                yData = viewModel.getLineChartData().second,
                                dataLabel = "Clicks",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                            )
                        } else {
                            Text(
                                text = "NA",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                            )
                        }
                    }
                    Information()
                    Rectangular_Button(
                        s = "View Analytics",
                        index = 0,
                        color = Color(0xD8D8D8FF),
                        height = 50,
                        imageBitmap = BitmapFactory.decodeResource(LocalContext.current.resources,R.drawable.img_4).asImageBitmap()
                    ){

                    }
                    ShowLinks()
                    Rectangular_Button(
                        s = "Talk with us",
                        index = 1,
                        color = Color(74, 209, 95),
                        height = 60,
                        imageBitmap = BitmapFactory.decodeResource(LocalContext.current.resources,R.drawable.img_6).asImageBitmap()
                    ){}
                    Rectangular_Button(
                        s = "Frequently Asked Questions",
                        index = 2,
                        color = Color(232, 241, 255),height = 60,
                        imageBitmap = BitmapFactory.decodeResource(LocalContext.current.resources,R.drawable.img_7).asImageBitmap()
                    ){}
                }
            }
        }
    }
    @Composable
    fun NavGraoh(navController: NavHostController){
        NavHost(navController = navController, startDestination = Screens.Links.route) {
            composable(Screens.Links.route) { Home_Screen() }
            composable(Screens.Courses.route) { CoursesScreen() }
            composable(Screens.Profile.route) { ProfileScreen() }
            composable(Screens.Campaigns.route) { CampaignsScreen() }
        }
    }
    @Composable
    fun BottomBar(navController : NavHostController) {
        var navigationSelectedItem by remember {
            mutableStateOf(0)
        }
        NavigationBar(modifier = Modifier.height(150.dp), containerColor = Color.White) {
            BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, item ->
                if (index != 2) {
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        label = {
                            Text(item.label, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clipToBounds())
                        },
                        icon = {
                            Icon(
                                item.icon!!,
                                contentDescription = item.label,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(30.dp)
                            )
                        },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(item.route) {
                                Log.i("MYTAG",item.route.toString())
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        modifier = Modifier
                            .wrapContentSize()
                            .clipToBounds()
                        , colors = NavigationBarItemColors(Color.Black,Color.Black,Color.Unspecified,Color.Unspecified,gray,Color.Unspecified,Color.Unspecified)
                    )
                } else {
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        onClick = { /*TODO*/ },
                        enabled = false,
                        icon = {
                            Box(
                                modifier = Modifier
                                    .offset(y = (-20).dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(70.dp)
                                        .padding(7.dp)
                                        .background(Color(14, 111, 255), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Filled.Add, "Home Add", tint = Color.White)
                                }
                            }
                        },
                        modifier =  Modifier.padding(1.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun ShowLinks() {
        var selected by remember {
            mutableStateOf(true)
        }
        var number_ofLinks by remember {
            mutableStateOf(3)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            val context = LocalContext.current
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Button(onClick = {
                        selected = true
                    }, colors = ReturnButtonColor(selected)) {
                        Text(
                            text = "Top Links", color = if (selected) Color.White
                            else gray,
                            fontWeight = FontWeight.Normal, fontSize = 16.sp
                        )
                    }
                    Button(onClick = {
                        selected = false
                    }, colors = ReturnButtonColor(!selected)) {
                        Text(
                            text = "Recent Links", color = if (!selected) Color.White
                            else gray,
                            fontWeight = FontWeight.Normal, fontSize = 14.sp
                        )
                    }
                }
                Icon(
                    BitmapFactory.decodeResource(context.resources, R.drawable.img_9)
                        .asImageBitmap(), "Search", modifier = Modifier
                        .size(36.dp)
                        .border(1.dp, gray, RoundedCornerShape(8.dp)), tint = gray
                )
            }
            if(selected){
                //show Top Links
                val list = viewModel.response.data.top_links
                if(list!=null){
                    for(i in 0..<number_ofLinks){
                        LinkCard(uniqueResponse = list[i])
                    }
                }
            }else{
                //show recent links
                val list = viewModel.response.data.recent_links
                if(list!=null){
                    for(i in 0..<number_ofLinks){
                        LinkCard(uniqueResponse = list[i])
                    }
                }
            }
            if(viewModel.response.data.recent_links!=null) {
                Rectangular_Button(
                    s = "View All Links",
                    index = 0,
                    color = Color(216, 216, 216),
                    height = 50,
                    BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.img_5)
                        .asImageBitmap()
                ) {
                  number_ofLinks= viewModel.response.data.recent_links!!.size
                }
            }
        }
    }

    @Composable
    fun ReturnButtonColor(selected: Boolean): ButtonColors {
        val c = Color(14, 111, 255)
        if (selected)
            return ButtonColors(c, Color.White, Color(0xF5F5F5FF), gray)

        return ButtonColors(Color(0xF5F5F5FF), Color.White, Color(0xF5F5F5FF), gray)
    }

    @Composable
    fun LineGraph(
        xData: List<String>,
        yData: List<Float>,
        dataLabel: String,
        modifier: Modifier
    ) {
        AndroidView(
            modifier = modifier.fillMaxSize(),
            factory = { context ->
                val chart = LineChart(context)
                val xIndices = xData.indices.map { it.toFloat() }
                val entries = xIndices.zip(yData) { x, y -> Entry(x, y) }
                val dataSet = LineDataSet(entries, dataLabel)
                chart.data = LineData(dataSet)
                chart.xAxis.valueFormatter = IndexAxisValueFormatter(xData)
                chart.xAxis.granularity = 1f
                chart.setDrawGridBackground(false)
                chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
                chart.invalidate()
                chart
            }
        )
    }

    @Composable
    fun Information() {
        val scrollState = rememberScrollState()
        LazyRow(
            modifier = Modifier
                .scrollable(scrollState, Orientation.Horizontal)
                .background(Color(0xF5F5F5FF))
                .fillMaxWidth()
                .padding(5.dp)
                .heightIn(0.dp, 250.dp), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            println("response is" + (viewModel.response.data.top_links?.size))
            println("response is" + (viewModel.response.data.recent_links?.size))
            item("Today_clicks") {
                Single_card(
                    data_point = viewModel.response.today_clicks.toString(),
                    data_key = "today_clicks",
                    index = 0
                )
            }
            item {
                Spacer(modifier = Modifier.width(5.dp))
            }
            item("Top_Location") {
                Single_card(
                    data_point = viewModel.response.top_location,
                    data_key = "top_location",
                    index = 1
                )
            }
            item {
                Spacer(modifier = Modifier.width(5.dp))
            }
            item("Top_Source") {
                Single_card(
                    data_point = viewModel.response.top_source,
                    data_key = "top_source",
                    index = 2
                )
            }
        }
    }

    @Composable
    fun Rectangular_Button(
        s: String,
        index: Int,
        color: Color,
        height: Int,
        imageBitmap: ImageBitmap,
        onClick : ()->Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp)
                .padding(10.dp)
                .clickable {
                    onClick()
                }
                .border(1.dp, gray), colors = CardColors(color,
                CardDefaults.elevatedCardColors().contentColor,
                CardDefaults.outlinedCardColors().disabledContentColor,
                CardDefaults.elevatedCardColors().contentColor
            )
        ) {
            if (index == 0) {
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xF5F5F5FF)),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(imageBitmap, "middle", modifier = Modifier.size(32.dp), tint = Color.Black)
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = s,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                    }
                }
            } else {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(viewModel.giveColor(index)), verticalArrangement = Arrangement.Center) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            imageBitmap,
                            "middle",
                            modifier = Modifier.size(20.dp),
                            tint = viewModel.giveColor(index+10)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = s,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun LinkCard(uniqueResponse: RecentLink) {
        Log.i("MYTAG", uniqueResponse.toString())
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(116.dp),
            colors = CardColors(
                Color.White,
                CardDefaults.elevatedCardColors().contentColor,
                CardDefaults.outlinedCardColors().disabledContentColor,
                CardDefaults.elevatedCardColors().contentColor
            )
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.fillMaxHeight()) {
                    Image(
                        myImagetoPainter(imageUrl = uniqueResponse.original_image),
                        "null",
                        modifier = Modifier
                            .size(70.dp)
                    )
                    Column(
                        modifier = Modifier
                            .widthIn(0.dp, 150.dp)
                            .padding(5.dp)
                    ) {
                        Text(
                            text = uniqueResponse.smart_link,
                            color = Color.Black,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp,
                            maxLines = 1,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = viewModel.date(uniqueResponse.created_at),
                            color = Color.Black,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(5.dp)
                ) {
                    Text(
                        text = uniqueResponse.total_clicks.toString(),
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(5.dp)
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(
                        text = "Clicks",
                        color = gray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .border(BorderStroke(Dp.Hairline, Color(166, 199, 255)))
                    .background(Color(232, 241, 255)), verticalArrangement = Arrangement.Center
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = uniqueResponse.web_link,
                        modifier = Modifier
                            .widthIn(0.dp, 200.dp)
                            .padding(5.dp),
                        overflow = TextOverflow.Ellipsis,
                        color = Color(14, 111, 255)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            BitmapFactory.decodeResource(
                                LocalContext.current.resources,
                                R.drawable.img_10
                            ).asImageBitmap(),
                            "null",
                            tint = Color(14, 111, 255),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun myImagetoPainter(imageUrl: String): Painter {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .build()
        )
        return painter
    }

    @Composable
    fun Single_card(data_point: String, data_key: String, index: Int) {
        var s = data_point
        if (data_point.isEmpty()) {
            s = "NA"
        }
        Card(
            modifier = Modifier
                .size(150.dp)
                .padding(5.dp), colors = CardColors(
                Color(android.graphics.Color.WHITE),
                CardDefaults.elevatedCardColors().contentColor,
                CardDefaults.outlinedCardColors().disabledContentColor,
                CardDefaults.elevatedCardColors().contentColor
            )
        ) {
            Column(modifier = Modifier.padding(5.dp)) {
                Spacer(modifier = Modifier.height(10.dp))
                ImageSelector(index = index)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = s,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = viewModel.giveTitle(data_key),
                    color = gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }

    @Composable
    fun ImageSelector(index: Int) {
        val context = LocalContext.current
        var id = 0
        val tint: Color
        if (index == 0) {
            id = R.drawable.img_1
            tint = Color(0xFF6650a4)
        } else if (index == 1) {
            id = R.drawable.img_2
            tint = Color.Blue
        } else {
            id = R.drawable.img_3
            tint = Color.Cyan
        }
        return Icon(
            BitmapFactory.decodeResource(context.resources, id).asImageBitmap(),
            contentDescription = "icon12",
            modifier = Modifier.size(32.dp),
            tint = tint
        )
    }

    @Composable
    fun HideSystemBars() {
        val view = LocalView.current
        ViewCompat.getWindowInsetsController(view)?.let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
        }
    }
    @Composable
    fun Greeting() {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = viewModel.greeting(), color = Color(0xFF999CA0))
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Text(
                    text = "Ajay Manva",
                    color = Color.Black,
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    bitmap = BitmapFactory.decodeResource(
                        LocalContext.current.resources,
                        R.drawable.hi
                    ).asImageBitmap(), contentDescription = "Hi", modifier = Modifier.size(24.dp)
                )
            }
        }
    }

    @Composable
    fun TopBar() {
        Column(
            modifier = Modifier
                .height(100.dp), verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                val context = LocalContext.current
                Column(verticalArrangement = Arrangement.Center) {
                    Spacer(modifier = Modifier.height(9.dp))
                    Text(
                        text = "Dashboard",
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        color = Color.White
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        BitmapFactory.decodeResource(context.resources, R.drawable.img)
                            .asImageBitmap(),
                        contentDescription = "wrench",
                        tint = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }
}
