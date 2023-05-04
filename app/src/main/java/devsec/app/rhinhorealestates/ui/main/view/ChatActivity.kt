package devsec.app.rhinhorealestates.ui.main.view

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import devsec.app.RhinhoRealEstates.R

class ChatActivity : AppCompatActivity() {

    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button

    private lateinit var socket: Socket
    private var username: String? = null
    private var toUsername: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_activity)

        messageEditText = findViewById(R.id.message_input)
        sendButton = findViewById(R.id.send_button)

        val intent = intent
        username = intent.getStringExtra("username")
        toUsername = intent.getStringExtra("toUsername")

        // Establishing socket connection
        val options = IO.Options()
        options.forceNew = true
        socket = IO.socket("http://localhost:9090/api/message/", options)
        socket.connect()

        socket.on(Socket.EVENT_CONNECT, onConnect)
        socket.on(Socket.EVENT_DISCONNECT, onDisconnect)
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
        socket.on("message", onIncomingMessage)

        sendButton.setOnClickListener {
            val messageText = messageEditText.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val data = mapOf(
                    "from" to username,
                    "to" to toUsername,
                    "text" to messageText
                )
                socket.emit("message", data)
                messageEditText.setText("")
            }
        }
    }

    private val onConnect = Emitter.Listener {
        Log.d("ChatActivity", "Socket connected")
    }

    private val onDisconnect = Emitter.Listener {
        Log.d("ChatActivity", "Socket disconnected")
    }

    private val onConnectError = Emitter.Listener {
        Log.d("ChatActivity", "Socket connection error")
    }

    private val onIncomingMessage = Emitter.Listener { args ->
        runOnUiThread {
            val data = args[0] as Map<*, *>
            val from = data["from"] as String
            val text = data["text"] as String
            if (from == toUsername) {
                // Display the message in UI
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }
}
