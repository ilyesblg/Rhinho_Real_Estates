package devsec.app.rhinhorealestates.ui.main.adapter

import devsec.app.RhinhoRealEstates.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso


class ImagePagerAdapter(private val context: Context, private val imageUrls: List<String>) : PagerAdapter() {
    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        Picasso.get()
            .load(imageUrls[position])
            .into(imageView)

        container.addView(imageView)
        return imageView
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
