package com.mozzart.grckikino.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mozzart.grckikino.R
import com.mozzart.grckikino.databinding.ActivityKinoBinding
import com.mozzart.grckikino.global.BaseActivity
import com.mozzart.grckikino.main.data.Kino
import com.mozzart.grckikino.main.ui.KinosAdapter
import com.mozzart.grckikino.main.ui.KinosViewModel
import com.mozzart.grckikino.talon.ui.TalonActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class KinoActivity : BaseActivity(), KinosAdapter.OnKinosItemListener {

    private lateinit var binding: ActivityKinoBinding

    private val viewModel by viewModels<KinosViewModel>()


    var adapter: KinosAdapter = KinosAdapter(ArrayList(), this)

    var kinos = ArrayList<Kino>()


    companion object {
        fun newInit(activity: Activity, kino: Kino) {
            val intent = Intent(activity, KinoActivity::class.java)
            val gson = Gson()
            val json = gson.toJson(kino)
            intent.putExtra("kino", json)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKinoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setStatusBarColor(R.color.dark_background)

        var gson = Gson()

        binding.apply {
            if (isInternetConnected()) {
                viewModel.fetchKinos()
            } else {
                Toast.makeText(baseContext, R.string.message_connection, Toast.LENGTH_LONG).show()
            }

            viewModel.kinoMutableLiveData.observe(this@KinoActivity) { kinosResponse ->
                kinosResponse.let {
                    kinos = kinosResponse
                    adapter.updateList(kinosResponse)
                }
            }
            val layoutManager = LinearLayoutManager(baseContext)
            mainRecyclerview.layoutManager = layoutManager
            mainRecyclerview.adapter = adapter
            mainRecyclerview.addItemDecoration(
                DividerItemDecoration(
                    mainRecyclerview.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun onKinoClicked(position: Int) {
        TalonActivity.newInit(this, kinos[position])
    }
}