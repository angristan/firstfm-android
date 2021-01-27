package fr.esgi.firstfm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.esgi.firstfm.R
import fr.esgi.firstfm.album.AlbumDetailActivity

class TopFiveTracksFragment : Fragment(), ANominatedFragment.OnNominatedClickedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        bindDataToFragmentId(R.id.aNominatedFragment1, 1, "track_1", "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088")
        bindDataToFragmentId(R.id.aNominatedFragment2, 2, "track_2", "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088")
        bindDataToFragmentId(R.id.aNominatedFragment3, 3, "track_3", "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088")
        bindDataToFragmentId(R.id.aNominatedFragment4, 4, "track_4", "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088")
        bindDataToFragmentId(R.id.aNominatedFragment5, 5, "track_5", "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088")

        return inflater.inflate(R.layout.fragment_top_five_tracks, container, false)
    }

    private fun bindDataToFragmentId(fragmentId: Int, rankNumber: Int, nominatedName: String, nominatedPicture: String) {
        childFragmentManager.beginTransaction().replace(fragmentId, ANominatedFragment.newInstance(rankNumber, nominatedName, nominatedPicture)).commit()
    }

    override fun onNominatedClicked(rank: String?) {
        activity?.let { AlbumDetailActivity.navigateTo(it, "fromMain", "fromMain") }
    }
}