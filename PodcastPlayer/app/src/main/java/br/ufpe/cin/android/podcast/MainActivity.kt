package br.ufpe.cin.android.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list_item_feed.layoutManager = LinearLayoutManager(this)
        list_item_feed.adapter = ItemFeedAdapter(listOf(), this)

        doAsync {
            val url = "https://s3-us-west-1.amazonaws.com/podcasts.thepolyglotdeveloper.com/podcast.xml?fbclid=IwAR3X1VxOU4OFdxG-2m0IKHLwDXHFRavdx1ZndZ1T53OLRQk_XQlE168N1bI"
            var listItemFeeds: List<ItemFeed> = emptyList()

            try {
                val podcast = URL(url).readText()
                listItemFeeds = Parser.parse(podcast)

                uiThread {
                    list_item_feed.adapter = ItemFeedAdapter(listItemFeeds, applicationContext)
                }

                val db = ItemFeedDB.getDatabase(applicationContext)
                db.itemFeedDAO().insertListItemFeeds(listItemFeeds)

            } catch (error:Throwable) {
                Log.e("ERROR",error.message.toString())
            }

        }
    }
}
