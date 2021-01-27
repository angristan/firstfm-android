package fr.esgi.firstfm.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.esgi.firstfm.R
import kotlinx.android.synthetic.main.fragment_a_nominated.*

private const val ARG_PARAM1 = "rankNumber"
private const val ARG_PARAM2 = "nominatedName"
private const val ARG_PARAM3 = "nominatedPicture"

/**
 * A simple [Fragment] subclass.
 * Use the [ANominatedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ANominatedFragment : Fragment(), View.OnClickListener{

    private var rankNumber: String? = null
    private var nominatedName: String? = null
    private var nominatedPicture: String? = null

    interface OnNominatedClickedListener
    {
        fun onNominatedClicked(rank: String?)
    }
    
    private var onNominatedClickedListener: OnNominatedClickedListener? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            rankNumber = it.getInt(ARG_PARAM1).toString()
            nominatedName = it.getString(ARG_PARAM2)
            nominatedPicture = it.getString(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        return inflater.inflate(R.layout.fragment_a_nominated, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rankNumber?.let { Log.d("essaie", it) }
        nominatedName?.let { Log.d("essaie", it) }
        rankNumberTextView?.text = rankNumber
        nominatedNameTextView?.text = nominatedName

        Picasso.get()
            .load("https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088")
            .into(nominatedPictureImageView)
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(rankNumber: Int, nominatedName: String, nominatedPicture: String) =
            ANominatedFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, rankNumber)
                    putString(ARG_PARAM2, nominatedName)
                    putString(ARG_PARAM3, nominatedPicture)
                }
            }
    }

    override fun onClick(v: View?) {
        onNominatedClickedListener?.onNominatedClicked(rankNumber)
    }
}