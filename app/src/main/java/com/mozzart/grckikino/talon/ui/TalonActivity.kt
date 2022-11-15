package com.mozzart.grckikino.talon.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.mozzart.grckikino.R
import com.mozzart.grckikino.databinding.ActivityTalonBinding
import com.mozzart.grckikino.global.BaseActivity
import com.mozzart.grckikino.main.data.Kino
import com.mozzart.grckikino.talon.data.TalonNumber
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class TalonActivity : BaseActivity(), TalonAdapter.OnTalonItemListener {

    var adapter: TalonAdapter = TalonAdapter(ArrayList(), this)
    var pom = 100
    var counter = 0
    private val numbersRandom: ArrayList<Int> = ArrayList()
    private val numbers: ArrayList<TalonNumber> = ArrayList()

    companion object {
        fun newInit(activity: Activity, kino: Kino) {
            val intent = Intent(activity, TalonActivity::class.java)
            val gson = Gson()
            val json = gson.toJson(kino)
            intent.putExtra("kino", json)
            activity.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityTalonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTalonBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setStatusBarColor(R.color.dark_background)

        val kinoString = intent.getSerializableExtra("kino") as String

        kinoString.replace("^\"|\"$".toRegex(), "")
        val gson = Gson()
        val kino = gson.fromJson(kinoString, Kino::class.java)


        binding.apply {

            textBrojLoptica.text = getString(R.string.broj_izabranih_loptica, 0)

            mainTitleTalon.text = getString(
                R.string.vreme_izvlacenja,
                convertDate(kino.drawTime, "hh:mm"),
                kino.drawID
            )
            object : CountDownTimer(kino.drawTime.toLong() - System.currentTimeMillis(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    preostaloVremeTalon.text = getString(
                        R.string.preostalo_vreme, String.format(
                            "%02d:%02d ",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(
                                        TimeUnit.MILLISECONDS.toMinutes(
                                            millisUntilFinished
                                        )
                                    )
                        )
                    )

                    if (millisUntilFinished / 1000 < 10) {
                        preostaloVremeTalon.setTextColor(resources.getColor(R.color.red))
                    }
                }

                override fun onFinish() {
                    preostaloVremeTalon.text = "00:00"
                }

            }.start()
            for (nums in 1..80) {
                numbers.add(TalonNumber(nums))
            }

            adapter.updateList(numbers)
            talonRecyclerview.layoutManager = GridLayoutManager(baseContext, 10)
            talonRecyclerview.adapter = adapter

            buttonSlucajno.setOnClickListener {
                randomNumbers()
                textBrojLoptica.text = getString(R.string.broj_izabranih_loptica, 8)

            }

            buttonUzivo.setOnClickListener {
                LiveGameActivity.newInit(this@TalonActivity)
            }
        }
    }

    override fun onTalonClicked(position: Int) {
        if (numbers[position].isSelected) {
            numbersRandom.remove(position)
            adapter.clearItem(position)
            counter--
        } else {
            if (counter < 15) {
                if (!numbersRandom.contains(position)) {
                    numbersRandom.add(position)
                }
                counter++
                adapter.updateItem(position)

            } else {
                Toast.makeText(
                    this,
                    "Maximalno polja izabrano, molim Vas prvo ponistite neko polje",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        binding.apply {
            textBrojLoptica.text = getString(R.string.broj_izabranih_loptica, counter)

        }
        calculateKvota()
    }

    private fun randomNumbers() {
        if (numbersRandom.isNotEmpty()) {
            adapter.clearItems(numbersRandom)
            for (nums in 0..79) {
                adapter.clearItem(nums)
            }
            numbersRandom.clear()
            counter = 0
        }
        for (nums in 1..8) {
            var randomNumber = (1..80).random()
            if (numbersRandom.contains(randomNumber)) {
                randomNumber = (1..80).random()
                numbersRandom.add(randomNumber)
                onTalonClicked(randomNumber)
            } else {
                numbersRandom.add(randomNumber)
                onTalonClicked(randomNumber)
            }
        }
    }


    // nisam najponosniji na ovu metodu, trebao sam recyclerview kako bi bilo preglednije ali je ovo za sada bilo najbrze...
    private fun calculateKvota() {
        binding.apply {
            when (counter) {
                0 -> {
                    layoutKvota.horizontalScroll.fullScroll(HorizontalScrollView.FOCUS_LEFT)
                    layoutKvota.one.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.two.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.three.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.four.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.five.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.six.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.seven.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.eight.setTextColor(resources.getColor(R.color.grey))
                }
                1 -> {
                    layoutKvota.horizontalScroll.fullScroll(HorizontalScrollView.FOCUS_LEFT)
                    layoutKvota.one.setTextColor(resources.getColor(R.color.white))
                    layoutKvota.two.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.three.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.four.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.five.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.six.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.seven.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.eight.setTextColor(resources.getColor(R.color.grey))
                }
                2 -> {
                    layoutKvota.horizontalScroll.fullScroll(HorizontalScrollView.FOCUS_LEFT)
                    layoutKvota.one.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.two.setTextColor(resources.getColor(R.color.white))
                    layoutKvota.three.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.four.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.five.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.six.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.seven.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.eight.setTextColor(resources.getColor(R.color.grey))
                }
                3 -> {
                    layoutKvota.one.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.two.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.three.setTextColor(resources.getColor(R.color.white))
                    layoutKvota.four.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.five.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.six.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.seven.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.eight.setTextColor(resources.getColor(R.color.grey))
                }
                4 -> {
                    layoutKvota.one.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.two.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.three.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.four.setTextColor(resources.getColor(R.color.white))
                    layoutKvota.five.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.six.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.seven.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.eight.setTextColor(resources.getColor(R.color.grey))
                }
                5 -> {
                    layoutKvota.one.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.two.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.three.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.four.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.five.setTextColor(resources.getColor(R.color.white))
                    layoutKvota.six.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.seven.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.eight.setTextColor(resources.getColor(R.color.grey))

                }
                6 -> {
                    layoutKvota.one.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.two.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.three.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.four.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.five.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.six.setTextColor(resources.getColor(R.color.white))
                    layoutKvota.seven.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.eight.setTextColor(resources.getColor(R.color.grey))
                }
                7 -> {
                    layoutKvota.one.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.two.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.three.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.four.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.five.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.six.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.seven.setTextColor(resources.getColor(R.color.white))
                    layoutKvota.eight.setTextColor(resources.getColor(R.color.grey))

                }
                8 -> {
                    layoutKvota.horizontalScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
                    layoutKvota.eight.setTextColor(resources.getColor(R.color.white))
                    layoutKvota.one.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.two.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.three.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.four.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.five.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.six.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.seven.setTextColor(resources.getColor(R.color.grey))
                }
                else -> {
                    layoutKvota.one.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.two.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.three.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.four.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.five.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.six.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.seven.setTextColor(resources.getColor(R.color.grey))
                    layoutKvota.eight.setTextColor(resources.getColor(R.color.white))
                }
            }
        }
    }
}