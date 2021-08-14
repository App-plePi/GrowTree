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
    private var tree_kind:Int = 0
    private var tree_level:Int = 0
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
                binding.textUserTreeMain.text = "${it.value.toString()}의\n나무 키우기"
            }

        treeNu(tree_kind,tree_level)

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
        timerTask = timer(period = 1){
            time--
            var sec = time/100
            var min = time/6000
            var hour = time/360000
            var milli = time % 100

            if (hour == 99) {time == 0}
            if (min >= 60) { min -= 60*hour}
            if (sec >= 60) { sec -= 60*min }
            if (time == 0) {
                tree_level++
                if (tree_level == 5){
                    tree_level = 0
                    tree_kind++
                }
                if (tree_kind == 5){
                    tree_kind = 0 // 첫번째 나무로 돌아오기
                }
                tree_time(tree_level)
                treeNu(tree_kind,tree_level)
                Log.d(TAG, "time = ${time} ${sec}이다")
            }
            requireActivity().runOnUiThread {
                binding.timerMain.text = "%02d : %02d : %02d".format(hour,min,sec)
                binding.timerMilli.text = "${milli}"
                binding.progressCircleMain.progress = time1 - time

            }

        }

    }

    private fun stopTimer(){
        timerTask?.cancel()
    }

    private fun tree_time(trLevel: Int){
        when (tree_level){
            0 -> {time = 360000
            time1 = 360000}
            1 -> {time = 1080000
            time1 = 1080000}
            2 -> {time = 1800000
            time1 = 1800000}
            3 -> {time = 2520000
            time1 = 2520000}
        }
    }

    private fun treeNu(tr_kind:Int,tr_level:Int){

        val trKind = tr_kind
        val trLevel = tr_level
        val trImg = binding.progressImage

        when (trKind){
            0 -> when(trLevel) {
                0 -> trImg.setImageResource(R.drawable.profile1)
                1 -> trImg.setImageResource(R.drawable.profile2)
                2 -> trImg.setImageResource(R.drawable.profile3)
                3 -> trImg.setImageResource(R.drawable.profile4)
                4 -> trImg.setImageResource(R.drawable.profile5)
            }
            1 -> when(trLevel) {
                0 -> trImg.setImageResource(R.drawable.profile1)
                1 -> trImg.setImageResource(R.drawable.profile2)
                2 -> trImg.setImageResource(R.drawable.profile3)
                3 -> trImg.setImageResource(R.drawable.profile4)
                4 -> trImg.setImageResource(R.drawable.profile5)
            }
            2 -> when(trLevel) {
                0 -> trImg.setImageResource(R.drawable.profile1)
                1 -> trImg.setImageResource(R.drawable.profile2)
                2 -> trImg.setImageResource(R.drawable.profile3)
                3 -> trImg.setImageResource(R.drawable.profile4)
                4 -> trImg.setImageResource(R.drawable.profile5)
            }
            3 -> when(trLevel) {
                0 -> trImg.setImageResource(R.drawable.profile1)
                1 -> trImg.setImageResource(R.drawable.profile2)
                2 -> trImg.setImageResource(R.drawable.profile3)
                3 -> trImg.setImageResource(R.drawable.profile4)
                4 -> trImg.setImageResource(R.drawable.profile5)
            }
            4 -> when(trLevel) {
                0 -> trImg.setImageResource(R.drawable.profile1)
                1 -> trImg.setImageResource(R.drawable.profile2)
                2 -> trImg.setImageResource(R.drawable.profile3)
                3 -> trImg.setImageResource(R.drawable.profile4)
                4 -> trImg.setImageResource(R.drawable.profile5)
            }
        }


    }

}