package br.ufpe.cin.android.podcast

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.itemlista.view.*


class ItemFeedAdapter (
    private val itemFeeds: List<ItemFeed>, private val c: Context) : RecyclerView.Adapter<ItemFeedAdapter.ViewHolder>() {

    override fun getItemCount(): Int = itemFeeds.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.itemlista, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemFeed = itemFeeds[position]
        holder.title?.text = itemFeed.title
        holder.date?.text = itemFeed.pubDate

        holder.title.setOnClickListener{
            val intent = Intent(c, EpisodeDetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("title",itemFeed.title)
            intent.putExtra("description",itemFeed.description)
            intent.putExtra("pubDate",itemFeed.pubDate)

            c.startActivity(intent)
        }
    }

    class ViewHolder (itemFeed : View) : RecyclerView.ViewHolder(itemFeed) {
        val title = itemFeed.item_title
        val date = itemFeed.item_date
        val action_download = itemFeed.item_action

        init {
            action_download.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    "Download ${title.text} in progress!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}