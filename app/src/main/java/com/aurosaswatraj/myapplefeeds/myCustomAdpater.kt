package com.aurosaswatraj.myapplefeeds

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ViewHolder(view:View)
{
    val title=view.findViewById<TextView>(R.id.title)
    val category=view.findViewById<TextView>(R.id.category)
    val description=view.findViewById<TextView>(R.id.Description)
    val pubDate=view.findViewById<TextView>(R.id.pubDate)
}


class myCustomAdpater(context:Context,private val resourse:Int,private val application:List<FeedEntry>):ArrayAdapter<FeedEntry>(context,resourse) {

    private var inflator=LayoutInflater.from(context)


    override fun getCount(): Int {
        return application.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view:View
        var viewHolder:ViewHolder

        if (convertView==null)
        {
            view=inflator.inflate(R.layout.list_items,parent,false)
            viewHolder=ViewHolder(view)
            view.tag=viewHolder
        }
        else
        {
            view=convertView
            viewHolder=view.tag as ViewHolder
        }

        viewHolder.title.text=application[position].title
        viewHolder.category.text="Category:"+application[position].category
        viewHolder.description.text="Description:"+application[position].description
        viewHolder.pubDate.text="Publisher Date:"+application[position].pubdate

        return view
    }

}