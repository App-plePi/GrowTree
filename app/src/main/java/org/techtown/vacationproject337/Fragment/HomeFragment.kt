package org.techtown.vacationproject337.Fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.techtown.vacationproject337.R
import org.techtown.vacationproject337.databinding.FragmentHomeBinding
import java.util.*
import kotlin.concurrent.timer

class HomeFragment : Fragment() {
    private var time = 0
    private var timerTask : Timer? = null
    /*lateinit var mainActivity2: MainActivity2

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity2 = context as MainActivity2
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentHomeBinding.inflate(inflater,container,false)
        val v: View = binding.getRoot()

        binding.progressCircleMain.progress = 0 // 초단위
        binding.progressCircleMain.max = 200

        binding.startBtnMain.setOnClickListener{
            binding.startBtnMain.visibility = View.GONE
            binding.endBtnMain.visibility = View.VISIBLE
            Log.d(TAG, "onCreateView: 성공은 실패의 어머니")
        }
        binding.endBtnMain.setOnClickListener {
            binding.startBtnMain.visibility = View.VISIBLE
            binding.endBtnMain.visibility = View.GONE
        }
        return v

    }

    private fun startTimer() {
        timerTask = timer(period = 10){
            time++
            val sec = time/100
            val milli = time%100


        }

    }

}