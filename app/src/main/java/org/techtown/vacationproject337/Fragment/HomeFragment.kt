package org.techtown.vacationproject337.Fragment

import android.content.Context
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.UiThread
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import org.techtown.vacationproject337.MainActivity
import org.techtown.vacationproject337.MainActivity2
import org.techtown.vacationproject337.R
import org.techtown.vacationproject337.databinding.FragmentHomeBinding
import java.util.*
import kotlin.concurrent.timer

class HomeFragment : Fragment() {
    private var time = 0
    private var timerTask : Timer? = null
    lateinit var mainActivity2: MainActivity2

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity2 = context as MainActivity2
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentHomeBinding.inflate(inflater,container,false)

        binding.startBtnMain.setOnClickListener{
            binding.startBtnMain.visibility = View.GONE
            binding.endBtnMain.visibility = View.VISIBLE
            startTimer()
        }
        binding.endBtnMain.setOnClickListener {
            binding.startBtnMain.visibility = View.VISIBLE
            binding.endBtnMain.visibility = View.GONE
        }
        return inflater.inflate(R.layout.fragment_about, container, false)

    }

    private fun startTimer() {
        timerTask = timer(period = 10){
            time++
            val sec = time/100
            val milli = time%100


        }

    }
}