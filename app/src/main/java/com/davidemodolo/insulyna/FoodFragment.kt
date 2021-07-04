package com.davidemodolo.insulyna

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.davidemodolo.insulyna.ui.main.MainFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FoodFragment : Fragment() {

    companion object {
        fun newInstance() = FoodFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_food, container, false)
        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            /*val bsd = BottomSheetDialog(requireContext(), R.style.BottomSheedDialogTheme)
            val bsv = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_addfood, container)
            bsd.show()*/
        }
        return view
    }

}