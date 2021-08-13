package org.techtown.vacationproject337.Fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.techtown.vacationproject337.Dbuser
import org.techtown.vacationproject337.R
import org.techtown.vacationproject337.databinding.FragmentHomeBinding
import java.util.*
import kotlin.concurrent.timer

class HomeFragment : Fragment() {

    private var time = 360000
    private var time1:Int = time
    private var timerTask : Timer? = null
    private var treeN = 0
    private var time_dou:Double = 0.0
    private var time1_dou:Double = 0.0
    private var t_dou:Double = 0.0
    private lateinit var firebaseDb:FirebaseDatabase
    private lateinit var auth:FirebaseAuth
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(inflater,container,false)
        val v: View = binding.getRoot()
        firebaseDb = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid:String = auth.uid.toString()
        
        firebaseDb.reference.child("User").child(uid).child("name").get()
            .addOnSuccessListener { Log.d(TAG, "onCreateView: ${it.value}")
                binding.textUserTreeMain.text = "${it.value.toString()}의\n나무 키우기"// 사랑합니다
            }


        binding.progressCircleMain.progress = 0 // 초단위
        binding.progressCircleMain.max = 360000

        binding.startBtnMain.setOnClickListener{
            binding.startBtnMain.visibility = View.GONE
            binding.endBtnMain.visibility = View.VISIBLE
            startTimer(0,0)
            Log.d(TAG, "onCreateView: 성공은 실패의 어머니")
        }
        binding.endBtnMain.setOnClickListener {
            binding.startBtnMain.visibility = View.VISIBLE
            binding.endBtnMain.visibility = View.GONE
            stopTimer()
        }
        return v
    }



    private fun startTimer(treeName:Int,treeNum:Int) { //treeName : 종류 , treeNum : 단계
        timerTask = timer(period = 10){
            time--
            var sec = time/100
            var min = time/6000
            var hour = time/360000
            var milli = time % 100
            if (hour == 99) {time == 0}
            if (min >= 60) { min -= 60*hour}
            if (sec >= 60) { sec -= 60*min }
            requireActivity().runOnUiThread { //
                binding.timerMain.text = "%02d : %02d : %02d".format(hour,min,sec)
                binding.timerMilli.text = "${milli}"
                binding.progressCircleMain.progress = time1 - time

            }

        }

    }

    private fun stopTimer(){
        timerTask?.cancel()
    }

    private fun treeNu(){
        treeN++
        if (treeN == 5) {treeN -= 5}
        val trImg = view?.findViewById<ImageView>(R.id.progress_Image)
        when (treeN){
            1 -> trImg?.setImageResource(R.drawable.profile1) // 나무로 교체
            2 -> trImg?.setImageResource(R.drawable.profile2)
            3 -> trImg?.setImageResource(R.drawable.profile3)
            4 -> trImg?.setImageResource(R.drawable.profile4)
        }


    }

}