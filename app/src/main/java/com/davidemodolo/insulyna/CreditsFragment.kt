package com.davidemodolo.insulyna

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import android.content.Intent
import android.net.Uri


class CreditsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_credits, container, false)

        val btnBack = view.findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            btnBack.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.alpha))
            findNavController().navigateUp()
        }

        val btnContactMe = view.findViewById<TextView>(R.id.contactMe)
        btnContactMe.setOnClickListener {
            val i = Intent(Intent.ACTION_SENDTO)
            i.data = Uri.parse("mailto:dott.modolo+insulyna@gmail.com")
            i.putExtra(Intent.EXTRA_SUBJECT, "[Insulyna] ....")
            startActivity(Intent.createChooser(i, "Problema o Idea"))
        }

        val help = view.findViewById<TextView>(R.id.help)
        help.movementMethod = LinkMovementMethod.getInstance()

        return view
    }

}