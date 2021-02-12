package fr.esgi.firstfm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.esgi.firstfm.R
import kotlinx.android.synthetic.main.fragment_back_button.*


class BackButtonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_back_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        backActivity.setOnClickListener {
            activity?.finish()
        }
    }

}