package org.techtown.vacationproject337.Fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private var time = 0
    private var timerTask : Timer? = null
    private lateinit var firebaseDb:FirebaseDatabase
    private lateinit var auth:FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentHomeBinding.inflate(inflater,container,false)
        val v: View = binding.getRoot()
        firebaseDb = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid:String = auth.uid.toString()

        
        firebaseDb.reference.child("User").child(uid).child("name").get()
            .addOnSuccessListener { Log.d(TAG, "onCreateView: ${it.value}")
                binding.textUserTreeMain.text = "${it.value.toString()}의\n나무 키우기"// 사랑합니다
            }


        binding.progressCircleMain.progress = 0 // 초단위
        binding.progressCircleMain.max = 100

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
            val treeName:Int = treeName
            val treeNum:Int = treeNum
            time--
            val sec = time/100
            val milli = time%100
            if(time == 0){
                changeImage()
            }
            requireActivity().runOnUiThread { //

            }

        }

    }

    private fun stopTimer(){
        timerTask?.cancel()
    }

    private fun changeImage(){

    }

}