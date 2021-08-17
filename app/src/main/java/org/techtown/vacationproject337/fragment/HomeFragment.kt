package org.techtown.vacationproject337.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.techtown.vacationproject337.R
import org.techtown.vacationproject337.databinding.FragmentHomeBinding
import java.util.*
import kotlin.concurrent.timer

class HomeFragment : Fragment() {

    private var time = 360000
    private var time1: Int = time
    private var timerTask: Timer? = null
    private var tree_kind: Int = 0
    private var tree_level: Int = 0
    private var count: Int = 0
    private var studyTime: Int = 0
    private var studymin: Int = 0
    private lateinit var firebaseDb: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentHomeBinding
    private var cheak: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val v: View = binding.getRoot()
        firebaseDb = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid: String = auth.uid.toString()

        firebaseDb.reference.child("User").child(uid).child("name").get()
            .addOnSuccessListener {
                binding.textUserTreeMain.text = "${it.value.toString()}의\n나무 키우기"
            }

        firebaseDb.reference.child("User").child(uid).child("treeKind").get()
            .addOnSuccessListener {
                val a = "${it.value.toString()}"
                tree_kind = a.toInt()
            }
        firebaseDb.reference.child("User").child(uid).child("studyTime").get()
            .addOnSuccessListener {
                var a = "${it.value.toString()}"
                studyTime = a.toInt()
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

            }
        firebaseDb.reference.child("User").child(uid).child("treeLevel").get()
            .addOnSuccessListener {
                val a = "${it.value.toString()}"
                tree_level = a.toInt()
                treeNu(tree_kind, tree_level)
                tree_progess()
                var sec = time / 100 // 초
                var min = time / 6000 // 분
                var hour = time / 360000 // 시간
                var milli = time % 100 // 밀리초
                if (sec >= 60) {
                    sec -= 60 * min
                }
                if (min >= 60) {
                    min -= 60 * hour
                }
                if (hour == 99) {
                    time == 0
                }
                binding.progressCircleMain.progress = time1 - time
                binding.timerMain.text = "%02d : %02d : %02d".format(hour, min, sec)
                binding.timerMilli.text = "%02d".format(milli)
                if (time == 0) {
                    binding.startBtnMain.visibility = View.INVISIBLE
                    binding.endBtnMain.visibility = View.INVISIBLE
                    binding.startBtnMainSub.visibility = View.VISIBLE
                    binding.progressCircleMain.max = 1
                    binding.progressCircleMain.progress = 1
                    when (tree_kind) {
                        0 -> binding.progressImage.setImageResource(R.drawable.ic_tree_peach)
                        1 -> binding.progressImage.setImageResource(R.drawable.ic_tree_meadow)
                        2 -> binding.progressImage.setImageResource(R.drawable.ic_tree_cherryblossom)
                        3 -> binding.progressImage.setImageResource(R.drawable.ic_tree_cha)
                        4 -> binding.progressImage.setImageResource(R.drawable.ic_tree_oak)
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(requireActivity(), "실행오류", Toast.LENGTH_SHORT).show()
            }


        binding.startBtnMain.setOnClickListener {
            binding.startBtnMain.visibility = View.INVISIBLE
            binding.endBtnMain.visibility = View.VISIBLE
            startTimer()
        }
        binding.endBtnMain.setOnClickListener {
            binding.startBtnMain.visibility = View.VISIBLE
            binding.endBtnMain.visibility = View.INVISIBLE
            stopTimer()
        }
        binding.startBtnMainSub.setOnClickListener {
            binding.endBtnMain.visibility = View.VISIBLE
            binding.startBtnMainSub.visibility = View.INVISIBLE
            treeNu(tree_kind, tree_level)
            tree_time()
            startTimer()
        }

        return v
    }

    override fun onStop() {
        super.onStop()
        firebaseDb = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid: String = auth.uid.toString()
        if (time == 0) {
            tree_min(tree_level)
            studyTime = 960 * count + studymin
        } else {
            tree_min(tree_level)
            studyTime = ((time1 - time) / 6000) + (960 * count + studymin)
        }
        firebaseDb.reference.child("User").child(uid).child("time")
            .setValue(time) // 플래그먼트 종료시 타이머 시간값
        firebaseDb.reference.child("User").child(uid).child("treeKind")
            .setValue(tree_kind) // 종료시 나무 종류
        firebaseDb.reference.child("User").child(uid).child("treeLevel")
            .setValue(tree_level) // 종료시 나무 단계
        firebaseDb.reference.child("User").child(uid).child("count").setValue(count) // 나무 수
        firebaseDb.reference.child("User").child(uid).child("studyTime").setValue(studyTime) // 공부시간
        stopTimer()
    }

    private fun startTimer() {

        timerTask = timer(period = 10) {
            time--

            var sec = time / 100 // 초
            var min = time / 6000 // 분
            var hour = time / 360000 // 시간
            var milli = time % 100 // 밀리초


            if (sec >= 60) {
                sec -= 60 * min
            } // 십진수 = time 변수들 단위 맞추기
            if (min >= 60) {
                min -= 60 * hour
            }
            if (hour == 99) {
                time == 0
            }
            if (time == 0) {

                tree_level++ // 단계 증가

                if (tree_level == 4) {
                    treeNu(tree_kind, tree_level)
                    tree_level = 0
                    tree_kind++
                    count++
                    stopTimer()
                    cheak = true
                } else {
                    treeNu(tree_kind, tree_level)
                    tree_time()
                }
                if (tree_kind == 5) {
                    tree_kind = 0 // 첫번째 나무로 돌아오기
                }
            }

            requireActivity().runOnUiThread {

                if (cheak == true) {
                    val builder = AlertDialog.Builder(requireActivity())
                    builder.setTitle("나무 성장 완료!")
                    builder.setMessage("16시간동안 나무를 키운 당신! 대단합니다!\n다음 나무를 키우고 싶으시면 'start'버튼을 눌러주세요")
                    builder.setPositiveButton(
                        "확인"
                    ) { dialog, which -> }
                    cheak = false
                    builder.show()
                    binding.startBtnMain.visibility = View.INVISIBLE
                    binding.endBtnMain.visibility = View.INVISIBLE
                    binding.startBtnMainSub.visibility = View.VISIBLE
                }
                binding.timerMain.text = "%02d : %02d : %02d".format(hour, min, sec)
                binding.timerMilli.text = "%02d".format(milli)
                binding.progressCircleMain.progress = time1 - time

            }

        }

    }

    private fun stopTimer() {
        timerTask?.cancel()
    }

    private fun tree_time() {

        val zer = 360000
        val one = 1080000
        val two = 1800000
        val thr = 2520000

        when (tree_level) {
            0 -> {
                time = zer
                time1 = zer
                binding.progressCircleMain.max = zer
                studyTime += thr / 6000
            }
            1 -> {
                time = one
                time1 = one
                binding.progressCircleMain.max = one
                studyTime += zer / 6000
            }
            2 -> {
                time = two
                time1 = two
                binding.progressCircleMain.max = two
                studyTime += one / 6000
            }
            3 -> {
                time = thr
                time1 = thr
                binding.progressCircleMain.max = thr
                studyTime += two / 6000
            }
            4 -> {
                stopTimer()
                tree_level = 0
            }
        }
    }

    private fun treeNu(tr_kind: Int, tr_level: Int) {

        val trKind = tr_kind
        val trLevel = tr_level
        val trImg = binding.progressImage

        when (trKind) {
            0 -> when (trLevel) {
                0 -> trImg.setImageResource(R.drawable.ic_levelone)
                1 -> trImg.setImageResource(R.drawable.ic_leveltwo)
                2 -> trImg.setImageResource(R.drawable.ic_levelthree)
                3 -> trImg.setImageResource(R.drawable.ic_levelfour)
                4 -> trImg.setImageResource(R.drawable.ic_tree_meadow)
            }
            1 -> when (trLevel) {
                0 -> trImg.setImageResource(R.drawable.ic_levelone)
                1 -> trImg.setImageResource(R.drawable.ic_leveltwo)
                2 -> trImg.setImageResource(R.drawable.ic_levelthree)
                3 -> trImg.setImageResource(R.drawable.ic_levelfour)
                4 -> trImg.setImageResource(R.drawable.ic_tree_cherryblossom)
            }
            2 -> when (trLevel) {
                0 -> trImg.setImageResource(R.drawable.ic_levelone)
                1 -> trImg.setImageResource(R.drawable.ic_leveltwo)
                2 -> trImg.setImageResource(R.drawable.ic_levelthree)
                3 -> trImg.setImageResource(R.drawable.ic_levelfour)
                4 -> trImg.setImageResource(R.drawable.ic_tree_cha)
            }
            3 -> when (trLevel) {
                0 -> trImg.setImageResource(R.drawable.ic_levelone)
                1 -> trImg.setImageResource(R.drawable.ic_leveltwo)
                2 -> trImg.setImageResource(R.drawable.ic_levelthree)
                3 -> trImg.setImageResource(R.drawable.ic_levelfour)
                4 -> trImg.setImageResource(R.drawable.ic_tree_oak)
            }
            4 -> when (trLevel) {
                0 -> trImg.setImageResource(R.drawable.ic_levelone)
                1 -> trImg.setImageResource(R.drawable.ic_leveltwo)
                2 -> trImg.setImageResource(R.drawable.ic_levelthree)
                3 -> trImg.setImageResource(R.drawable.ic_levelfour)
                4 -> trImg.setImageResource(R.drawable.ic_tree_peach)
            }
        }
    }

    private fun tree_min(trLev: Int) {
        val trLev = trLev
        when (trLev) {
            0 -> {
                studymin = 0
            }
            1 -> {
                studymin = 60
            }
            2 -> {
                studymin = 240
            }
            3 -> {
                studymin = 540
            }
            4 -> {
                studymin = 960
            }

        }
    }

    private fun tree_progess() {
        when (tree_level) {
            0 -> {
                binding.progressCircleMain.max = 360000
                time1 = 360000
            }
            1 -> {
                binding.progressCircleMain.max = 1080000
                time1 = 1080000
            }
            2 -> {
                binding.progressCircleMain.max = 1800000
                time1 = 1800000
            }
            3 -> {
                binding.progressCircleMain.max = 2520000
                time1 = 2520000
            }
        }

    }

}