package com.example.openinappassesment
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openinappassesment.Database.Token
import com.example.openinappassesment.Database.TokenRepository
import com.example.openinappassesment.Network.DashboardInterface
import com.example.openinappassesment.Network.DashboardObject
import com.example.openinappassesment.Network.OverallUrlChart
import com.example.openinappassesment.Network.RetrofitInstance
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.Month

class MainActivityViewModel(private val repository: TokenRepository) : ViewModel(){
    var response:DashboardObject = DashboardObject()
    fun ApiService(): MutableLiveData<Boolean>{
        var token =""
        val done = MutableLiveData(false)
        viewModelScope.launch {
            repository.tokens.observeForever{
                println("it:"+ it.token)
                if(it!=null){
                    token= it.token
                    netWorkCall(token).observeForever {
                        if(it){
                            done.value=true
                        }
                    }
                }
            }
        }
    return done
    }
    fun netWorkCall(token: String): MutableLiveData<Boolean>{
       val mutableLiveData = MutableLiveData<Boolean>(false)
       val  dashboardInterface=RetrofitInstance.create(token).create(DashboardInterface::class.java)
       viewModelScope.launch {
           flow {
               emit(dashboardInterface.getDashboard())
           }.onCompletion {
               mutableLiveData.value=true
           }
           .collect{
               response= it.body()!!
           }
       }
    return mutableLiveData
    }
    fun date(date: String): String {
        Log.i("MYTAG", date)
        val s: MutableList<StringBuilder> = mutableListOf(StringBuilder())
        var index = 0
         for (i in date.indices) {
            if (date[i] == 'T') {
                break
            } else if (date[i] != '-') {
                s[index].append(date[i])
            } else {
                index++
                s.add(StringBuilder())
            }
        }
        val date_1 = s[2].toString().toInt()
        val monthNumber = s[1].toString().toInt()
        val monthName = Month.of(monthNumber).name.lowercase().replaceFirstChar { it.uppercase() }
        val dateWhole = StringBuilder()
        dateWhole.append(date_1)
        dateWhole.append(" ")
        dateWhole.append(monthName)
        dateWhole.append(" ")
        dateWhole.append(s[0])

        return dateWhole.toString()
    }
    fun greeting() : String{
        val time = LocalDateTime.now()
        var s: String = ""
        if(time.hour<12){
            s="Good Morning"
        }else if(time.hour in 12..16){
            s="Good Afternoon"
        }else if(time.hour in 17..20){
            s="Good Evening"
        }else{
            s="Good Day"
        }
    return s
    }
    fun insertToken(s : String){
        val token = Token(1,s)
         viewModelScope.launch {
             flow {
                 emit(repository.insertToken(token))
             }.onCompletion {
                 Log.i("MYTAG","inserted Successfully")
             }.catch {
                     e->
                 e.printStackTrace()
             }.collect()
         }
    }
    fun giveTitle(title: String) : String{
       println("title is: "+title)
       if(title.equals("today_clicks"))
           return "Today's Clicks"
       val sb : StringBuilder = StringBuilder()
       var h=0
       for(i in title.indices){
           if(h==i){
               val ascii=title[i].code //97 to 122
               val f=(ascii-32)
               sb.append(f.toChar())
           }else {
               if(title[i]!='_') {
                   sb.append(title[i])
               }else{
                   sb.append(' ')
                   h=i+1
               }
           }
           }
       return sb.toString()
    }
    fun getLineChartData() : Pair<List<String>,List<Float>>{
        val h : MutableList<Float> = ArrayList()
        val p = response.data.overall_url_chart
        val l = p?.values?.toMutableList()
        if (l != null) {
            for(i in 0..<l.size){
                h.add((l[i].toFloat()))
            }
        }
        println(h.toString())
        return Pair(p!!.keys.toList(),h.toList())
    }
    fun giveColor(index: Int) : androidx.compose.ui.graphics.Color {
        if(index==0)
            return Color(216, 216, 216)
        if(index==1)
            return Color(0xEEB3F5BB)
        if(index==11)
            return Color.Green
        if(index==12)
            return Color.Blue
        return Color(91, 121, 167, 255)
    }
}