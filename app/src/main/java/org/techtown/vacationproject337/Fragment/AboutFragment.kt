package org.techtown.vacationproject337.Fragment

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.techtown.vacationproject337.MainActivity2
import org.techtown.vacationproject337.R
import org.techtown.vacationproject337.RecyclerAdapter
import org.techtown.vacationproject337.Tree
import org.techtown.vacationproject337.databinding.FragmentAboutBinding
import org.techtown.vacationproject337.databinding.FragmentHomeBinding

class AboutFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentAboutBinding.inflate(inflater,container,false)
        //val v: View = binding.getRoot()

        val treelist = arrayListOf(
            Tree("참나무",R.drawable.ic_tree_cha), //이미지 추가
            Tree("복숭아나무",R.drawable.ic_tree_peach),
            Tree("조팝나무",R.drawable.ic_tree_meadow),
            Tree("떡갈나무",R.drawable.ic_tree_oak),
            Tree("벚나무",R.drawable.ic_tree_cherryblossom)
        )

        //val linearLayoutManager by lazy { LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false) }

        //recycler.layoutManager = linearLayoutManager // 프래그먼트에서는 context 사용 불가

        binding.recycler.adapter = RecyclerAdapter(treelist)
        Log.d(TAG, "onCreateView: ${binding.recycler.adapter}아잉")

        return binding.root
    }
}