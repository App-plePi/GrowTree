package org.techtown.vacationproject337.Fragment

import android.app.AlertDialog
import android.content.ClipData
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.techtown.vacationproject337.Dbuser
import org.techtown.vacationproject337.R
import org.techtown.vacationproject337.databinding.ActivityMain2Binding
import org.techtown.vacationproject337.databinding.FragmentHomeBinding
import java.util.*
import kotlin.concurrent.timer

class HomeFragment : Fragment() {

    private var time = 360000
    private var time1:Int = time
    private var timerTask : Timer? = null
    private var tree_kind:Int = 0
    private var tree_level:Int = 0
    private var count:Int = 0
    private var studyTime:Int = 0
    private var studymin:Int = 0
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

        binding.progressCircleMain.progress = 0 // 시작 원형프그바 = 0
        //binding.progressCircleMain.max = 360000




        firebaseDb.reference.child("User").child(uid).child("treeKind").get()
            .addOnSuccessListener {
                val a = "${it.value.toString()}"
                tree_kind = a.toInt()
                //Log.d(TAG, "onCreateView: ${tree_kind} kink")
            }
        firebaseDb.reference.child("User").child(uid).child("studyTime").get()
            .addOnSuccessListener {
                var a = "${it.value.toString()}"
                studyTime = a.toInt()
                //Log.d(TAG, "onCreateView: ${studyTime} st타임1")
                //Log.d(TAG, "onCreateView: ${studyTime} st타임2")
            }
        firebaseDb.reference.child("User").child(uid).child("count").get()
            .addOnSuccessListener {
                var a = "${it.value.toString()}"
                count = a.toInt()
            }
        firebaseDb.reference.child("User").child(uid).child("time").get()
            .addOnSuccessListener {
                var a = "${it.value.toString()}"
                time = a.toInt()
                //Log.d(TAG, "onCreateView: ${time} 타임")
            }
        firebaseDb.reference.child("User").child(uid).child("treeLevel").get()
            .addOnSuccessListener {
                val a = "${it.value.toString()}"
                tree_level = a.toInt()
                //Log.d(TAG, "onCreateView: ${tree_level} level  ${binding.progressCircleMain.max} max")
                treeNu(tree_kind,tree_level)
                tree_progess()
                //Log.d(TAG, "${binding.progressCircleMain.max} max2")

            }.addOnFailureListener { Toast.makeText(requireActivity(),"문제",Toast.LENGTH_SHORT).show() }


        Toast.makeText(requireActivity(),"타이머를 작동시켜주세요",Toast.LENGTH_SHORT).show()


        treeNu(tree_kind,tree_level) // 이미지 세팅 초기,

        binding.startBtnMain.setOnClickListener{
            binding.startBtnMain.visibility = View.GONE
            binding.endBtnMain.visibility = View.VISIBLE
            startTimer()
        }
        binding.endBtnMain.setOnClickListener {
            binding.startBtnMain.visibility = View.VISIBLE
            binding.endBtnMain.visibility = View.GONE
            stopTimer()
        }

        return v
    }

    override fun onStop() {
        super.onStop()
        firebaseDb = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid:String = auth.uid.toString()
        tree_min(tree_level)
        studyTime = ((time1 - time)/6000) + (960 * count + studymin)
        firebaseDb.reference.child("User").child(uid).child("time").setValue(time)
        firebaseDb.reference.child("User").child(uid).child("treeKind").setValue(tree_kind)
        firebaseDb.reference.child("User").child(uid).child("treeLevel").setValue(tree_level)
        firebaseDb.reference.child("User").child(uid).child("count").setValue(count)//만든 나무 수
        firebaseDb.reference.child("User").child(uid).child("studyTime").setValue(studyTime)//공부한 시간
        stopTimer()
    }



    private fun startTimer() { //treeName : 종류 , treeNum : 단계

        timerTask = timer(period = 1){
            time--


            var sec = time/100 // 초
            var min = time/6000 // 분
            var hour = time/360000 // 시간
            var milli = time % 100 // 밀리초


            if (sec >= 60) { sec -= 60*min }
            if (min >= 60) { min -= 60*hour}
            if (hour == 99) {time == 0}
            if (time == 0) {
                tree_level++


                if (tree_level == 4){
                    stopTimer()
                    treeNu(tree_kind,tree_level)
                    val builder = AlertDialog.Builder(requireActivity())
                    builder.setTitle("제목")
                    builder.setMessage("나무에 꽃이 피었습니다\n 다음 나무를 키우고 싶으시다면 '확인'버튼을 누르고 타이머를 실행시켜주세요")
                    builder.setPositiveButton(
                        "확인"
                    ) { dialog, which ->
                        tree_level = 0
                        tree_kind++
                        count++
                    }
                    builder.show()

                }else{
                    treeNu(tree_kind,tree_level)
                    tree_time()
                }
                if (tree_kind == 5){
                    tree_kind = 0 // 첫번째 나무로 돌아오기
                }
            }

            requireActivity().runOnUiThread {


                binding.timerMain.text = "%02d : %02d : %02d".format(hour,min,sec)
                //Log.d(TAG, "${min}:min ${sec}:sec 민")
                binding.timerMilli.text = "${milli}"
                binding.progressCircleMain.progress = time1 - time
               // Log.d(TAG, "startTimer: ${time1-time} t1 -t ")

            }

        }

    }

    private fun stopTimer(){
        timerTask?.cancel()
    }

    private fun tree_time(){

        val zer = 360000
        val one = 1080000
        val two = 1800000
        val thr = 2520000

        when (tree_level){
            0 -> {time = zer
            time1 = zer
            binding.progressCircleMain.max = zer
            studyTime += thr/6000}
            1 -> {time = one
            time1 = one
            binding.progressCircleMain.max = one
            studyTime += zer/6000}
            2 -> {time = two
            time1 = two
            binding.progressCircleMain.max = two
            studyTime += one/6000}
            3 -> {time = thr
            time1 =thr
            binding.progressCircleMain.max = thr
            studyTime += two/6000}
            4 -> {stopTimer()
            tree_level = 0}
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

    private fun tree_min(trLev:Int){
        val trLev = trLev
        when (trLev){
            0 -> {studymin = 0}
            1 -> {studymin = 60}
            2 -> {studymin = 180}
            3 -> {studymin = 300}
            4 -> {studymin = 420}

        }
    }

    private fun tree_progess(){
        when (tree_level){
            0 -> {binding.progressCircleMain.max = 360000
            time1 = 360000}
            1 -> {binding.progressCircleMain.max = 1080000
            time1 = 1080000}
            2 -> {binding.progressCircleMain.max = 1800000
            time1 = 1800000}
            3 -> {binding.progressCircleMain.max = 2520000
            time1 = 2520000}
        }

    }

}