package com.mozzart.grckikino.talon.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mozzart.grckikino.databinding.ItemTalonNumberBinding
import com.mozzart.grckikino.main.data.Kino
import com.mozzart.grckikino.talon.data.TalonNumber

class TalonAdapter(val numbers: ArrayList<TalonNumber>, val listener: OnTalonItemListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context

    var counter = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val binding =
            ItemTalonNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TalonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val number = numbers[position]
        if (holder is TalonViewHolder) {
            holder.bind(number)
        }
    }

    override fun getItemCount(): Int {
        return numbers.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun clearItems(positions: ArrayList<Int>) {
        for (position in positions) {
            numbers[position].isSelected = false
            notifyDataSetChanged()
        }
    }

    fun clearAll(positions: ArrayList<Int>) {
        for (position in positions) {
            numbers[position].isSelected = false
            notifyDataSetChanged()
        }
    }

    fun clearItem(position: Int) {
        numbers[position].isSelected = false
        notifyDataSetChanged()
    }

    fun updateList(numbers: ArrayList<TalonNumber>) {
        this.numbers.clear()
        this.numbers.addAll(numbers)
        notifyDataSetChanged()
    }

    fun updateItem(position: Int) {
        numbers[position].isSelected = true
        notifyDataSetChanged()
    }

    inner class TalonViewHolder(val binding: ItemTalonNumberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(number: TalonNumber) {
            binding.apply {
                itemNumber.text = number.number.toString()
                if (number.isSelected) {
                    circleNumber.visibility = View.VISIBLE
                } else {
                    circleNumber.visibility = View.GONE
                }
                container.setOnClickListener {
                    listener.onTalonClicked(currentPosition(bindingAdapterPosition))
                }
            }
        }
    }

    fun currentPosition(position: Int): Int = position

    interface OnTalonItemListener {
        fun onTalonClicked(position: Int)
    }
}