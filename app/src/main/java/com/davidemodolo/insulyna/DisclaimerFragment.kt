package com.davidemodolo.insulyna


import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class DisclaimerFragment : Fragment() {
    private val PREF_NAME = "data"
    private val FIRST_START = "start"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_disclaimer, container, false)

        val proceed = view.findViewById<TextView>(R.id.btn)

        val check = view.findViewById<CheckBox>(R.id.check)
        check.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                proceed.visibility = View.VISIBLE
            } else {
                proceed.visibility = View.GONE
            }

        }
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(PREF_NAME, 0)

        proceed.setOnClickListener {
            if (check.isChecked) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_right, R.anim.slide_right)
                    .replace(R.id.nav_host_fragment, MainFragment()).commit()
                //findNavController().navigate(R.id.mainFragment)
                val editor = sharedPref?.edit()
                editor?.putBoolean(FIRST_START, false)
                editor?.apply()
            }
        }


        return view
    }

}