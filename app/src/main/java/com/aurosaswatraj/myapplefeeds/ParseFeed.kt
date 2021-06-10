package com.aurosaswatraj.myapplefeeds

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.lang.Exception

class ParseFeed {

    var Tag:String="ParseFeed"
    var application=ArrayList<FeedEntry>()



    fun parseApplication(xmlData:String):Boolean
    {
        var status:Boolean=true
        try {
            var TextValue:String=""
            var inENtry:Boolean=false
            var factory=XmlPullParserFactory.newInstance()
            factory.isNamespaceAware=true
            var xpp=factory.newPullParser()
            xpp.setInput(xmlData.reader())
            var eventType=xpp.eventType
            var currentRecord=FeedEntry()
            //Parse till the end of the document
            while (eventType!=XmlPullParser.END_DOCUMENT)
            {


                val tagname=xpp.name?.lowercase()
                when(eventType)
                {
                    XmlPullParser.START_TAG->{
                        Log.d(Tag,"The starting Tag $tagname")
                        if (tagname=="item")
                        {
                            inENtry=true
                        }
                    }
                    XmlPullParser.TEXT->{TextValue=xpp.text}
                    XmlPullParser.END_TAG->{
                        Log.d(Tag,"ENding Tag is :-$tagname")
                        if (inENtry)
                        {
                            when(tagname)
                            {
                                "item"->{
                                    application.add(currentRecord)
                                    inENtry=false
                                    currentRecord= FeedEntry()
                                }

                                "category"->currentRecord.category=TextValue
                                "description"->currentRecord.description=TextValue
                                "pubdate"->currentRecord.pubdate=TextValue
                                "title"->currentRecord.title=TextValue


                            }
                        }
                    }

                }
                eventType=xpp.next()
            }
        }
        catch (e:Exception)
        {
            e.printStackTrace()
            status=false
        }

//        To display items in logcat...
            for (i in application)
            {
                Log.d(Tag,"******************")
                Log.d(Tag,i.toString())
            }

        return status
    }
}