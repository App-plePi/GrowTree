package org.techtown.vacationproject337.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.techtown.vacationproject337.R
import org.techtown.vacationproject337.RecyclerAdapter
import org.techtown.vacationproject337.Tree
import org.techtown.vacationproject337.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentAboutBinding.inflate(inflater, container, false)

        val treelist = arrayListOf(
            Tree("참나무", R.drawable.ic_tree_cha), //이미지 추가
            Tree("벚나무", R.drawable.ic_tree_cherryblossom),
            Tree("조팝나무", R.drawable.ic_tree_meadow),
            Tree("떡갈나무", R.drawable.ic_tree_oak),
            Tree("복숭아나무", R.drawable.ic_tree_peach)
        )

        binding.recycler.adapter = RecyclerAdapter(treelist)
        Log.d(TAG, "onCreateView: ${binding.recycler.adapter}아잉")

        return binding.root
    }
}