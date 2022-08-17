package com.example.loanamortizator

import android.content.Entity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LoanAdapter(val contentList: MutableList<Variables> ): RecyclerView.Adapter<LoanAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val monthTextView: TextView
        val paymentTextView: TextView
        val interestTextView: TextView
        val principalTextView: TextView
        val balanceTextView: TextView
        init {
            monthTextView = view.findViewById(R.id.monthTextView)
            balanceTextView = view.findViewById(R.id.balanceTextView)
            paymentTextView = view.findViewById(R.id.paymentTextView)
            interestTextView = view.findViewById(R.id.interestTextView)
            principalTextView = view.findViewById(R.id.principalTextView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.monthTextView.text = contentList.get(position).muaji
        holder.paymentTextView.text = contentList.get(position).pagesa.toString()
        holder.interestTextView.text = contentList.get(position).interesi.toString()
        holder.principalTextView.text = contentList.get(position).principal.toString()
        holder.balanceTextView.text = contentList.get(position).bilanci.toString()
    }

        override fun getItemCount(): Int = contentList.size
}