package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox : EditText
    private lateinit var sendButton : ImageView
    private lateinit var messageAdapter: msg_adapter
    private lateinit var messageList: ArrayList<message>
    private lateinit var mDBRef: DatabaseReference

    // create unique room for the pair of sender and receiver
    var receiverRoom : String ? = null
    var senderRoom : String ? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val name = intent.getStringExtra("name")
        val receiveruid = intent.getStringExtra("uid")

        val senderuid = FirebaseAuth.getInstance().currentUser?.uid

        mDBRef = FirebaseDatabase.getInstance().getReference()

        senderRoom=receiveruid + senderuid
        receiverRoom = senderuid+receiveruid

        supportActionBar?.title =name

        chatRecyclerView=findViewById(R.id.chat_recyclerview)
        messageBox=findViewById(R.id.messageBox)
        sendButton=findViewById(R.id.sendButton)
        messageList= ArrayList()
        messageAdapter= msg_adapter(this,messageList)


        chatRecyclerView.layoutManager=LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdapter

        //logic for adding data to recycler view
        mDBRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()
                    for (postSnapshot in snapshot.children)
                    {
                        val message = postSnapshot.getValue(message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        sendButton.setOnClickListener{

            //adding tye message to database
            val message = messageBox.text.toString()
            val messageObject = message(message,senderuid)
            mDBRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDBRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)

                }
            messageBox.setText("")
        }




    }
}