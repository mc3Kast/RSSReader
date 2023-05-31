package com.example.rssreader.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rssreader.R
import com.example.rssreader.WebActivity
import com.example.rssreader.model.RSSObject
import com.example.rssreader.myInterface.ItemClickListener

class FeedAdapter(private val rssObject: RSSObject, private val mContext: Context):RecyclerView.Adapter<FeedViewHolder>() {

    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.row, parent, false)
        return FeedViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return rssObject.items.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.txtTitle.text = rssObject.items[position].title
        holder.txtContext.text = rssObject.items[position].content
        holder.txtPubdate.text = rssObject.items[position].pubDate

        holder.setItemClickListener(ItemClickListener{ view, position, isLongClick ->

            if (!isLongClick){

                val i = Intent(mContext, WebActivity::class.java)
                i.putExtra("link", rssObject.items[position].link);
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                mContext.startActivity(i)
                //val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.items[position].link))
                //mContext.startActivity(browserIntent)
            }
        })
    }

}

class FeedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener{

    var txtTitle : TextView
    var txtPubdate : TextView
    var txtContext : TextView

    private var itemClickListener : ItemClickListener?=null

    init {
        txtTitle = itemView.findViewById(R.id.txtTitle) as TextView
        txtPubdate = itemView.findViewById(R.id.txtPubdate) as TextView
        txtContext = itemView.findViewById(R.id.txtContent) as TextView

        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onClick(p0: View?) {
        itemClickListener!!.onClick(p0, adapterPosition, false)
    }

    override fun onLongClick(p0: View?): Boolean {
        itemClickListener!!.onClick(p0, adapterPosition, true)
        return true
    }

}