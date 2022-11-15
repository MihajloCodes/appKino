package com.mozzart.grckikino.main.ui

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mozzart.grckikino.R
import com.mozzart.grckikino.databinding.ItemMainKinosBinding
import com.mozzart.grckikino.main.KinoActivity
import com.mozzart.grckikino.main.data.Kino
import java.util.concurrent.TimeUnit


class KinosAdapter(val kinos: ArrayList<Kino>, val listener: OnKinosItemListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val binding =
            ItemMainKinosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KinoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val kino = kinos[position]
        if (holder is KinoViewHolder) {
            holder.bind(kino)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return kinos.size
    }

    fun updateList(kinos: ArrayList<Kino>) {
        this.kinos.clear()
        this.kinos.addAll(kinos)
        notifyDataSetChanged()
    }

    inner class KinoViewHolder(val binding: ItemMainKinosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(kino: Kino) {
            binding.apply {

                itemScheduleTime.text =
                    (context as KinoActivity).convertDate(kino.drawTime, "hh:mm")

                object : CountDownTimer(kino.drawTime.toLong() - System.currentTimeMillis(), 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        itemLeftTime.text = String.format(
                            "%02d:%02d ",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(
                                        TimeUnit.MILLISECONDS.toMinutes(
                                            millisUntilFinished
                                        )
                                    )
                        )

                        if (millisUntilFinished / 1000 < 10) {
                            itemLeftTime.setTextColor(context.resources.getColor(R.color.red))
                        }
                    }

                    override fun onFinish() {
                        itemLeftTime.text = "00:00"
                    }
                }.start()
                itemLeftTime.text = System.currentTimeMillis().toString()
                container.setOnClickListener {
                    listener.onKinoClicked(currentPosition(bindingAdapterPosition))
                }
            }
        }
    }


    fun currentPosition(position: Int): Int = position

    interface OnKinosItemListener {
        fun onKinoClicked(position: Int)
    }
}