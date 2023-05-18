package devsec.app.rhinhorealestates.ui.main.view

import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import devsec.app.RhinhoRealEstates.R
import devsec.app.rhinhorealestates.data.api.MessageResponse
import devsec.app.rhinhorealestates.utils.session.SessionPref

class MessageAdapter(context: Context, messages: MutableList<MessageResponse>, private val username: String) :
    ArrayAdapter<MessageResponse>(context, R.layout.message_item, messages) {
    private class ViewHolder {
        lateinit var messageContainer: LinearLayout
        lateinit var fromTextView: TextView
        lateinit var textTextView: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        val viewHolder: ViewHolder
        if (rowView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            rowView = inflater.inflate(R.layout.message_item, parent, false)
            viewHolder = ViewHolder()
            viewHolder.fromTextView = rowView.findViewById(R.id.from_text_view)
            viewHolder.textTextView = rowView.findViewById(R.id.text_text_view)
            viewHolder.messageContainer = rowView.findViewById(R.id.message_container)
            rowView.tag = viewHolder
        } else {
            viewHolder = rowView.tag as ViewHolder
        }

        val message = getItem(position)
        if (message != null) {
            viewHolder.textTextView.text =
                message.text // Update textTextView instead of messageTextView
            viewHolder.fromTextView.text = message.from
            if (message.from == username) {
                viewHolder.messageContainer.gravity = Gravity.END
                viewHolder.textTextView.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.black,
                    )
                )
            // Update textTextView instead of messageTextView
            } else {
                viewHolder.messageContainer.gravity = Gravity.START
                viewHolder.textTextView.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                ) // Update textTextView instead of messageTextView
            }
        }
        return rowView!!
    }
}




