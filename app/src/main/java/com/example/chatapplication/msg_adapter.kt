package com.example.chatapplication

import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import java.util.ArrayList

class msg_adapter(val context : Context, val messageList : ArrayList<message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val ITEM_RECEIVED = 1
    val ITEM_SENT = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

    if(viewType==1)
    {
        //inflate received
        val view: View = LayoutInflater.from(context).inflate(R.layout.received,parent,false)
        return ReceiveViewHolder(view)
    }
        else
    {
        //inflate send
        val view: View = LayoutInflater.from(context).inflate(R.layout.send,parent,false)
        return SentViewHolder(view)
    }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentmsg = messageList[position]
        if(holder.javaClass==SentViewHolder::class.java)
        {

            //do the stuff for sent view holder
            val viewHolder = holder as SentViewHolder
            holder.sentmessage.text=currentmsg.message
        }

        else
        {
            //do stuff for receive view holder
            val viewHolder = holder as ReceiveViewHolder
            holder.recmessage.text=currentmsg.message


        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentmsg= messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentmsg.senderId))
        {
            return ITEM_SENT;
        }

        else
        {
            return ITEM_RECEIVED;
        }
    }
    override fun getItemCount(): Int {

        return messageList.size

    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val sentmessage = itemView.findViewById<TextView>(R.id.txt_sent_msg)

    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val recmessage = itemView.findViewById<TextView>(R.id.txt_rcv_msg)

    }

}