package com.aurosaswatraj.myapplefeeds

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.net.URL

import kotlinx.android.synthetic.main.activity_main.*

class FeedEntry
{
    var title:String=""
    var category:String=""
    var description:String=""
    var pubdate:String=""

    override fun toString(): String {
        return """
                
                Title:$title
                Description:$description
                Category:$category
                Publisher Date:$pubdate
                
        """.trimIndent()
    }

}

class MainActivity : AppCompatActivity() {

    var displayfeed:DownloadBackground?=null
    val STATE_URL:String="StateUrl"
    var feedURL:String="https://rss.itunes.apple.com/api/v1/us/books/top-free/all/100/explicit.rss"

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#161616")))
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        displayfeed=DownloadBackground(this,xmlListView)
        if(savedInstanceState!=null)
        {
            feedURL=savedInstanceState.getString(STATE_URL).toString()
        }
        displayfeed?.execute(feedURL)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_URL,feedURL)
    }


    override fun onDestroy() {
        super.onDestroy()
        displayfeed?.cancel(true)
    }

    companion object{
        class DownloadBackground(context:Context,listView:ListView) : AsyncTask<String,Void,String>()
        {
            lateinit var propContext:Context
            lateinit var proplistView: ListView

            init {
                propContext=context
                proplistView=listView
            }

            val Tag:String="DownloadBackground"
            override fun doInBackground(vararg params: String?): String {
                val rssFeed=downloadXML(params[0])
                if (rssFeed.isEmpty())
                {
                    Log.e(Tag,"do in background:Error in downloading")
                }
                return rssFeed
            }

            private fun downloadXML(UrlPath:String?):String {
                return URL(UrlPath).readText()

            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                var parsedData=ParseFeed()
                parsedData.parseApplication(result)

                val customAdpater=myCustomAdpater(propContext,R.layout.list_items,parsedData.application)
                proplistView.adapter=customAdpater

            }
        }
    }

}